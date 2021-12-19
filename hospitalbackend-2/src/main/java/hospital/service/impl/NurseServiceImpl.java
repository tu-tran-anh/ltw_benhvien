package hospital.service.impl;

import hospital.model.Diseases;
import hospital.model.Doctor;
import hospital.model.Nurse;
import hospital.repository.DiseasesRepository;
import hospital.repository.NurseRepository;
import hospital.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NurseServiceImpl implements NurseService {
    private NurseRepository nurseRepos;
    private JdbcTemplate jdbc;

    @Autowired
    NurseServiceImpl(JdbcTemplate jdbc, NurseRepository nurseRepos ) {
        this.jdbc = jdbc;
        this.nurseRepos = nurseRepos;
    }

    @Override
    public List<Nurse> getAll() {
        List<Nurse> lstNurse = new ArrayList<>();
        nurseRepos.findAll().forEach(lstNurse::add);
        return lstNurse;
    }

    @Override
    public List<Nurse> find(String type, String value) {
        String sql ="SELECT * FROM nurse where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToNurse);
    }

    @Override
    public List<Nurse> findLike(String type, String value) {
        String sql ="SELECT * FROM nurse where " + type +" like '%" + value +"%';";
        return jdbc.query(sql,this::mapRowToNurse);
    }

    @Override
    public int add(Nurse nurse) {
        try{
            nurseRepos.save(nurse);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            nurseRepos.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, Nurse nurse) {
        try{
            if(!nurseRepos.findById(id).isPresent()) return 0;
            nurseRepos.update(nurse.getCmd(),
                    nurse.getName(),
                    nurse.getAddress(),
                    nurse.getBirthday(),
                    nurse.getPhone(),
                    nurse.getLevel(),
                    nurse.getSeniority(),
                    nurse.getType(),
                    id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    private Nurse mapRowToNurse(ResultSet rs, int rowNum)
            throws SQLException {
        return new Nurse(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getInt(8),
                rs.getString(9));
    }
}
