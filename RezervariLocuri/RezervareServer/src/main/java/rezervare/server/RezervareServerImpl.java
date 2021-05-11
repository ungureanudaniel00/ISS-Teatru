package rezervare.server;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import rezervare.model.*;
import rezervare.persistance.repository.*;
import rezervare.persistance.repository.jdbc.RezervareRepo;
import rezervare.services.IRezervareObserver;
import rezervare.services.IRezervareServer;
import rezervare.services.RezervareException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RezervareServerImpl implements IRezervareServer {
    private ManagerRepository managerRepository;
    private SpectacolRepository spectacolRepository;
    private ClientRepository clientRepository;
    private LocRepository locRepository;
    private RezervareRepository rezervareRepository;
    private Map<String, IRezervareObserver> loggedClients;

    public RezervareServerImpl(ManagerRepository managerRepository, SpectacolRepository spectacolRepository, ClientRepository clientRepository, LocRepository locRepository, RezervareRepository rezervareRepository) {
        this.managerRepository = managerRepository;
        this.spectacolRepository = spectacolRepository;
        this.clientRepository = clientRepository;
        this.locRepository = locRepository;
        this.rezervareRepository = rezervareRepository;
        loggedClients = new ConcurrentHashMap<>();
//        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Manager.class).buildSessionFactory();
//        managerRepository.setSessionFactory(sessionFactory);
//        spectacolRepository.setSessionFactory(sessionFactory);
    }

    @Override
    public synchronized void login(Manager manager, IRezervareObserver client) throws RezervareException {
        Manager manager1 = managerRepository.verificareCont(manager.getId(), manager.getParola());
        if(manager1 == null) {
            System.out.println("Dau eroare");
            throw new RezervareException("null");
        }
    }

    @Override
    public synchronized void loginClient(Client client, IRezervareObserver clientObserver) throws RezervareException {
        Client client1 = clientRepository.verificareCont(client.getId(), client.getParola());
        if(client1 == null){
            throw new RezervareException("null");
        }
        else{
            loggedClients.put(client1.getId(), clientObserver);
        }
    }

    @Override
    public List<Spectacol> toateSpectacolele() throws RezervareException {
        List<Spectacol> all = spectacolRepository.findAll();
        return all;
    }

    @Override
    public List<Loc> toateLocurile() throws RezervareException {
        List<Loc> all = locRepository.findAll();
        return all;
    }

    @Override
    public void adaugaSpectacol(Spectacol spectacol) throws RezervareException {
        spectacolRepository.save(spectacol);
    }

    @Override
    public void stergeSpectacol(Integer id) throws RezervareException {
        spectacolRepository.delete(id);
    }

    private Integer getCorrectId(){
        List<Rezervare> lista = rezervareRepository.findAll();
        int lungime = lista.size();
        return lungime+1;
    }

    @Override
    public String adaugaRezervare(String numeUtilizator, String spectacolActual, Loc loc) throws RezervareException {
        if(loc == null){
            return "Trebuie sa selectati un loc!";
        }
        Client c = clientRepository.findOne(numeUtilizator);
        Spectacol s = spectacolRepository.findByName(spectacolActual);
        Integer id = getCorrectId();
        Rezervare r = new Rezervare(id,c,s,loc);
        if(loc.getStare().equals("Liber")) {
            rezervareRepository.save(r);
            locRepository.updateStare(loc.getId());
            notifyAllObservers();
        }
        else {
            return "Locul e ocupat deja!";
        }
        return "Rezervare cu succes!";
    }

    @Override
    public void modificaSpectacol(Spectacol spectacol) {
        spectacolRepository.update(spectacol);
    }

    @Override
    public void seteazaSpectacol(Spectacol spectacolSetat) throws RezervareException {
        Spectacol s = new Spectacol(1,spectacolSetat.getNume());
        spectacolRepository.setSpectacol(s);
    }

    @Override
    public String cautaSpectacolSetat() {
        DaySpectacol daySpectacol = spectacolRepository.findDaySpectacol();
        String nume = daySpectacol.getNume();
        return nume;
    }

    @Override
    public void updateLocuriLibere() {
        locRepository.updateLocuriLibere();
    }

    private void notifyAllObservers(){
        List<Loc> allLocuri = locRepository.findAll();
        for(IRezervareObserver obs : loggedClients.values()){
            try{
                obs.update(allLocuri);
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }
}
