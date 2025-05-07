package Model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Sau extends Piece {
    // Constructor for the Sau class, which initializes the piece and sets its image. (Done by Jarett Tan)
    public Sau(int row, int column, boolean isBlue) {
        super(row, column, PieceType.SAU, isBlue);
        try {
            // Load the image of the Sau piece (red version)
            BufferedImage sauRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/Sau.png"));
            ArrayList<BufferedImage> sauList = new ArrayList<>();
            sauList.add(sauRed);
            BufferedImage flippedSauRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/FlippedSau.png"));
            sauList.add(flippedSauRed);
            // Load the image of the Sau piece (blue version)
            BufferedImage sauBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/SauBlue.png"));
            sauList.add(sauBlue);
            BufferedImage flippedSauBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/FlippedSauBlue.png"));
            sauList.add(flippedSauBlue);

            // Set the list of images for the piece
            setPieceImage(sauList);

            // Set the active image based on the color and orientation
            if (isBlue) {
                setActivePieceImage(sauList.get(2 + getOrientation()));
            } else {
                setActivePieceImage(sauList.get(0 + getOrientation()));
            }
        } catch (IOException e) {
            // If an error occurs during image loading, print the stack trace
            e.printStackTrace();
        }
    }

    // Method to calculate the movement range of the Sau piece, considering blocking pieces. (Done by Jarett Tan)
    public ArrayList<int[]> getPieceMovementRange(ArrayList<Piece> blockingPieces) {
        int currRow = getRow();
        int currColumn = getColumn();
        ArrayList<int[]> ranges = new ArrayList<>();

        // Loop through all possible positions around the current position (a 3x3 grid)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = currRow + i;
                int newColumn = currColumn + j;
                boolean alreadyOccupied = false;
                boolean includeOccupy = false;

                // Check if the new position is within the board bounds
                if (newColumn >= 0 && newColumn < 5 && newRow >= 0 && newRow < 8) {
                    // Check if there are any blocking pieces in the new position
                    for (int k = 0; k < blockingPieces.size(); k++) {
                        if (blockingPieces.get(k).getRow() == newRow && blockingPieces.get(k).getColumn() == newColumn) {
                            alreadyOccupied = true;
                            // If the blocking piece is of the opposite color, it can be captured
                            if (getIsBlue() != blockingPieces.get(k).getIsBlue()) {
                                includeOccupy = true;
                            }
                            break;
                        }
                    }
                    // If the position is not occupied or can be occupied, add it to the movement range
                    if (!alreadyOccupied || includeOccupy) {
                        int[] range = new int[2];
                        range[0] = newRow;
                        range[1] = newColumn;
                        ranges.add(range);
                    }
                }
            }
        }
        return ranges;
    }

    // Override method to update the state of the piece (does nothing for Sau). (Done by Jarett Tan)
    @Override
    public void updateState(int turnNum, int newRow) {
        return;
    }

    // Method to move the piece to a new position on the board. (Done by Jarett Tan)
    @Override
    public void move(int newRow, int newColumn) {
        setRow(newRow);  // Update the row
        setColumn(newColumn);  // Update the column
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
    public boolean isEatWin() {
        return true;
    }

    //
    public String getPieceNotation(){
        if (getIsBlue()){
            return "s";
        } else {
            return "S";
        }
    }
}
