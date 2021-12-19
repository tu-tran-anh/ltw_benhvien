package hospital.service;

import hospital.model.Patient;

import java.util.List;

public interface PatientService {
    public List<Patient> getAll();

    public List<Patient> find(String type, String value);

    public List<Patient> findLike(String type, String value);

    public int add(Patient patient);

    public int delete(int id);

    public int update(int id,Patient patient) ;
}
