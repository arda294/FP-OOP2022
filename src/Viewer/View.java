package Viewer;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface View {
    int HEIGHT = 600;
    int WIDTH = 800;

    Scene getScene();
    Stage getStage();
}
