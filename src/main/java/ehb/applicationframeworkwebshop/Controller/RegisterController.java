package ehb.applicationframeworkwebshop.Controller;

import ehb.applicationframeworkwebshop.Model.Cart;
import ehb.applicationframeworkwebshop.Model.User;
import ehb.applicationframeworkwebshop.Repository.CartRepository;
import ehb.applicationframeworkwebshop.Repository.UserRepository;
import ehb.applicationframeworkwebshop.Service.CustomUserDetailServiceImplementatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private CustomUserDetailServiceImplementatie userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @ModelAttribute("newUser")
    public User toSave(){
        return new User();
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("newUser") @Valid User user,
                                      BindingResult bindingResult, HttpServletRequest request) {

        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            bindingResult.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.save(user);
        Cart cart =new Cart();
        cart.setUser(userRepository.findByEmail(user.getEmail()));
        cartRepository.save(cart);
        try {
            request.login(user.getEmail(), user.getPassword());
        } catch (ServletException e) {
            System.out.println(e);
        }
        return "redirect:/?login";
    }
}
