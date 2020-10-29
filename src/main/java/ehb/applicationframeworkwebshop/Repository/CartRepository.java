package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.Cart;
import ehb.applicationframeworkwebshop.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart,Integer> {
    Cart findByUser(User user);
}
