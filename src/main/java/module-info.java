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
    requires java.management;
    requires java.sql;

    opens it.runyourdog.runyourdogapp to javafx.fxml;
    exports it.runyourdog.runyourdogapp;
    exports it.runyourdog.runyourdogapp.graphiccontroller;
    opens it.runyourdog.runyourdogapp.graphiccontroller to javafx.fxml;
    exports it.runyourdog.runyourdogapp.utils;
    opens it.runyourdog.runyourdogapp.utils to javafx.fxml;
}