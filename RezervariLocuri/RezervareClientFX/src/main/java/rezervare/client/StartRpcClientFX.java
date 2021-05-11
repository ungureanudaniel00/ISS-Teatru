package rezervare.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rezervare.client.gui.LoginController;
import rezervare.services.IRezervareServer;

public class StartRpcClientFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IRezervareServer server = (IRezervareServer) factory.getBean("rezervareService");

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loginWindow.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setServer(server);
            primaryStage.setScene(new Scene(root));
            loginController.setStage(primaryStage);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
