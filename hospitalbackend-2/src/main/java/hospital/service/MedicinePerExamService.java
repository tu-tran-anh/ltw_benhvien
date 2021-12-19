package hospital.service;

import hospital.model.Medicineperexam;

import java.util.List;

public interface MedicinePerExamService {
    public List<Medicineperexam> getAll();

    public List<Medicineperexam> find(String type, String value);

    public int add(Medicineperexam medicine);

    public  int delete(int id);

    public int update(int id,Medicineperexam medicine) ;

}
