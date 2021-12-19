package hospital.service;

import hospital.model.Medicine;

import java.util.List;

public interface MedicineService {
    public List<Medicine> getAll();

    public List<Medicine> find(String type, String value);

    public List<Medicine> findLike(String type, String value);

    public int add(Medicine medicine);

    public  int delete(int id);

    public int update(int id,Medicine medicine) ;
}
