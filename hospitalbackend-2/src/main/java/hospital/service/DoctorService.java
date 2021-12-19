package hospital.service;

import hospital.model.Doctor;

import java.util.List;

public interface DoctorService {
    public List<Doctor> getAll();

    public List<Doctor> find(String type, String value);

    public List<Doctor> findLike(String type, String value);

    public int add(Doctor doctor);

    public  int delete(int id);

    public int update(int id,Doctor doctor) ;

    public List<Doctor> getDoctorHeal(String iddiseases);
}
