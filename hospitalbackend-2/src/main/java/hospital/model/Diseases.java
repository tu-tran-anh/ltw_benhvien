package hospital.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "diseases", schema = "ltwbs")
public class Diseases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iddiseases;
    private String name;
    private String description;
    private String type;

    public Diseases() {
    }

    public Diseases(int iddiseases, String name, String description, String type) {
        this.iddiseases = iddiseases;
        this.name = name;
        this.description = description;
        this.type = type;
    }
     
}
