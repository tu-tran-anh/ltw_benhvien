package hospital.repository;

import hospital.model.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    @Transactional
    @Modifying
    @Query(value ="UPDATE patient p SET p.cmd=:cmd,p.name=:name,p.birthday=:birthday,p.address=:address,p.phone=:phone WHERE p.idPatient=:id", nativeQuery = true)
    public void update(String cmd, String name, String birthday, String address, String phone,int id);
}
