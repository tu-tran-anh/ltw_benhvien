package hospital.repository;

import hospital.model.Diseases;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DiseasesRepository extends CrudRepository<Diseases, Integer > {

    @Transactional
    @Modifying
    @Query(value ="UPDATE diseases disease SET disease.name=:name,disease.description=:description,disease.type=:type WHERE disease.iddiseases=:id", nativeQuery = true)
    public void update(String name, String description, String type,int id);

}
