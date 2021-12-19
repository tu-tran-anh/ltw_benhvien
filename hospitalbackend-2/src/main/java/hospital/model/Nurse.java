package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "nurse", schema = "ltwbs")
public class Nurse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idnurse;

	private String cmd;
	private String name;
	private String birthday;
	private String address;
	private String level;
	private String phone;
	private int seniority;
	private String type;

	public Nurse() {
	}

	public Nurse(int idnurse, String cmd, String name, String address, String birthday, String phone, String level,
			int seniority, String type) {
		this.idnurse = idnurse;
		this.cmd = cmd;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.level = level;
		this.phone = phone;
		this.seniority = seniority;
		this.type = type;
	}

}
