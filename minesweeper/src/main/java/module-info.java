module com.example.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;

    opens com.game.minesweeper to javafx.fxml;
    exports com.game.minesweeper;
    exports com.game.minesweeper.UI;
    opens com.game.minesweeper.UI to javafx.fxml;
    exports com.game.minesweeper.BLL;
    opens com.game.minesweeper.BLL to javafx.fxml;
    exports com.game.minesweeper.DAL;
    opens com.game.minesweeper.DAL to javafx.fxml;
}