package hospital.service.impl;

import hospital.model.Medicine;
import hospital.repository.MedicineRepository;
import hospital.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    private MedicineRepository medicineRepos;
    private JdbcTemplate jdbc;

    @Autowired
    MedicineServiceImpl(JdbcTemplate jdbc, MedicineRepository medicineRepos ) {
        this.jdbc = jdbc;
        this.medicineRepos = medicineRepos;
    }

    @Override
    public List<Medicine> getAll() {
        List<Medicine> lstMedicine = new ArrayList<>();
        medicineRepos.findAll().forEach(lstMedicine::add);
        return lstMedicine;
    }

    @Override
    public List<Medicine> find(String type, String value) {
        String sql ="SELECT * FROM medicine where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToMedicine);
    }

    @Override
    public List<Medicine> findLike(String type, String value) {
        String sql ="SELECT * FROM medicine where " + type +" like '%" + value +"%';";
        return jdbc.query(sql,this::mapRowToMedicine);
    }

    @Override
    public int add(Medicine medicine) {
        try{
            medicineRepos.save(medicine);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int delete(int id) {
        try{
            medicineRepos.deleteById(id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int update(int id, Medicine medicine) {
        try{
            if(!medicineRepos.findById(id).isPresent()) return 0;
            medicineRepos.update(medicine.getName(), medicine.getCost(), medicine.getType(), id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
    private Medicine mapRowToMedicine(ResultSet rs, int rowNum)
            throws SQLException {
        return new Medicine(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getString(4));
    }
}
