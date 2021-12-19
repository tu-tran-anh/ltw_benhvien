package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "medicineperexam", schema = "ltwbs")
public class Medicineperexam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idmedicineperexam;
    private int idmedicine;
    private int idexamination;

    public Medicineperexam() {
    }

    public Medicineperexam(int idmedicineperexam, int idmedicine, int idexamination) {
        this.idmedicineperexam = idmedicineperexam;
        this.idmedicine = idmedicine;
        this.idexamination = idexamination;
    }

}
