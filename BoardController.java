import Model.BoardModel;
import Model.Piece;
import View.BoardView;
import java.awt.KeyboardFocusManager;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class BoardController {
    private BoardView boardView;
    private BoardModel boardModel;

    public BoardController(BoardView boardView, BoardModel boardModel) {
        this.boardView = boardView;
        this.boardModel = boardModel;
        initializeBoardView();
        addButtonListeners();
        addSaveButtonListener(); //
        addKeyListeners(); // (Done by Zikry)
        addWindowListener();
        addQuitButtonListener();
        addResetButtonListener();
    }

    // Initializes the board view by populating it with the current pieces from the model. (Done by Jarett Tan)
    public void initializeBoardView() {
        boardView.initialize(boardModel.getPieces());
    }

    public void addButtonListeners() {
        for (int i = 0; i < boardView.getBoardGrid().size(); i++) {
            boardView.getBoardGrid().get(i).addActionListener(e -> {
                // Determine the row and column of the clicked button
                int gridRow = boardView.getBoardGrid().indexOf((JButton) e.getSource()) / 5;
                int gridColumn = boardView.getBoardGrid().indexOf((JButton) e.getSource()) % 5;

                // Handle the logic based on the current mode of the board
                //
                if (boardModel.getCurrentMode() == BoardModel.Mode.PIECE_SELECTION && !boardModel.isWonGame()) {
                    Piece selectedPiece = null;

                    // Check if a piece is at the clicked position
                    for (int j = 0; j < boardModel.getPieces().size(); j++) {
                        if (boardModel.getPieces().get(j).getRow() == gridRow && boardModel.getPieces().get(j).getColumn() == gridColumn) {
                            selectedPiece = boardModel.getPieces().get(j);
                            break;
                        }
                    }

                    // If a valid piece is selected, highlight its possible moves
                    if (selectedPiece != null) {
                        if (boardModel.isTurnBlue() == selectedPiece.getIsBlue()) {
                            ArrayList<int[]> ranges = selectedPiece.getPieceMovementRange(boardModel.getPieces());
                            boardView.showPieceRange(ranges);

                            if (!ranges.isEmpty()) {
                                boardModel.setCurrentMode(BoardModel.Mode.PIECE_MOVE);
                                boardModel.setSelectedPiece(selectedPiece);
                                boardModel.setSelectedPieceRange(ranges);
                            }
                        }
                    }
                } else if (!boardModel.isWonGame()) { //
                    // If in PIECE_MOVE mode, handle the move action
                    if (boardModel.isWithinRange(gridRow, gridColumn)) {
                        // Set win to false. Done by
                        boolean win = false;
                        // Update the board view and model after a successful move
                        boardView.updateMove(boardModel.getSelectedPiece().getRow(), boardModel.getSelectedPiece().getColumn(), gridRow, gridColumn);
                        win = boardModel.movePiece(gridRow, gridColumn); //
                        boardView.showPieceRange(new ArrayList<>());
                        boardModel.setCurrentMode(BoardModel.Mode.PIECE_SELECTION);
                        boardModel.setSelectedPiece(null);
                        boardModel.setSelectedPieceRange(null);
                        boardModel.updatePiecesState();
                        if (win) {
                            boardView.clearIcons();
                            boardView.updateIcons(boardModel.getPieces());
                            boardModel.setWonGame(true);
                            String winnerString = boardModel.isTurnBlue() ? "Blue" : "Red";
                            // Show dialog for new game when a player wins
                            int result = JOptionPane.showConfirmDialog(
                                    boardView.getBoardWindow(),
                                    winnerString + " is the winner!\nGame over! Do you want to start a new game?",
                                    "New Game",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (result == JOptionPane.YES_OPTION) {
                                restartGame(); // Restart the game
                            }
                        } else {
                            boardModel.mirrorPieces(); //
                            boardModel.increaseTurns();
                            boardModel.toggleTurn();
                            boardView.clearIcons();
                            boardView.updateIcons(boardModel.getPieces());
                        }
                    } else {
                        // Reset to PIECE_SELECTION mode if the move is invalid
                        boardModel.setCurrentMode(BoardModel.Mode.PIECE_SELECTION);
                        boardModel.setSelectedPiece(null);
                        boardModel.setSelectedPieceRange(null);
                        boardView.showPieceRange(new ArrayList<>());
                    }
                }
            });
        }
    }

    // Function used to save the current game into a text file. (Done by Uzair)
    public void saveGame() {
        try {
            FileWriter save = new FileWriter("save.txt");
            save.write(Integer.toString(boardModel.getNumTurns()));
            save.write(System.lineSeparator());
            save.write(Boolean.toString(boardModel.isTurnBlue()));
            save.write(System.lineSeparator());
            for (int i = 0; i < 8; i++) {
                String rowPieces = "";
                for (int j = 0; j < 5; j++) {
                    Piece piece = boardModel.getPieceAtPosition(i, j);
                    if (piece == null) {
                        rowPieces += "-";
                    } else {
                        rowPieces += piece.getPieceNotation();
                    }
                    rowPieces += j < 4 ? " " : i < 7 ? System.lineSeparator() : "";
                }
                save.write(rowPieces);
            }
            save.close();
            JOptionPane.showMessageDialog(
                    boardView.getBoardWindow(),
                    "Game has been saved.",
                    "Game Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public void addSaveButtonListener() {
        boardView.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
    }

    //
    public void addResetButtonListener(){
        boardView.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to reset the game?",
                        "Reset Game",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    restartGame(); // Reset the game
                }
            }
        });
    }

    
    public void addKeyListeners() {
        // Use KeyboardFocusManager for global key events
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                // Trigger reset confirmation on ESC key press
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to reset the game?",
                        "Reset Game",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    restartGame(); // Reset the game
                }
            }
            return false; // Allow other key events to propagate
        });
    }

    //
    public void addWindowListener(){
        boardView.setWindowEvent(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to save the game?",
                        "Save Game?",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    saveGame(); // Reset the game
                } else if (result == JOptionPane.NO_OPTION) {
                    boardView.setCloseOperation();
                }
            }
        });
    }

    //
    public void addQuitButtonListener(){
        boardView.getQuitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        boardView.getBoardWindow(),
                        "Do you want to quit the game?",
                        "Quit Game",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    int secondResult = JOptionPane.showConfirmDialog(
                            null,
                            "Do you want to save the game?",
                            "Save Game?",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (secondResult == JOptionPane.YES_OPTION) {
                        saveGame(); // Save the game
                        boardView.disposeSelf();
                        new MainMenuController();
                    } else if (secondResult == JOptionPane.NO_OPTION) {
                        boardView.disposeSelf();
                        new MainMenuController();
                    }
                }
            }
        });
    }
    
    public void restartGame() {
        // Reset the game to its initial state
        boardModel.resetGame();
        boardView.clearIcons();
        boardView.updateIcons(boardModel.getPieces());
    }
}
