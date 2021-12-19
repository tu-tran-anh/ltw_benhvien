package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "patient", schema = "ltwbs")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idpatient;

	private String cmd;
	private String name;
	private String birthday;
	private String address;
	private String phone;

	public Patient(int idpatient, String cmd, String name, String birthday, String address, String phone) {
        this.idpatient = idpatient;
        this.cmd = cmd;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
    }

	public Patient() {
    }

}
