package hospital.service.impl;

import hospital.model.Diseases;
import hospital.model.Examination;
import hospital.repository.DiseasesRepository;
import hospital.repository.ExaminationRepository;
import hospital.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ExaminationServiceImpl implements ExaminationService {
    private ExaminationRepository examinationRepos;
    private JdbcTemplate jdbc;

    @Autowired
    ExaminationServiceImpl(JdbcTemplate jdbc, ExaminationRepository examinationRepos ) {
        this.jdbc = jdbc;
        this.examinationRepos = examinationRepos;
    }
    @Override
    public List<Examination> getAll() {
        List<Examination> lstExamination = new ArrayList<>();
        examinationRepos.findAll().forEach(lstExamination::add);
        return lstExamination;
    }

    @Override
    public List<Examination> find(String type, String value) {
        String sql ="SELECT * FROM examination where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToExamination);
    }

    @Override
    public List<Examination> findLike(String type, String value) {
        String sql ="SELECT * FROM examination where " + type +" like '%" + value +"%';";
        return jdbc.query(sql,this::mapRowToExamination);
    }

    @Override
    public int add(Examination examination) {
        try{
            examinationRepos.save(examination);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            examinationRepos.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, Examination examination) {
        try{
            if(!examinationRepos.findById(id).isPresent()) return 0;
            examinationRepos.update(examination.getIddoctordiseases(),
                    examination.getIdnurse(),
                    examination.getIdpatient(),
                    examination.getDayin(),
                    examination.getDayout(),
                    examination.getType(),
                    id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public List<Examination> getExamOfDoctor(String iddoctor, String dayin) {
        List<Examination> lstExamination = new ArrayList<>();
        examinationRepos.getExamOfDoctor(iddoctor, dayin).forEach(lstExamination::add);
        return lstExamination;
    }
    private Examination mapRowToExamination(ResultSet rs, int rowNum)
            throws SQLException {
        return new Examination(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7));
    }
}
