package rezervare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Locuri")
public class Loc implements HasID<Integer>, Serializable {

    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Loja")
    private String loja;

    @Column(name = "Rand")
    private String rand;

    @Column(name = "NumarLoc")
    private Integer numarLoc;

    @Column(name = "Stare")
    private String stare;

    @Column(name = "Pret")
    private Integer pret;

    public Loc(){}

    public Loc(Integer id, String loja, String rand, Integer numarLoc, String stare, Integer pret) {
        this.id = id;
        this.loja = loja;
        this.rand = rand;
        this.numarLoc = numarLoc;
        this.stare = stare;
        this.pret = pret;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public Integer getNumarLoc() {
        return numarLoc;
    }

    public void setNumarLoc(Integer numarLoc) {
        this.numarLoc = numarLoc;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public Integer getPret() {
        return pret;
    }

    @Override
    public String toString() {
        return "Loc{" +
                "id=" + id +
                ", loja='" + loja + '\'' +
                ", rand='" + rand + '\'' +
                ", numarLoc=" + numarLoc +
                ", stare='" + stare + '\'' +
                ", pret=" + pret +
                '}';
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

}
