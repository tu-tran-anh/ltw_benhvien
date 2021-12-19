package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "examination", schema = "ltwbs")
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idexamination;

	private int iddoctordiseases;
	private int idnurse;
	private int idpatient;
	private String dayin;
	private String dayout;
	private String type;

	public Examination() {
    }

	public Examination(int idexamination, int iddoctordiseases, int idnurse, int idpatient, String dayin, String dayout, String type) {
        this.idexamination = idexamination;
        this.iddoctordiseases = iddoctordiseases;
        this.idnurse = idnurse;
        this.idpatient = idpatient;
        this.dayin = dayin;
        this.dayout = dayout;
        this.type = type;
    }

}
