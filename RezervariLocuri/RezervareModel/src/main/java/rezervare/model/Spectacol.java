package rezervare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "Spectacole")
public class Spectacol implements HasID<Integer>, Serializable {

    @Id
    @Column( name = "Id")
    private Integer id;

    @Column( name = "Nume")
    private String nume;

    public Spectacol(){}

    public Spectacol(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                '}';
    }
}
