package hospital.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name= "doctor", schema = "ltwbs")
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iddoctor;

	private String name;
	private String birthday;
	private String cmd;
	private String address;
	private String level;
	private int seniority;
	private String type;

	public Doctor() {
	}

	public Doctor(int iddoctor, String name, String birthday, String cmd, String address, String level, int seniority,
				  String type) {
		this.iddoctor = iddoctor;
		this.name = name;
		this.birthday = birthday;
		this.cmd = cmd;
		this.address = address;
		this.level = level;
		this.seniority = seniority;
		this.type = type;
	}

}