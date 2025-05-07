package View;

import Model.Piece;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import javax.swing.*;

public class BoardView {
    private JFrame boardWindow;
    private JPanel boardPanel;
    private ArrayList<JButton> boardGrid = new ArrayList<>();
    private JButton saveButton;
    private JPanel controlPanel;
    private JButton quitButton;
    private JButton resetButton;


    // BoardView Constructor. (Done by Jarett Tan and Zikry)
    public BoardView(){
        this.boardWindow = new JFrame();
        this.boardPanel = new JPanel(new GridLayout(8,5));

        // Set background color to make the board more visible and easier to look at
        boardWindow.getContentPane().setBackground(Color.WHITE);

        // Set the size of the frame
        boardWindow.setMinimumSize(new Dimension(500, 800));

        // Set the frame to be in the center of the screen
        boardWindow.setLocationRelativeTo(null);

        // Initialize the grid buttons and add them to the panel
        for(int i = 0; i < 40; i++){
            JButton gridButton = new JButton();
            gridButton.setContentAreaFilled(false);
            gridButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
            boardGrid.add(gridButton);
            boardPanel.add(gridButton);
        }

        // Add the panel to the window and finalize setup
        boardWindow.add(boardPanel, BorderLayout.CENTER);
        controlPanel = new JPanel(new GridBagLayout());
        quitButton = new JButton();
        quitButton.setText("Quit");
        saveButton = new JButton();
        saveButton.setText("Save");
        resetButton = new JButton();
        resetButton.setText("Reset");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5,5,5,5);
        controlPanel.add(quitButton, c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5,5,5,5);
        controlPanel.add(saveButton, c);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5,5,5,5);
        controlPanel.add(resetButton, c);
        boardWindow.add(controlPanel, BorderLayout.SOUTH);
        boardWindow.setVisible(true);
        boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ensure the JFrame and JPanel are focusable for esc key shenanigans (Zikry)
        boardWindow.setFocusable(true);
        boardWindow.requestFocusInWindow();
    }

    // Initializes the board with pieces by placing their images in the appropriate grid cells. (Done by Jarett Tan)
    public void initialize(ArrayList<Piece> pieces){
        for(int i = 0; i < pieces.size(); i++){
            int index = pieces.get(i).getRow() * 5 + pieces.get(i).getColumn();
            Image scaledImage = pieces.get(i).getActivePieceImage().getScaledInstance(boardGrid.get(index).getWidth(), boardGrid.get(index).getHeight(), Image.SCALE_SMOOTH);
            boardGrid.get(index).setIcon(new ImageIcon(scaledImage));
        }
    }

    // Returns the list of buttons representing the board grid. (Done by Jarett Tan)
    public ArrayList<JButton> getBoardGrid(){
        return boardGrid;
    }

    // JFrame getter function.
    public JFrame getBoardWindow() {
        return boardWindow;
    }

    //
    public JButton getSaveButton(){
        return saveButton;
    }

    //
    public JButton getQuitButton() {
        return quitButton;
    }

    //
    public JButton getResetButton() {
        return resetButton;
    }

    // Highlights the valid movement range for a selected piece. (Done by Jarett Tan)
    public void showPieceRange(ArrayList<int[]> ranges){
        // Reset all grid backgrounds
        for(int i = 0; i < boardGrid.size(); i++){
            boardGrid.get(i).setBackground(null);
        }
        // Highlight cells within the range
        if(!ranges.isEmpty()){
            for(int i = 0; i < ranges.size(); i++){
                int index = ranges.get(i)[0] * 5 + ranges.get(i)[1];
                boardGrid.get(index).setBackground(new Color(255, 239, 164));
                boardGrid.get(index).setOpaque(true);
            }
        }
    }

    // Updates the board when a piece is moved from one position to another. (Done by Jarett Tan)
    public void updateMove(int oldRow, int oldColumn, int newRow, int newColumn){
        int oldIndex = oldRow * 5 + oldColumn;
        int newIndex = newRow * 5 + newColumn;
        boardGrid.get(newIndex).setIcon(boardGrid.get(oldIndex).getIcon());
        boardGrid.get(oldIndex).setIcon(null);
    }

    // Updates the icons for all pieces on the board based on their current positions. (Done by Jarett Tan)
    public void updateIcons(ArrayList<Piece> pieces){
        for(int i = 0; i < pieces.size(); i++){
            int index = pieces.get(i).getRow() * 5 + pieces.get(i).getColumn();
            Image scaledImage = pieces.get(i).getActivePieceImage().getScaledInstance(boardGrid.get(index).getWidth(), boardGrid.get(index).getHeight(), Image.SCALE_SMOOTH);
            boardGrid.get(index).setIcon(new ImageIcon(scaledImage));
        }
    }

    //
    public void clearIcons(){
        for (int i = 0; i < 40; i++){
            boardGrid.get(i).setIcon(null);
        }
    }

    //
    public void setWindowEvent(WindowAdapter adapter){
        boardWindow.addWindowListener(adapter);
    }

    public void setCloseOperation(){
        boardWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //
    public void disposeSelf(){
        boardWindow.dispose();
    }
}
