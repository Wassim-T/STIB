package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StationData;
import model.dto.FavorisDto;
import model.dto.StationsDto;
import presenter.Presenter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ViewMain {

    private ControllerFXML controller;

    public ViewMain(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/stib.fxml")));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void initialize(List<StationsDto> stations, List<FavorisDto> favoris) {
        controller.initialize(stations, favoris);
    }

    public StationsDto originStation() {
        return controller.originStation();
    }

    public StationsDto destinationStation() {
        return controller.destinationStation();
    }

    public FavorisDto favorisDto() {
        return controller.favoris();
    }

    public void clearTable() {
        controller.clearTable();
    }

    public void addData(StationData stationData) {
        controller.addData(stationData);
    }

    public void handleRechercher(Presenter presenter) {
        controller.handleRechercher(presenter);
    }

    public void handleAjouter(Presenter presenter) {
        controller.handleAjouterFavoris(presenter);
    }

    public void handleSupprimer(Presenter presenter) {
        controller.handleSupprimerFavoris(presenter);
    }

    public void addFavoris(FavorisDto favoris) {
        controller.addFavoris(favoris);
    }

    public void supprimerFavoris() {
        controller.supprimerFavoris();
    }

    public void ajouterFavoris(List<FavorisDto> favoris) {
        controller.ajouterFavoris(favoris);
    }
}
