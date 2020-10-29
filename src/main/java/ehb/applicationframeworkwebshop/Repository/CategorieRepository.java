package ehb.applicationframeworkwebshop.Repository;

import ehb.applicationframeworkwebshop.Model.Categorie;
import ehb.applicationframeworkwebshop.Model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategorieRepository extends CrudRepository<Categorie,Integer> {
    @Query("SELECT p FROM Categorie p where p.id in :cat_id")
    List<Categorie> findCategorieByIds(@Param("cat_id") List<Integer> cat_id );
}
