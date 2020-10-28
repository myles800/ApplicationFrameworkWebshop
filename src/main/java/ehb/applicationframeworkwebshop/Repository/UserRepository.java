package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
