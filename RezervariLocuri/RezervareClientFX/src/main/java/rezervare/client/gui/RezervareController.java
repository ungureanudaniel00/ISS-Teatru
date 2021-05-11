package rezervare.client.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import rezervare.model.Loc;
import rezervare.services.IRezervareObserver;
import rezervare.services.IRezervareServer;
import rezervare.services.RezervareException;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RezervareController extends UnicastRemoteObject implements Initializable, IRezervareObserver, Serializable {

    private IRezervareServer server;
    private String numeUtilizator;
    private ObservableList<Loc> modelLoc = FXCollections.observableArrayList();

    @FXML
    TableView<Loc> locTableView;

    @FXML
    TableColumn<Loc, Integer> idLocColumn;

    @FXML
    TableColumn<Loc, String> lojaLocColumn;

    @FXML
    TableColumn<Loc, String> randLocColumn;

    @FXML
    TableColumn<Loc, Integer> numarLocColumn;

    @FXML
    TableColumn<Loc, String> stareLocColumn;

    @FXML
    TableColumn<Loc, Integer> pretLocColumn;

    @FXML
    TextField spectacolActualTextField;

    private Loc loc;

    public RezervareController() throws RemoteException {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idLocColumn.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("id"));
        lojaLocColumn.setCellValueFactory(new PropertyValueFactory<Loc, String>("loja"));
        randLocColumn.setCellValueFactory(new PropertyValueFactory<Loc, String>("rand"));
        numarLocColumn.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("numarLoc"));
        stareLocColumn.setCellValueFactory(new PropertyValueFactory<Loc, String>("stare"));
        pretLocColumn.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("pret"));
        locTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null)
                getLoc(newValue);
        });
        //spectacolActualTextField.setText(spectacolSetat);
        spectacolActualTextField.setEditable(false);
    }

    private void getLoc(Loc newValue) {
        this.loc = newValue;
    }

    public void loadTable(){
        try{
            List<Loc> locuri = server.toateLocurile();
            modelLoc.setAll(locuri);
            locTableView.setItems(modelLoc);
            String spectacolSetat = server.cautaSpectacolSetat();
            spectacolActualTextField.setText(spectacolSetat);
            spectacolActualTextField.setEditable(false);
        } catch (RezervareException e) {
            e.printStackTrace();
        }
    }

    public void setServer(IRezervareServer server, String userName){
        this.server = server;
        this.numeUtilizator = userName;
    }

    public void handleRezervaBiletButtonAction(ActionEvent actionEvent) {
        String spectacolActual = spectacolActualTextField.getText();
        try {
            String res = server.adaugaRezervare(numeUtilizator, spectacolActual, loc);
            modelLoc.setAll(server.toateLocurile());
            showInformationMessage(res);
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
        modelLoc.setAll(all);
    }
}
