package hospital.repository;

import hospital.model.Medicineperexam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MedicinePerExamRepository extends CrudRepository<Medicineperexam, Integer> {
    @Transactional
    @Modifying
    @Query(value ="UPDATE medicineperexam n SET n.idmedicine=:idmedicine,n.idexamination=:idexamination WHERE n.idmedicineperexam=:id", nativeQuery = true)
    public void update(int idmedicine,int idexamination,int id);
}
