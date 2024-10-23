package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dto.FavorisDto;
import model.exception.RepositoryException;
import presenter.Presenter;

import java.util.List;

public class ControllerFavorisFXML {


    @FXML
    Label labelFavoris;

    @FXML
    TextField textFieldFavoris;

    @FXML
    Button boutonFavoris;

    public void handler(Presenter presenter) {
        boutonFavoris.setOnAction((e) -> {
            try {
                presenter.SaveFavoris();
            } catch (RepositoryException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public String getName() {
        return textFieldFavoris.getText();
    }
}
