package personal.dgvu.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import personal.dgvu.Constants;
import personal.dgvu.dao.SearchException;
import personal.dgvu.service.UserManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ndvu on 4/6/2015.
 */
@Controller
@RequestMapping("taxpaid*")
public class TaxPaidController {
    private UserManager userManager = null;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping("taxpaid")
    public ModelAndView handleRequest(final HttpServletRequest request) throws Exception {
        Model model = new ExtendedModelMap();
        String username = request.getRemoteUser();
        //String username2 = request.getUserPrincipal().getName();//2nd approach
        model.addAttribute("salaryRecordList", userManager.getUserByUsername(username).getSalaryRecords());
        System.out.println(userManager.getUserByUsername(username).getSalaryRecords().toString());
        return new ModelAndView("taxpaid", model.asMap());
    }
}
