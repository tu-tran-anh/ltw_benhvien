package hospital.service.impl;

import hospital.model.Medicine;
import hospital.model.Medicineperexam;
import hospital.repository.MedicinePerExamRepository;
import hospital.service.MedicinePerExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicinePerExamServiceImpl implements MedicinePerExamService {
    private MedicinePerExamRepository medicineRepos;
    private JdbcTemplate jdbc;

    @Autowired
    MedicinePerExamServiceImpl(JdbcTemplate jdbc, MedicinePerExamRepository medicineRepos ) {
        this.jdbc = jdbc;
        this.medicineRepos = medicineRepos;
    }

    @Override
    public List<Medicineperexam> getAll() {
        List<Medicineperexam> lstMedicine = new ArrayList<>();
        medicineRepos.findAll().forEach(lstMedicine::add);
        return lstMedicine;
    }

    @Override
    public List<Medicineperexam> find(String type, String value) {
        String sql ="SELECT * FROM medicineperexam where " + type +" = '" + value +"';";
        return jdbc.query(sql,this::mapRowToMedicine);
    }

    @Override
    public int add(Medicineperexam medicine) {
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
    public int update(int id, Medicineperexam medicine) {
        try{
            if(!medicineRepos.findById(id).isPresent()) return 0;
            medicineRepos.update(medicine.getIdmedicine(),medicine.getIdexamination(), id);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
    private Medicineperexam mapRowToMedicine(ResultSet rs, int rowNum)
            throws SQLException {
        return new Medicineperexam(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3));
    }
}
