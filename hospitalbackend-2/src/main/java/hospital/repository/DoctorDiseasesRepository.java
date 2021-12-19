package hospital.repository;

import hospital.model.DoctorDiseases;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorDiseasesRepository extends CrudRepository<DoctorDiseases, Integer> {

    public List<DoctorDiseases> findAllByIddoctorAndIddiseases(int iddoctor, int iddiseases);
    public Optional<DoctorDiseases> findAllByIddoctor(int id);
    @Transactional
    @Modifying
    @Query(value ="UPDATE doctordiseases d SET d.iddoctor=:iddoctor,d.iddiseases=:iddiseases,d.type=:type WHERE d.iddoctordiseases=:id", nativeQuery = true)
    public void update(int iddoctor, int iddiseases, String type,int id);

    @Transactional
    @Modifying
    @Query(value ="UPDATE doctordiseases SET type=?1 WHERE iddoctor=?2", nativeQuery = true)
    public  void updateType(String uType, int iddoctor);
}
