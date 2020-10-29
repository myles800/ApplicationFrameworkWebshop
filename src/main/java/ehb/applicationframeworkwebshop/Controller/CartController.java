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
    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String root(Principal principal, Model model) {
        User authUser= userRepository.findByEmail(principal.getName());
        List<Products> productsList= productsRepository.findProductsByAuthUser(authUser);
        model.addAttribute("productsList", productsList);
        return "cart";
    }
    @RequestMapping(value = {"/cart/addProduct"}, method = RequestMethod.GET)
    public String addCart(@RequestParam("product_id") String product_id,Principal principal) {
        User authUser= userRepository.findByEmail(principal.getName());
        Cart cart= cartRepository.findByUser(authUser);
        Product product;
        System.out.println(product_id);
        if(productRepository.findById(Integer.parseInt(product_id))!=null){
            product = productRepository.findById(Integer.parseInt(product_id));
            if(productsRepository.findProductsByProductAndCart(product,cart)!=null){
                Products p=productsRepository.findProductsByProductAndCart(product,cart);
                p.setAmount(p.getAmount()+1);
                productsRepository.save(p);
            }else{
                Products p=new Products();
                p.setActive(true);
                p.setAmount(1);
                p.setCart(cart);
                p.setProduct(product);
                productsRepository.save(p);
            }

        }

        return "redirect:/cart";
    }
    @RequestMapping(value = {"/cart/changeAmount"}, method = RequestMethod.POST)
    public String changeAmount(Principal principal,@RequestParam("product_id") int product_id,@RequestParam("amount") int amount) {

        Products p= productsRepository.findById(product_id);
        if(amount<1){
            productsRepository.deleteById(product_id); //does not work
        }
        else{
            p.setAmount(amount);
            productsRepository.save(p);

        }
        return "redirect:/cart";

    }
    @RequestMapping(value = {"/cart/delete"}, method = RequestMethod.GET)
    public String deleteCart(@RequestParam("product_id") int product_id) {
        productsRepository.deleteById(product_id);

        return "redirect:/cart";
    }
}
