package hospital.service;

import hospital.model.DoctorDiseases;

import java.util.List;

public interface DoctorDiseasesService {
    public List<DoctorDiseases> getAll();

    public List<DoctorDiseases> find(String type, String value);

    public List<DoctorDiseases> findAllByIddoctorAndIddiseases(int iddoctor, int iddiseases);

    public int add(DoctorDiseases doctorDiseases);

    public int delete(int id);

    public int update(int id, DoctorDiseases doctorDiseases);

    public int updateType(int iddoctor, String uType );
}
