package ehb.applicationframeworkwebshop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class MainController {
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String showLogin(Model model){
        return "login";
    }
    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public String userIndex() {
        return "cart";
    }

}