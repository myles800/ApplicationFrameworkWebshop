package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.Factuur;
import ehb.applicationframeworkwebshop.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FactuurRepository extends CrudRepository<Factuur,Integer> {
    Factuur findById(int id);
    List<Factuur> findByUser(User user);
}
