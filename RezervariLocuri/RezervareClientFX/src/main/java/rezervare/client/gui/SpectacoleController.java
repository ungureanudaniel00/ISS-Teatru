package rezervare.client.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import rezervare.model.Loc;
import rezervare.model.Spectacol;
import rezervare.services.IRezervareObserver;
import rezervare.services.IRezervareServer;
import rezervare.services.RezervareException;

import javax.persistence.Id;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SpectacoleController extends UnicastRemoteObject implements Initializable,IRezervareObserver, Serializable {

    private IRezervareServer server;
    private ObservableList<Spectacol> modelSpectacol = FXCollections.observableArrayList();
    private Spectacol spectacolSetat;

    @FXML
    TableView<Spectacol> spectacolTableView;

    @FXML
    TableColumn<Spectacol, Integer> idSpectacolColumn;

    @FXML
    TableColumn<Spectacol, String> numeSpectacolColumn;

    @FXML
    TextField idSpectacolTextField;

    @FXML
    TextField numeSpectacolTextField;

    @FXML
    TextField idSpectacolStersTextField;

    @FXML
    TextField idSpectacolModificaTextField;

    @FXML
    TextField numeNouSpectacolTextField;

    public SpectacoleController() throws RemoteException {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idSpectacolColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, Integer>("id"));
        numeSpectacolColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("nume"));
        spectacolTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                getSpectacol(newValue);
        });
    }

    private void getSpectacol(Spectacol newValue) {
        this.spectacolSetat = newValue;
    }


    public void loadTable(){
        try{
            List<Spectacol> spectacole = server.toateSpectacolele();
            modelSpectacol.setAll(spectacole);
            spectacolTableView.setItems(modelSpectacol);
        } catch (RezervareException e) {
            e.printStackTrace();
        }
    }

    public void setServer(IRezervareServer server){
        this.server = server;
    }

    public void handleAdaugaSpectacolButtonAction(ActionEvent actionEvent) {
        Integer id = Integer.parseInt(idSpectacolTextField.getText());
        String nume = numeSpectacolTextField.getText();
        Spectacol spectacol = new Spectacol(id,nume);
        try {
            server.adaugaSpectacol(spectacol);
            modelSpectacol.setAll(StreamSupport.stream(server.toateSpectacolele().spliterator(), false).collect(Collectors.toList()));
        } catch (RezervareException e) {
            e.printStackTrace();
        }
    }

    public void handleStergeSpectacolButtonAction(ActionEvent actionEvent) {
        Integer id = Integer.parseInt(idSpectacolStersTextField.getText());
        try {
            server.stergeSpectacol(id);
            modelSpectacol.setAll(StreamSupport.stream(server.toateSpectacolele().spliterator(), false).collect(Collectors.toList()));
        } catch (RezervareException e) {
            e.printStackTrace();
        }
    }

    public void handleModificaSpectacolButtonAction(ActionEvent actionEvent) {
        Integer id = Integer.parseInt(idSpectacolModificaTextField.getText());
        String nume = numeNouSpectacolTextField.getText();
        Spectacol spectacol = new Spectacol(id,nume);
        try{
            server.modificaSpectacol(spectacol);
            modelSpectacol.setAll(StreamSupport.stream(server.toateSpectacolele().spliterator(), false).collect(Collectors.toList()));
        }catch (RezervareException e){
            e.printStackTrace();
        }
    }

    public void handleSeteazaSpectacolButtonAction(ActionEvent actionEvent) {
        try{
            if(spectacolSetat == null){
                showInformationMessage("Spectacolul nu a fost selectat!");
            }
            else {
                server.seteazaSpectacol(spectacolSetat);
                server.updateLocuriLibere();
            }
        }catch (RezervareException e){
            e.printStackTrace();
        }
    }

    private void showInformationMessage(String s){
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Eroare");
        message.setContentText(s);
        message.showAndWait();
    }

    @Override
    public void update(List<Loc> all) throws RemoteException {

    }
}
