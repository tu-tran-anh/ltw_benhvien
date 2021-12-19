package hospital.service;

import hospital.model.Doctor;
import hospital.model.Examination;
import hospital.repository.DiseasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ExaminationService {

    public List<Examination> getAll();

    public List<Examination> find(String type, String value);

    public List<Examination> findLike(String type, String value);

    public int add(Examination examination);

    public  int delete(int id);

    public int update(int id, Examination examination) ;

    public List<Examination> getExamOfDoctor(String iddoctor,String dayin);
}
