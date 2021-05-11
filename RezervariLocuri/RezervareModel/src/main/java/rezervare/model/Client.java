package rezervare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "Clienti")
public class Client implements HasID<String>, Serializable {

    @Id
    @Column(name = "NumeUtilizator")
    private String numeUtilizator;

    @Column(name = "Parola")
    private String parola;

    @Column(name = "Nume")
    private String nume;

    public Client(){}

    public Client(String numeUtilizator, String parola, String nume) {
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.nume = nume;
    }

    public Client(String numeUtilizator, String parola){
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
    }

    @Override
    public String getId() {
        return numeUtilizator;
    }

    @Override
    public void setId(String s) {
        this.numeUtilizator = s;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Client{" +
                "numeUtilizator='" + numeUtilizator + '\'' +
                ", parola='" + parola + '\'' +
                ", nume='" + nume + '\'' +
                '}';
    }
}
