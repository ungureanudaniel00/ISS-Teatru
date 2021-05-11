package rezervare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "DaySpectacol")
public class DaySpectacol implements HasID<Integer>, Serializable {
    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Nume")
    private String nume;

    public DaySpectacol(){}

    public DaySpectacol(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "DaySpectacol{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                '}';
    }
}
