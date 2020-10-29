package ehb.applicationframeworkwebshop.Controller;

import ehb.applicationframeworkwebshop.Model.Cart;
import ehb.applicationframeworkwebshop.Model.Product;
import ehb.applicationframeworkwebshop.Model.Products;
import ehb.applicationframeworkwebshop.Model.User;
import ehb.applicationframeworkwebshop.Repository.CartRepository;
import ehb.applicationframeworkwebshop.Repository.ProductRepository;
import ehb.applicationframeworkwebshop.Repository.ProductsRepository;
import ehb.applicationframeworkwebshop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public String root(Principal principal, Model model) {
        User authUser= userRepository.findByEmail(principal.getName());
        List<Products> productsList= productsRepository.findProductsByAuthUser(authUser);
        model.addAttribute("productsList", productsList);
        return "cart";
    }
    @RequestMapping(value = {"/user/addProduct"}, method = RequestMethod.GET)
    public String addCart(@RequestParam("product_id") String product_id,Principal principal, Model model) {
        User authUser= userRepository.findByEmail(principal.getName());
        Cart cart= cartRepository.findByUser(authUser);
        Product product;
        System.out.println(product_id);
        if(productRepository.findById(Integer.parseInt(product_id))!=null){
            product = productRepository.findById(Integer.parseInt(product_id));
            Products p=new Products();
            p.setActive(true);
            p.setAmount(1);
            p.setCart(cart);
            p.setProduct(product);
            productsRepository.save(p);
        }

        return "redirect:/user";
    }
}
