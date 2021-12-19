package hospital.service.impl;

import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.repository.PatientRepository;
import hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepos;
    private JdbcTemplate jdbc;

    @Autowired
    PatientServiceImpl(JdbcTemplate jdbc, PatientRepository patientRepos ) {
        this.jdbc = jdbc;
        this.patientRepos = patientRepos;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> lstPatient = new ArrayList<>();
        patientRepos.findAll().forEach(lstPatient::add);
        return lstPatient;
    }

    @Override
    public List<Patient> find(String type, String value) {
        String sql ="SELECT * FROM patient where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToPatient);
    }

    @Override
    public List<Patient> findLike(String type, String value) {
        String sql ="SELECT * FROM patient where " + type +" like '%" + value +"%';";
        return jdbc.query(sql,this::mapRowToPatient);
    }

    @Override
    public int add(Patient patient) {
        try{
            patientRepos.save(patient);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            patientRepos.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, Patient patient) {
        try{
            if(!patientRepos.findById(id).isPresent()) return 0;
            patientRepos.update(patient.getCmd(),
                    patient.getName(),
                    patient.getBirthday(),
                    patient.getAddress(),
                    patient.getPhone(),
                    id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
    private Patient mapRowToPatient(ResultSet rs, int rowNum)
            throws SQLException {
        return new Patient(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6));
    }
}
