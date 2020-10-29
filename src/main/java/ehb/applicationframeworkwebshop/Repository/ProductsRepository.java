package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.Categorie;
import ehb.applicationframeworkwebshop.Model.Products;
import ehb.applicationframeworkwebshop.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products,Integer> {
    @Query("select p from Products p where p.cart.user = :authUser")
    List<Products> findProductsByAuthUser(@Param("authUser") User authUser );

}
