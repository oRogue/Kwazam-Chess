package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Ram extends Piece {
    private int movementIncrement = -1; // Indicates the direction of movement (up or down)

     // Constructor for the Ram class. (Done by Jarett Tan )
     // Initializes the Ram piece with its position, color, and associated images.
    public Ram(int row, int column, boolean isBlue, int movementIncrement) {
        super(row, column, PieceType.RAM, isBlue);
        this.movementIncrement = movementIncrement; // Check if Ram is moving up or down
        try {
            // Load the image resources for the Ram piece
            BufferedImage ramRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/Ram.png"));
            ArrayList<BufferedImage> ramList = new ArrayList<>();
            BufferedImage flippedRamRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/FlippedRam.png"));
            ramList.add(ramRed);
            ramList.add(flippedRamRed);
            BufferedImage ramBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/RamBlue.png"));
            ramList.add(ramBlue);
            BufferedImage flippedRamBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/FlippedRamBlue.png"));
            ramList.add(flippedRamBlue);

            // Set the piece images
            setPieceImage(ramList);

            // Set the active image based on the color and orientation
            if (isBlue) {
                setActivePieceImage(ramList.get(2 + getOrientation()));
            } else {
                setActivePieceImage(ramList.get(0 + getOrientation()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        if (movementIncrement > 0){
            flipOrientation();
        }
    }

     // Calculates the movement range for the Ram piece. (Done by Jarett Tan)
     // The Ram can move one step forward in the current direction, unless blocked by another piece.
    public ArrayList<int[]> getPieceMovementRange(ArrayList<Piece> blockingPieces) {
        ArrayList<int[]> ranges = new ArrayList<>();
        int newRow = getRow() + movementIncrement; // Calculate the next row based on movement direction
        int newColumn = getColumn(); // Column remains the same
        boolean alreadyOccupied = false;
        boolean includeOccupy = false;

        // Check for pieces blocking the target position
        for (int i = 0; i < blockingPieces.size(); i++) {
            if (blockingPieces.get(i).getRow() == newRow && blockingPieces.get(i).getColumn() == newColumn) {
                alreadyOccupied = true;
                if (getIsBlue() != blockingPieces.get(i).getIsBlue()) {
                    includeOccupy = true;
                }
                break;
            }
        }

        // Add the target position to the range if valid
        if (!alreadyOccupied || includeOccupy) {
            int[] range = new int[2];
            range[0] = newRow;
            range[1] = newColumn;
            ranges.add(range);
        }
        return ranges;
    }

     // Updates the state of the Ram piece. (Done by Jarett Tan)
     // This implementation does nothing as the state does not change based on turns or row changes.
    @Override
    public void updateState(int turnNum, int newRow) {
        return;
    }

     // Moves the Ram piece to a new position and adjusts its orientation if it reaches the board's edge. (Done by Jarett Tan)
     // Flips the piece's orientation and reverses its movement direction when it reaches the top or bottom row.
    @Override
    public void move(int newRow, int newColumn) {
        setRow(newRow);
        setColumn(newColumn);

        // Check if the Ram has reached the top or bottom row
        if (newRow == 0 || newRow == 7) {
            movementIncrement = movementIncrement * -1; // Reverse the movement direction
            flipOrientation(); // Flip the orientation of the piece
        }
    }

    //
    public boolean isEatWin() {
        return false;
    }

    //
    @Override
    public void flipOrientation() {
        setOrientation(getOrientation() + 1 - 2 * getOrientation());
        // Update the active image based on the new orientation and color
        if (getIsBlue()) {
            setActivePieceImage(getPieceImage().get(2 + getOrientation()));
        } else {
            setActivePieceImage(getPieceImage().get(0 + getOrientation()));
        }
    }

    //
    public String getPieceNotation(){
        if (getIsBlue()){
            if (movementIncrement > 0){
                return "v";
            } else {
                return "a";
            }
        } else {
            if (movementIncrement > 0){
                return "V";
            } else {
                return "A";
            }
        }
    }
}
