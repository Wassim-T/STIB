package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.StationData;
import model.dto.FavorisDto;
import model.dto.StationsDto;
import model.exception.RepositoryException;
import org.controlsfx.control.SearchableComboBox;
import presenter.Presenter;

import java.util.List;

public class ControllerFXML {

    private @FXML
    VBox vboxParent;

    private @FXML
    MenuBar menuBar;

    private @FXML
    ImageView imgNetwork;

    private @FXML
    HBox hboxParent;

    private @FXML
    VBox vboxChild;

    private @FXML
    ImageView imgLogo;

    private @FXML
    HBox hboxChild1;

    private @FXML
    Label labelOrigine;

    private @FXML
    Label labelDestination;

    private @FXML
    HBox hboxChild2;

    private @FXML
    SearchableComboBox<StationsDto> scbOrigine;

    private @FXML
    SearchableComboBox<StationsDto> scbDestination;

    private @FXML
    SearchableComboBox<FavorisDto> scbFavoris;

    private @FXML
    HBox hboxChild3;

    private @FXML
    Label labelFavoris;

    @FXML
    Button btnAjouterFavoris;

    @FXML
    Button btnSupprimerFavoris;

    @FXML
    Button btnRechercher;

    public void handleAjouterFavoris(Presenter presenter) {
        System.out.println("handleAjouterFavoris");
        btnAjouterFavoris.setOnAction((e) -> {
            presenter.handleAjouter();
        });
    }

    public void handleSupprimerFavoris(Presenter presenter) {
        System.out.println("handleSupprimerFavoris");
        btnSupprimerFavoris.setOnAction((e) -> {
            try {
                presenter.supprimer();
            } catch (RepositoryException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void handleRechercher(Presenter presenter) {
        btnRechercher.setOnAction((e) -> {
            try {
                presenter.search();
            } catch (RepositoryException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<StationData, String> colStations;

    @FXML
    private TableColumn<StationData, List<Integer>> colLignes;


    public void initialize(List<StationsDto> stations, List<FavorisDto> favoris) {
        tableView.getColumns().setAll(colStations, colLignes);
        colStations.setCellValueFactory(new PropertyValueFactory<>("stationName"));
        colLignes.setCellValueFactory(new PropertyValueFactory<>("stationLines"));
        scbOrigine.getItems().addAll(stations);
        scbDestination.getItems().addAll(stations);
        scbFavoris.getItems().addAll(favoris);
    }

    public void clearTable() {
        tableView.getItems().clear();
    }

    public void addFavoris(FavorisDto favoris) {
        scbFavoris.getItems().add(favoris);
    }

    public StationsDto originStation() {
        return scbOrigine.getValue();
    }

    public StationsDto destinationStation() {
        return scbDestination.getValue();
    }


    public FavorisDto favoris() {
        return scbFavoris.getValue();
    }

    public void addData(StationData data) {
        tableView.getItems().add(data);
    }


    public void supprimerFavoris() {
        //scbFavoris.getItems().removeAll(scbFavoris.getItems());
        scbFavoris.getItems().clear();
    }

    public void ajouterFavoris(List<FavorisDto> favoris) {
        ObservableList<FavorisDto> observableList = FXCollections.observableList(favoris);
        scbFavoris.setItems(observableList);
    }
}
