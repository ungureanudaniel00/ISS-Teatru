package rezervare.services;

import rezervare.model.Client;
import rezervare.model.Loc;
import rezervare.model.Manager;
import rezervare.model.Spectacol;

import java.util.List;

public interface IRezervareServer {
    void login(Manager manager, IRezervareObserver client) throws RezervareException;
    void loginClient(Client client, IRezervareObserver clientObserver) throws RezervareException;
    List<Spectacol> toateSpectacolele() throws RezervareException;
    List<Loc> toateLocurile() throws RezervareException;
    void adaugaSpectacol(Spectacol spectacol) throws RezervareException;
    void stergeSpectacol(Integer id) throws RezervareException;
    String adaugaRezervare(String numeUtilizator, String spectacolActual, Loc loc) throws RezervareException;

    void modificaSpectacol(Spectacol spectacol) throws RezervareException;

    void seteazaSpectacol(Spectacol spectacolSetat) throws RezervareException;

    String cautaSpectacolSetat();

    void updateLocuriLibere();
}
