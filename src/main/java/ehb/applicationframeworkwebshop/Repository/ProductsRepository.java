package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductsRepository extends CrudRepository<Products,Integer> {
    @Query("select p from Products p where p.cart.user = :authUser")
    List<Products> findProductsByAuthUser(@Param("authUser") User authUser );
    Products findById(int id);
    @Query("select p from Products p where p.product = :product and p.cart = :cart and p.active=true")
    Products findProductsByProductAndCart(@Param("product")Product product,@Param("cart") Cart cart);
    @Transactional              //zodat db kan aangepast worden
    @Modifying
    @Query("delete from Products p where p.id = :id")
    void deleteById(@Param("id")int id);


}
