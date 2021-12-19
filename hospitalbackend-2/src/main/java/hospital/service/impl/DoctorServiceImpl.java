package hospital.service.impl;

import hospital.model.Doctor;
import hospital.repository.DoctorRepository;
import hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepo;
    private JdbcTemplate jdbc;
    @Autowired
    public DoctorServiceImpl(JdbcTemplate jdbc, DoctorRepository doctorRepo ) {
        this.jdbc = jdbc;
        this.doctorRepo = doctorRepo;
    }
    @Override
    public List<Doctor> getAll() {
        List<Doctor> lstDoctor = new ArrayList<>();
        doctorRepo.findAll().forEach(lstDoctor::add);
        return lstDoctor;
    }

    @Override
    public List<Doctor> find(String type, String value) {
        String sql ="SELECT * FROM doctor where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToDoctor);
    }

    @Override
    public List<Doctor> findLike(String type, String value) {
        String sql ="SELECT * FROM doctor where " + type +" like '%" + value +"%';";
        return jdbc.query(sql,this::mapRowToDoctor);
    }

    @Override
    public int add(Doctor doctor) {
        try{
            doctorRepo.save(doctor);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            doctorRepo.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, Doctor doctor) {
        try{
            if(!doctorRepo.findById(id).isPresent()) return 0;
            doctorRepo.update(doctor.getName(),
                    doctor.getBirthday(),
                    doctor.getCmd(),
                    doctor.getAddress(),
                    doctor.getLevel(),
                    doctor.getSeniority(),
                    doctor.getType(),
                    id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public List<Doctor> getDoctorHeal(String iddiseases) {
        List<Doctor> lstDoctor = new ArrayList<>();
        doctorRepo.findDoctorHeal(iddiseases).forEach(lstDoctor::add);
        return lstDoctor;
    }

    private Doctor mapRowToDoctor(ResultSet rs, int rowNum)
            throws SQLException {
        return new Doctor(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7),
                rs.getString(8));
    }
}
