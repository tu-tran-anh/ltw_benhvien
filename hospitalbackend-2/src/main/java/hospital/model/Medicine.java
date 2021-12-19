package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "medicine", schema = "ltwbs")
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idmedicine;

	private String name;
	private int cost;
	private String type;

	public Medicine() {
    }

	public Medicine(int idmedicine, String name, int cost, String type) {
        this.idmedicine = idmedicine;
        this.name = name;
        this.cost = cost;
        this.type = type;
    }
}
