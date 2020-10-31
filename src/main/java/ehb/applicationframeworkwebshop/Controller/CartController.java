package ehb.applicationframeworkwebshop.Controller;

import ehb.applicationframeworkwebshop.Model.*;
import ehb.applicationframeworkwebshop.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FactuurRepository factuurRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @ModelAttribute("newFactuur")
    public Factuur toSave(){
        return new Factuur();
    }
    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String root(Principal principal, Model model) {      //shows all products in cart and the orders
        User authUser= userRepository.findByEmail(principal.getName());
        List<Products> productsList= productsRepository.findProductsByAuthUser(authUser);
        model.addAttribute("productsList", productsList);
        double total=0;
        for (Products p: productsList
             ) {
            total=total+(p.getProduct().getPrijs()*p.getAmount());
        }
        model.addAttribute("total", total);
        List<Factuur> facturen = factuurRepository.findByUser(authUser);
        model.addAttribute("facturen", facturen);

        return "cart";
    }
    @RequestMapping(value = {"/cart/addProduct"}, method = RequestMethod.GET) //Adds product to cart
    public String addCart(@RequestParam("product_id") String product_id,Principal principal) {
        User authUser= userRepository.findByEmail(principal.getName());
        Cart cart= cartRepository.findByUser(authUser);
        Product product;
        if(productRepository.findById(Integer.parseInt(product_id))!=null){
            product = productRepository.findById(Integer.parseInt(product_id));
            if(productsRepository.findProductsByProductAndCart(product,cart)!=null){
                Products p=productsRepository.findProductsByProductAndCart(product,cart);
                p.setAmount(p.getAmount()+1);
                productsRepository.save(p);
            }else{
                Products p=new Products();
                p.setAmount(1);
                p.setCart(cart);
                p.setProduct(product);
                productsRepository.save(p);
            }

        }

        return "redirect:/cart";
    }
    @RequestMapping(value = {"/cart/changeAmount"}, method = RequestMethod.POST) //change the amount of the specific product in the cart
    public String changeAmount(@RequestParam("product_id") int product_id,@RequestParam("amount") int amount) {

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
    @RequestMapping(value = {"/cart/delete"}, method = RequestMethod.GET) //deletes product out of cart
    public String deleteCart(@RequestParam("product_id") int product_id,Principal principal) {
        Products p = productsRepository.findById(product_id);
        User authUser= userRepository.findByEmail(principal.getName());

        if(p.getCart().getUser()==authUser && p.getFactuur() == null)
            productsRepository.deleteById(product_id);

        return "redirect:/cart";
    }
    @RequestMapping(value = {"/cart/checkout/{id}"}, method = RequestMethod.GET) //shows check out with the order and a form to fill in
    public String Checkout(@PathVariable("id") int cart_id,Model model,Principal principal) {
        User authUser= userRepository.findByEmail(principal.getName());
        List<Products> productsList= productsRepository.findProductsByAuthUser(authUser);
        model.addAttribute("productsList", productsList);
        double total=0;
        for (Products p: productsList
        ) {
            total=total+(p.getProduct().getPrijs()*p.getAmount());
        }
        model.addAttribute("total", total);

        return "checkout";
    }
    @RequestMapping(value = {"/cart/checkout"}, method = RequestMethod.POST) //makes the order and links it to the cart
    public String CheckoutSubmit(Model model, Principal principal, @ModelAttribute("newFactuur") @Valid Factuur factuur,
                                 BindingResult bindingResult) {
        int factuur_id = 0;
        User authUser= userRepository.findByEmail(principal.getName());
        Factuur f= new Factuur();
        f.setHuisnummer(factuur.getHuisnummer());
        f.setLand(factuur.getLand());
        f.setPostcode(factuur.getPostcode());
        f.setStraat(factuur.getStraat());
        f.setUser(authUser);
        f.setDatum(new Date());
        f = factuurRepository.save(f);
        List<Products> productsList= productsRepository.findProductsByAuthUser(authUser);

        for (Products p:productsList
             ) {
            p.setFactuur(f);
            productsRepository.save(p);
        }
        return "redirect:/factuur/"+f.getId();
    }
    @RequestMapping(value = {"/factuur/{id}"}, method = RequestMethod.GET) //gets a specific order
    public String getFactuur(@PathVariable("id") int factuur_id,Model model,Principal principal) {
        User authUser= userRepository.findByEmail(principal.getName());

        Factuur factuur=factuurRepository.findById(factuur_id);
        List<Products> productsList= factuur.getProductsList();
        model.addAttribute("productsList", productsList);
        double total=0;
        for (Products p: productsList
        ) {
            total=total+(p.getProduct().getPrijs()*p.getAmount());
        }
        model.addAttribute("total", total);
        Date d= factuur.getDatum();
        model.addAttribute("date", d.toString());
        if(authUser==factuur.getUser())
            return "factuur";
        else
            return "/";
    }
}
