package ehb.applicationframeworkwebshop.Controller;

import ehb.applicationframeworkwebshop.Model.Categorie;
import ehb.applicationframeworkwebshop.Model.Product;
import ehb.applicationframeworkwebshop.Repository.CategorieRepository;
import ehb.applicationframeworkwebshop.Repository.ProductRepository;
import ehb.applicationframeworkwebshop.Service.CustomUserDetailServiceImplementatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @RequestMapping(value = {"/categorie"}, method = RequestMethod.GET)
    public String indexByCategorie(ModelMap model, @RequestParam("categorie") List<String> categories_id) {
        List<Integer> ids = new ArrayList<>();
        for ( String id: categories_id) {
            ids.add(Integer.parseInt(id));

        }
        List<Categorie> categories = categorieRepository.findCategorieByIds(ids);
        Iterable<Product> producten;
        if(!categories.isEmpty()){
            producten=productRepository.findProductsByCategories(categories);

        }else{
            producten =productRepository.findAll();
        }

        model.addAttribute("producten", producten);
        return "index";
    }
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String indexByCategorie(ModelMap model, @RequestParam("search") String search) {
        List<Product> producten = productRepository.findByTitelOrDescription(search);
        model.addAttribute("producten", producten);
        return "index";
    }

}
