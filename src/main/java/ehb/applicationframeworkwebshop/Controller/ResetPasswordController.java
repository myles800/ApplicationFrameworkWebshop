package ehb.applicationframeworkwebshop.Controller;

import ehb.applicationframeworkwebshop.Model.Cart;
import ehb.applicationframeworkwebshop.Model.Product;
import ehb.applicationframeworkwebshop.Model.Products;
import ehb.applicationframeworkwebshop.Model.User;
import ehb.applicationframeworkwebshop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ResetPasswordController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = {"/user/resetPassword"}, method = RequestMethod.GET)
    public String showResetPasswordForm() {
        return "resetPassword";
    }
    @RequestMapping(value = {"/user/resetPassword"}, method = RequestMethod.POST)
    public String resetPassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("password") String password, @RequestParam("checkPassword") String checkPassword,
                          Principal principal) {
        User authUser= userRepository.findByEmail(principal.getName());
        System.out.println(passwordEncoder.matches(oldPassword, authUser.getPassword()));
        if (!passwordEncoder.matches(oldPassword, authUser.getPassword())) {
            return "redirect:/user/resetPassword?oldPassword";
        }

        if (password.length()<8) {
            return "redirect:/user/resetPassword?password";
        }
        if (!password.equals(checkPassword)) {
            return "redirect:/user/resetPassword?checkPassword";
        }
        authUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(authUser);
        return "redirect:/?password";
    }
}
