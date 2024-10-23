module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.controlsfx.controls;

    opens app to javafx.fxml;
    exports app;

    exports view;
    opens view to javafx.fxml;

    exports model;
    opens model to javafx.fxml;

    exports model.exception;
    opens model.exception to javafx.fxml;

    exports model.repository;
    opens model.repository to javafx.fxml;

    exports model.dto;
    opens model.dto to javafx.fxml;

    exports presenter;
    opens presenter to javafx.fxml;
}
