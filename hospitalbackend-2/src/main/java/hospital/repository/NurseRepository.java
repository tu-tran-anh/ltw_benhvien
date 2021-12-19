package hospital.repository;

import hospital.model.Nurse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface NurseRepository extends CrudRepository<Nurse, Integer> {
    @Transactional
    @Modifying
    @Query(value ="UPDATE nurse n SET n.cmd=:cmd,n.name=:name,n.address=:address,n.birthday=:birthday,n.phone=:phone,n.level=:level,n.seniority=:seniority,n.type=:type WHERE n.idnurse=:id", nativeQuery = true)
    public void update(String cmd, String name, String address, String birthday, String phone, String level, int seniority, String type,int id);
}
