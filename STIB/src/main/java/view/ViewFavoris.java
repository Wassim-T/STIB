package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.config.ConfigManager;
import model.dto.FavorisDto;
import presenter.Presenter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ViewFavoris {

    private ControllerFavorisFXML controllerFavorisFXML;

    ConfigManager configManager;

    private Stage stage;

    public ViewFavoris(Stage stage) throws IOException {
        // configManager.getProperties("fxml");
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/stibFavoris.fxml")));
        Parent root = loader.load();
        controllerFavorisFXML = loader.getController();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
    }

    public void handler(Presenter presenter) {
        controllerFavorisFXML.handler(presenter);
    }

    public String getName() {
        return controllerFavorisFXML.getName();
    }

    public void close() {
        stage.close();
    }

    public void show() {
        stage.show();
    }

}
