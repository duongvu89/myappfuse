package personal.dgvu.webapp.controller;


import personal.dgvu.service.GenericManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import personal.dgvu.webapp.model.Person;

/**
 * Created by ndvu on 3/30/2015.
 */

@Controller
@RequestMapping("/persons*")
public class PersonController {

    private GenericManager<Person, Long> personManager;

    @Autowired
    public void setPersonManager(@Qualifier("personManager") GenericManager<Person, Long> personManager) {
        this.personManager = personManager;
    }

    public GenericManager<Person, Long> getPersonManager() {
        return personManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest()
            throws Exception {
        //The default view page will be located at src/main/webapp/WEB-INF/pages/persons.jsp because of the RequestToViewNameTranslator
        //The list of people (the model) will be available from "personList"
        return new ModelAndView().addObject(personManager.getAll());
    }
}
