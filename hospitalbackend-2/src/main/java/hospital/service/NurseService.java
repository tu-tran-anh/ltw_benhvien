package hospital.service;

import hospital.model.Nurse;

import java.util.List;

public interface NurseService {
    public List<Nurse> getAll();

    public List<Nurse> find(String type, String value);

    public List<Nurse> findLike(String type, String value);

    public int add(Nurse nurse);

    public  int delete(int id);

    public int update(int id,Nurse nurse) ;
}
