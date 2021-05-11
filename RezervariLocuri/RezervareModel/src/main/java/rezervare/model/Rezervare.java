package rezervare.model;

import javax.persistence.*;

@Entity
@Table(name = "Rezervari")
public class Rezervare implements HasID<Integer> {

    @Id
    @Column(name = "Id")
    private Integer id;

    @ManyToOne(targetEntity = Client.class)
    //@JoinColumn(name = "NumeUtilizator")
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Client", referencedColumnName = "NumeUtilizator")
    private Client client;

    @ManyToOne(targetEntity = Spectacol.class)
//    @JoinColumn(name = "Id")
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Spectacol", referencedColumnName = "Id")
    private Spectacol spectacol;

    @ManyToOne(targetEntity = Loc.class)
//    @JoinColumn(name = "Id")
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Loc", referencedColumnName = "Id")
    private Loc loc;

    public Rezervare(){}

    public Rezervare(Integer id, Client client, Spectacol spectacol, Loc loc) {
        this.id = id;
        this.client = client;
        this.spectacol = spectacol;
        this.loc = loc;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }


    @Override
    public String toString() {
        return "Rezervare{" +
                "id=" + id +
                ", client=" + client +
                ", spectacol=" + spectacol +
                ", loc=" + loc +
                '}';
    }
}
