package hospital.service;


import hospital.model.Diseases;

import java.util.List;

public interface DiseasesService {
    public List<Diseases> getAll();

    public List<Diseases> find(String type, String value);

    public List<Diseases> findLike(String type, String value);

    public int add(Diseases diseases);

    public  int delete(int id);

    public int update(int id,Diseases diseases) ;

}
