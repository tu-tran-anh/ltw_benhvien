package hospital.service.impl;

import hospital.model.DoctorDiseases;
import hospital.repository.DoctorDiseasesRepository;
import hospital.service.DoctorDiseasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorDiseasesServiceImpl implements DoctorDiseasesService {
    private DoctorDiseasesRepository doctorDiseasesRepos;
    private JdbcTemplate jdbc;

    @Autowired
    DoctorDiseasesServiceImpl(JdbcTemplate jdbc, DoctorDiseasesRepository doctorDiseasesRepos ) {
        this.jdbc = jdbc;
        this.doctorDiseasesRepos = doctorDiseasesRepos;
    }
    @Override
    public List<DoctorDiseases> getAll() {
        List<DoctorDiseases> lstDoctorDiseases = new ArrayList<>();
        doctorDiseasesRepos.findAll().forEach(lstDoctorDiseases::add);
        return lstDoctorDiseases;
    }

    @Override
    public List<DoctorDiseases> find(String type, String value) {
        String sql ="SELECT * FROM doctordiseases where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToDoctorDiseases);
    }

    @Override
    public List<DoctorDiseases> findAllByIddoctorAndIddiseases(int iddoctor, int iddiseases ) {
        List<DoctorDiseases> lstDoctorDiseases = new ArrayList<>();
        doctorDiseasesRepos.findAllByIddoctorAndIddiseases(iddoctor, iddiseases).forEach(lstDoctorDiseases::add);
        return lstDoctorDiseases;
    }

    @Override
    public int add(DoctorDiseases doctorDiseases) {
        try{
            doctorDiseasesRepos.save(doctorDiseases);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            doctorDiseasesRepos.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, DoctorDiseases doctorDiseases) {
        try{
            if(!doctorDiseasesRepos.findById(id).isPresent()) return 0;
            doctorDiseasesRepos.update(doctorDiseases.getIddoctor(),
                    doctorDiseases.getIddiseases(),
                    doctorDiseases.getType(),
                    id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int updateType(int iddoctor, String uType) {
        try{
            if(!doctorDiseasesRepos.findAllByIddoctor(iddoctor).isPresent()) return 0;
            doctorDiseasesRepos.updateType(uType, iddoctor);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    private DoctorDiseases mapRowToDoctorDiseases(ResultSet rs, int rowNum) throws SQLException {
        return new DoctorDiseases(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getString(4));
    }
}
