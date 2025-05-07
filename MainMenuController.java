import Model.*;
import View.BoardView;
import View.MainMenuView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenuController {
    private MainMenuView mainMenuView;
    private MainMenuModel mainMenuModel;

    public MainMenuController() {
        mainMenuModel = new MainMenuModel();
        mainMenuView = new MainMenuView();
        addListeners();
        checkSaveFile();
        mainMenuView.setVisible(true);
    }

    private void addListeners() {
        mainMenuView.getNewGameButton().addActionListener(e -> startNewGame());
        mainMenuView.getLoadGameButton().addActionListener(e -> loadGame());
    }

    private void checkSaveFile() {
        File saveFile = new File("save.txt");
        if (saveFile.exists()) {
            mainMenuView.getLoadGameButton().setEnabled(true);
        }
    }

    private void startNewGame() {
        // Close the Main Menu
        mainMenuView.dispose();

        // Create and initialize BoardController
        BoardModel boardModel = mainMenuModel.initialize();
        BoardView boardView = new BoardView();   
        new BoardController(boardView, boardModel);
    }

    private void loadGame() {
        // Close the Main Menu
        mainMenuView.dispose();

        // Load saved game data into BoardModel
        BoardView boardView = new BoardView();
        try {
            BoardModel boardModel = mainMenuModel.loadGame();
            new BoardController(boardView, boardModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    mainMenuView,
                    "There was an error loading your game. Starting new game.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            BoardModel boardModel = mainMenuModel.initialize();
            new BoardController(boardView, boardModel);
        }
    }
}