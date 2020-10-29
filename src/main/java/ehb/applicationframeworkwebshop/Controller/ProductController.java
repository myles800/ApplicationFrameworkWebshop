package ehb.applicationframeworkwebshop.Controller;

import ehb.applicationframeworkwebshop.Model.Categorie;
import ehb.applicationframeworkwebshop.Model.Product;
import ehb.applicationframeworkwebshop.Repository.CategorieRepository;
import ehb.applicationframeworkwebshop.Repository.ProductRepository;
import ehb.applicationframeworkwebshop.Service.CustomUserDetailServiceImplementatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @ModelAttribute("categories")
    public Iterable<Categorie> getAllCategorie(){
        return categorieRepository.findAll();
    }
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index(ModelMap model) {
        Iterable<Product> producten =productRepository.findAll();
        model.addAttribute("producten", producten);
        return "index";
    }

}
