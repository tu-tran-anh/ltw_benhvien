package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "doctordiseases", schema = "ltwbs")
public class DoctorDiseases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddoctordiseases;
    private int iddoctor;
    private int iddiseases;
    private String type;

    public DoctorDiseases() {
    }

    public DoctorDiseases(int iddoctordiseases, int iddoctor, int iddiseases, String type) {
        this.iddoctordiseases = iddoctordiseases;
        this.iddoctor = iddoctor;
        this.iddiseases = iddiseases;
        this.type = type;
    }

}
