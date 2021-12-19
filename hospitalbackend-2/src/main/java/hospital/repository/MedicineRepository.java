package hospital.repository;

import hospital.model.Medicine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Integer> {
    @Transactional
    @Modifying
    @Query(value ="UPDATE medicine n SET n.name=:name,n.cost=:cost,n.type=:type WHERE n.idmedicine=:id", nativeQuery = true)
    public void update(String name,int cost, String type,int id);
}
