package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.Role;
import ehb.applicationframeworkwebshop.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
