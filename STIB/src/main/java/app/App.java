package app;

import javafx.application.Application;
import javafx.stage.Stage;
import model.StationNetwork;
import presenter.Presenter;
import view.ViewFavoris;
import view.ViewMain;

public class App extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StationNetwork stationNetwork = new StationNetwork();
        ViewMain view = new ViewMain(stage);
        ViewFavoris viewFavoris = new ViewFavoris(new Stage());
        Presenter presenter = new Presenter(stationNetwork, view, viewFavoris);
        presenter.initialize();
    }
}
