package rezervare.services;

import rezervare.model.Loc;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRezervareObserver extends Remote {
    void update(List<Loc> all) throws RemoteException;
}
