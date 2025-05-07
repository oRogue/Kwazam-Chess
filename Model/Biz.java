package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Biz extends Piece {


    // Constructor for the Biz piece. (Done by Jarett Tan)
    // Initializes the piece's position, type, and team color. (Done by Jarett Tan)
    // Also loads and assigns the appropriate image based on the team color. (Done by Jarett Tan)
    public Biz(int row, int column, boolean isBlue) {
        super(row, column, PieceType.BIZ, isBlue);
        try {
            // Load images for the Biz piece
            BufferedImage bizRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/Biz.png"));
            ArrayList<BufferedImage> bizList = new ArrayList<>();
            bizList.add(bizRed);
            BufferedImage bizBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/BizBlue.png"));
            bizList.add(bizBlue);
            setPieceImage(bizList);

            // Set the active image based on the team color
            if (isBlue) {
                setActivePieceImage(bizBlue);
            } else {
                setActivePieceImage(bizRed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Calculates and returns the valid movement range for the Biz piece. (Done by Jarett Tan)
    public ArrayList<int[]> getPieceMovementRange(ArrayList<Piece> blockingPieces) {
        int currRow = getRow();
        int currColumn = getColumn();
        ArrayList<int[]> ranges = new ArrayList<>();

        // Check potential moves in all directions
        for (int i = 0; i < 4; i++) {
            int newColumn = currColumn - 2 + (int) Math.floor(i * 1.5);
            for (int j = 0; j < 2; j++) {
                int newRow = currRow + (-1 - (i == 1 || i == 2 ? 1 : 0)) * (1 - j * 2);

                // Ensure the move stays within board boundaries
                if (newColumn >= 0 && newColumn < 5 && newRow >= 0 && newRow < 8) {
                    boolean alreadyOccupied = false;
                    boolean includeOccupy = false;

                    // Check for other pieces in the target location
                    for (int k = 0; k < blockingPieces.size(); k++) {
                        if (blockingPieces.get(k).getRow() == newRow && blockingPieces.get(k).getColumn() == newColumn) {
                            alreadyOccupied = true;
                            // Allow capturing opponent pieces
                            if (getIsBlue() != blockingPieces.get(k).getIsBlue()) {
                                includeOccupy = true;
                            }
                            break;
                        }
                    }

                    // Add the move to the range if valid
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

    // Updates the state of the Biz piece. (Done by Jarett Tan)
    // This piece does not change state based on the turn number or its row, so the method does nothing.
    @Override
    public void updateState(int turnNum, int newRow) {
        return;
    }

    // Moves the Biz piece to a new position. (Done by Jarett Tan)
    @Override
    public void move(int newRow, int newColumn) {
        setRow(newRow);
        setColumn(newColumn);
    }

    //
    public boolean isEatWin() {
        return false;
    }

    //
    public String getPieceNotation(){
        if (getIsBlue()){
            return "b";
        } else {
            return "B";
        }
    }
}
