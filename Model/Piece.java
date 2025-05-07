package Model;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

public abstract class Piece {
    // Enum to define the different types of pieces. (Done by Jarett Tan)
    public enum PieceType {
        RAM,
        BIZ,
        TORXOR,
        SAU
    }

    private int row; // The current row position of the piece
    private int column; // The current column position of the piece
    private PieceType id; // The type identifier for the piece
    private int orientation = 0; // The current orientation of the piece
    private boolean isBlue; // Indicates whether the piece belongs to the blue team
    private ArrayList<BufferedImage> pieceImage; // A list of images representing the piece in different states
    private BufferedImage activePieceImage; // The currently active image for the piece

    // Piece Constructor. (Done by Jarett Tan)
    public Piece(int row, int column, PieceType id, boolean isBlue) {
        this.row = row;
        this.column = column;
        this.id = id;
        this.isBlue = isBlue;
    }

    // Returns the current row position of the piece. (Done by Jarett Tan)
    public int getRow() {
        return row;
    }

    // Returns the current column position of the piece. (Done by Jarett Tan)
    public int getColumn() {
        return column;
    }

    // Returns the type identifier of the piece. (Done by Jarett Tan)
    public PieceType getId() {
        return id;
    }

    // Returns whether the piece belongs to the blue team. (Done by Jarett Tan)
    public boolean getIsBlue() {
        return isBlue;
    }

    // Returns the list of images for the piece. (Done by Jarett Tan)
    public ArrayList<BufferedImage> getPieceImage() {
        return pieceImage;
    }

    // Returns the current orientation of the piece. (Done by Jarett Tan)
    public int getOrientation() {
        return orientation;
    }

    // Updates the row position of the piece. (Done by Jarett Tan)
    public void setRow(int newRow) {
        row = newRow;
    }

    // Updates the column position of the piece. (Done by Jarett Tan)
    public void setColumn(int newCol) {
        column = newCol;
    }

    // Sets the list of images for the piece. (Done by Jarett Tan)
    public void setPieceImage(ArrayList<BufferedImage> pieceImage) {
        this.pieceImage = pieceImage;
    }

    // Returns the currently active image for the piece. (Done by Jarett Tan)
    public BufferedImage getActivePieceImage() {
        return activePieceImage;
    }

    // Sets the active image for the piece. (Done by Jarett Tan)
    public void setActivePieceImage(BufferedImage activePieceImage) {
        this.activePieceImage = activePieceImage;
    }

    // Abstract method to calculate the movement range of the piece. (Done by Jarett Tan)
    public abstract ArrayList<int[]> getPieceMovementRange(ArrayList<Piece> blockingPieces);

    // Flips the orientation of the piece between two states. (Done by Jarett Tan)
    public void flipOrientation() {
        orientation += 1 - 2 * orientation;
    }

    // Abstract method to move the piece to a new position. (Done by Jarett Tan)
    public abstract void move(int newRow, int newColumn);

    // Abstract method to update the state of the piece based on the turn number and new row position. (Done by Jarett Tan)
    public abstract void updateState(int turnNum, int newRow);

    //
    public abstract boolean isEatWin();

    //
    public abstract String getPieceNotation();

    //
    public void setOrientation(int orientation){
        this.orientation = orientation;
    }
}
