package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.Categorie;
import ehb.applicationframeworkwebshop.Model.Product;
import ehb.applicationframeworkwebshop.Model.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("SELECT p FROM Product p where p.categorie in :categories")
    List<Product> findProductsByCategories(@Param("categories") List<Categorie> categories);

    Product findById(int id);
    @Query("select p from Product p where p.name  LIKE %:text%  or p.description LIKE %:text% ")
    List<Product> findByTitelOrDescription(@Param("text")String text);
}
