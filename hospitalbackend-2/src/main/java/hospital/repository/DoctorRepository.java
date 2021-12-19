package hospital.repository;

import hospital.model.Doctor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    @Query(value = "SELECT doctor.* FROM ltwbs.doctordiseases " +
            "inner join doctor on doctor.iddoctor = doctordiseases.iddoctor " +
            "inner join diseases on diseases.iddiseases = doctordiseases.iddiseases " +
            "WHERE diseases.iddiseases = ? and doctor.type = 'active' and diseases.type = 'active' and doctordiseases.type = 'active'", nativeQuery = true)
    public List<Doctor> findDoctorHeal(String iddiseases);

    @Transactional
    @Modifying
    @Query(value ="UPDATE doctor doc SET doc.name=:name,doc.birthday=:birthday,doc.cmd=:cmd,doc.address=:address,doc.level=:level,doc.seniority=:seniority,doc.type=:type WHERE doc.iddoctor=:id", nativeQuery = true)
    public void update(String name, String birthday, String cmd, String address, String level, int seniority, String type,int id);
}
