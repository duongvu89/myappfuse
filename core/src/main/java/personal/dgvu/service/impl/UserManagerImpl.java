package personal.dgvu.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import personal.dgvu.dao.TaxRateDao;
import personal.dgvu.dao.UserDao;
import personal.dgvu.model.SalaryRecord;
import personal.dgvu.model.TaxRate;
import personal.dgvu.model.User;
import personal.dgvu.service.MailEngine;
import personal.dgvu.service.UserExistsException;
import personal.dgvu.service.UserManager;
import personal.dgvu.service.UserService;

import javax.jws.WebService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "personal.dgvu.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private TaxRateDao taxRateDao;

    private MailEngine mailEngine;
    private SimpleMailMessage message;
    private PasswordTokenManager passwordTokenManager;

    private String passwordRecoveryTemplate = "passwordRecovery.vm";
    private String passwordUpdatedTemplate = "passwordUpdated.vm";

    @Autowired
    @Qualifier("passwordEncoder")
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Autowired
    public void setUserDao(final UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }

    @Override
    @Autowired
    public void setTaxRateDao(TaxRateDao taxRateDao) {
        this.taxRateDao = taxRateDao;
    }

    @Autowired(required = false)
    public void setMailEngine(final MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired(required = false)
    public void setMailMessage(final SimpleMailMessage message) {
        this.message = message;
    }

    @Autowired(required = false)
    public void setPasswordTokenManager(final PasswordTokenManager passwordTokenManager) {
        this.passwordTokenManager = passwordTokenManager;
    }

    /**
     * Velocity template name to send users a password recovery mail (default
     * passwordRecovery.vm).
     *
     * @param passwordRecoveryTemplate the Velocity template to use (relative to classpath)
     * @see personal.dgvu.service.MailEngine#sendMessage(org.springframework.mail.SimpleMailMessage, String, java.util.Map)
     */
    public void setPasswordRecoveryTemplate(final String passwordRecoveryTemplate) {
        this.passwordRecoveryTemplate = passwordRecoveryTemplate;
    }

    /**
     * Velocity template name to inform users their password was updated
     * (default passwordUpdated.vm).
     *
     * @param passwordUpdatedTemplate the Velocity template to use (relative to classpath)
     * @see personal.dgvu.service.MailEngine#sendMessage(org.springframework.mail.SimpleMailMessage, String, java.util.Map)
     */
    public void setPasswordUpdatedTemplate(final String passwordUpdatedTemplate) {
        this.passwordUpdatedTemplate = passwordUpdatedTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(final String userId) {
        return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User saveUser(final User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                final String currentPassword = userDao.getUserPassword(user.getId());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return userDao.saveUser(user);
        } catch (final Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(final User user) {
        log.debug("removing user: " + user);
        userDao.remove(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(final String userId) {
        log.debug("removing user: " + userId);
        userDao.remove(new Long(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException thrown when username not found
     */
    @Override
    public User getUserByUsername(final String username) throws UsernameNotFoundException {
        User u = (User) userDao.loadUserByUsername(username);
        calculateTax(u);
        return u;
    }

    /**
     * {@inheritDoc}
     * @param user
     */
    @Override
    public void calculateTax(User user) {
        Hibernate.initialize(user.getSalaryRecords());
        List<SalaryRecord> records = user.getSalaryRecords();
        List<TaxRate> allTaxRates = taxRateDao.getAll();
        for (int i = 0; i < records.size(); i++) {
            calculateTax(allTaxRates, records.get(i));
        }
    }

    private void calculateTax(List<TaxRate> allTaxRates, SalaryRecord originRecord) {
        final SalaryRecord record = originRecord;
        final LocalDate startDate = new LocalDate(record.getStartDate());
        final LocalDate endDate = new LocalDate(record.getEndDate());
        List<TaxRate> taxRates = (List<TaxRate>) CollectionUtils.select(allTaxRates, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                TaxRate taxRate = (TaxRate) o;
                return (taxRate.getCountry().getCode() == record.getCountry().getCode()) && (taxRate.getYear() == endDate.getYear());
            }
        });

        BigDecimal tax = new BigDecimal(0);
        BigDecimal tmpTax;
        int month = Months.monthsBetween(startDate, endDate).getMonths();
        month = month > 12 ? 12 : month;
        BigDecimal income = new BigDecimal(record.getSalary() * month);
        System.out.println("Months:" + month + " Salary:" + income);
        for(TaxRate taxRate : taxRates) {
            tmpTax = tax;
            if (income.compareTo(taxRate.getTo()) >= 0) {
                tax =  tax.add(taxRate.getTo().subtract(taxRate.getFrom()).multiply(taxRate.getRate().divide(new BigDecimal(100))));
                income = income.subtract(taxRate.getTo().subtract(taxRate.getFrom()));
                System.out.println("Tax from " + taxRate.getFrom() + " Tax to " + taxRate.getTo() + " is " + tax.subtract(tmpTax));
            } else if (income.compareTo(taxRate.getTo()) < 0 & income.compareTo(taxRate.getFrom()) >= 0) {
                String incomeStr = income.toPlainString();
                tax =  tax.add(income.subtract(taxRate.getFrom()).multiply(taxRate.getRate().divide(new BigDecimal(100))));
                income = taxRate.getFrom();
                System.out.println("Tax from " + taxRate.getFrom() + " Tax to " + incomeStr + " is " + tax.subtract(tmpTax));
            }

        }
        originRecord.setTax(tax.divide(new BigDecimal(month), 1, RoundingMode.HALF_UP));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> search(final String searchTerm) {
        return super.search(searchTerm, User.class);
    }

    @Override
    public String buildRecoveryPasswordUrl(final User user, final String urlTemplate) {
        final String token = generateRecoveryToken(user);
        final String username = user.getUsername();
        return StringUtils.replaceEach(urlTemplate,
                new String[]{"{username}", "{token}"},
                new String[]{username, token});
    }

    @Override
    public String generateRecoveryToken(final User user) {
        return passwordTokenManager.generateRecoveryToken(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRecoveryTokenValid(final String username, final String token) {
        return isRecoveryTokenValid(getUserByUsername(username), token);
    }

    @Override
    public boolean isRecoveryTokenValid(final User user, final String token) {
        return passwordTokenManager.isRecoveryTokenValid(user, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPasswordRecoveryEmail(final String username, final String urlTemplate) {
        log.debug("Sending password recovery token to user: " + username);

        final User user = getUserByUsername(username);
        final String url = buildRecoveryPasswordUrl(user, urlTemplate);

        sendUserEmail(user, passwordRecoveryTemplate, url, "Password Recovery");
    }

    private void sendUserEmail(final User user, final String template, final String url, final String subject) {
        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");
        message.setSubject(subject);

        final Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);
        model.put("applicationURL", url);

        mailEngine.sendMessage(message, template, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updatePassword(final String username, final String currentPassword, final String recoveryToken, final String newPassword, final String applicationUrl) throws UserExistsException {
        User user = getUserByUsername(username);
        if (isRecoveryTokenValid(user, recoveryToken)) {
            log.debug("Updating password from recovery token for user: " + username);
            user.setPassword(newPassword);
            user = saveUser(user);
            passwordTokenManager.invalidateRecoveryToken(user, recoveryToken);

            sendUserEmail(user, passwordUpdatedTemplate, applicationUrl, "Password Updated");

            return user;
        } else if (StringUtils.isNotBlank(currentPassword)) {
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                log.debug("Updating password (providing current password) for user:" + username);
                user.setPassword(newPassword);
                user = saveUser(user);
                return user;
            }
        }
        // or throw exception
        return null;
    }
}
