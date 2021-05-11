package rezervare.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rezervare.model.Client;
import rezervare.model.Manager;
import rezervare.services.IRezervareServer;
import rezervare.services.RezervareException;

import java.io.IOException;

public class LoginController {
    Stage primaryStage;
    private IRezervareServer server;

    @FXML
    TextField numeUtilizatorTextField;

    @FXML
    TextField parolaTextField;

    public void setServer(IRezervareServer s){
        server = s;
    }

    public void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    private Stage createWindow(AnchorPane rootLayout){
        Stage primaryStage = new Stage();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        return primaryStage;
    }

    public void handleLoginButtonAction(ActionEvent actionEvent){
        String numeUtilizator = numeUtilizatorTextField.getText();
        String parola = parolaTextField.getText();
        parolaTextField.setText("");
        if(numeUtilizator.startsWith("manager.")) {
            Manager manager = new Manager(numeUtilizator, parola);
            try{
                try{
                    FXMLLoader loaderSpectacole = new FXMLLoader(getClass().getClassLoader().getResource("spectacoleWindow.fxml"));
                    AnchorPane rootLayoutSpectacole = loaderSpectacole.load();
                    SpectacoleController spectacoleController = loaderSpectacole.getController();

                    spectacoleController.setServer(server);

                    Stage primaryStageSpectacole = createWindow(rootLayoutSpectacole);
                    spectacoleController.loadTable();
                    server.login(manager, spectacoleController);
                    primaryStageSpectacole.show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }catch (RezervareException e){
                showErrorMessage("Nume utilizator sau parola invalide!");
            }
        }
        else{
            Client client = new Client(numeUtilizator, parola);
            try{
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rezervareWindow.fxml"));
                    AnchorPane rootLayoutRezervare = loader.load();
                    RezervareController rezervareController = loader.getController();

                    rezervareController.setServer(server,numeUtilizator);

                    Stage primaryStageRezervare = createWindow(rootLayoutRezervare);

                    rezervareController.loadTable();
                    server.loginClient(client, rezervareController);
                    primaryStageRezervare.show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }catch (RezervareException e){
                showErrorMessage("Nume utilizator sau parola invalide!");
            }
        }


    }

    private void showErrorMessage(String s){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(s);
        message.showAndWait();
    }
}
