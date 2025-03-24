module it.runyourdog.runyourdogapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens it.runyourdog.runyourdogapp to javafx.fxml;
    exports it.runyourdog.runyourdogapp;
    exports it.runyourdog.runyourdogapp.GraphicController;
    opens it.runyourdog.runyourdogapp.GraphicController to javafx.fxml;
    exports it.runyourdog.runyourdogapp.Utils;
    opens it.runyourdog.runyourdogapp.Utils to javafx.fxml;
}