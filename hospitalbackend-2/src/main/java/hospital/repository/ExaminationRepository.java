package hospital.repository;

import hospital.model.Examination;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExaminationRepository extends CrudRepository<Examination, Integer> {

    @Transactional
    @Modifying
    @Query(value ="UPDATE examination e SET e.iddoctordiseases=:iddoctordiseases,e.idnurse=:idnurse,e.idpatient=:idpatient,e.dayin=:dayin,e.dayout=:dayout,doc.type=:type WHERE e.idexamination=:id", nativeQuery = true)
    public void update(int iddoctordiseases, int idnurse, int idpatient, String dayin, String dayout, String type,int id);


    @Query(value ="SELECT examination.* FROM ltwbs.examination\r\n"
            + "inner join doctordiseases on doctordiseases.iddoctordiseases = examination.iddoctordiseases\r\n"
            + "where doctordiseases.iddoctor = ?1 and examination.dayin like %?2%", nativeQuery = true)
    public List<Examination> getExamOfDoctor(String iddoctor, String dayin);
}
