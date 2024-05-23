module org.jvmlthread.javamultithreading {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.logging;
    requires com.google.gson;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens org.jvmlthread.javamultithreading.server;
    opens org.jvmlthread.javamultithreading.game;
    opens org.jvmlthread.javamultithreading.client;
    exports org.jvmlthread.javamultithreading.client;
}