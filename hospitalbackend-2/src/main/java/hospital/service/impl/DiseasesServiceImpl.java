package hospital.service.impl;

import hospital.model.Diseases;
import hospital.repository.DiseasesRepository;
import hospital.service.DiseasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiseasesServiceImpl implements DiseasesService {
    private DiseasesRepository diseasesRepos;
    private JdbcTemplate jdbc;

    @Autowired
    DiseasesServiceImpl(JdbcTemplate jdbc, DiseasesRepository diseasesRepos ) {
        this.jdbc = jdbc;
        this.diseasesRepos = diseasesRepos;
    }

    @Override
    public List<Diseases> getAll() {
        List<Diseases> lstDiseases = new ArrayList<>();
        diseasesRepos.findAll().forEach(lstDiseases::add);
        return lstDiseases;
    }

    @Override
    public List<Diseases> find(String type, String value) {
        String sql ="SELECT * FROM diseases where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToDiseases);
    }

    @Override
    public List<Diseases> findLike(String type, String value) {
        String sql ="SELECT * FROM diseases where " + type +" like '%" + value +"%';";
        return jdbc.query(sql,this::mapRowToDiseases);
    }

    @Override
    public int add(Diseases diseases) {
        try{
            diseasesRepos.save(diseases);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            diseasesRepos.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, Diseases diseases) {
        try{
            if(!diseasesRepos.findById(id).isPresent()) return 0;
            diseasesRepos.update(diseases.getName(),
                    diseases.getDescription(),
                    diseases.getType(),
                    id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
    private Diseases mapRowToDiseases(ResultSet rs, int rowNum)
            throws SQLException {
        return new Diseases(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4));
    }
}
