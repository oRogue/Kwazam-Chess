package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class TorXor extends Piece {
    private int torOrXor = 0;  // Variable to differentiate between Tor and Xor (0 = Tor, 1 = Xor)

    // Constructor to initialize the TorXor piece, setting up the piece's image. (Done by Jarett Tan)
    public TorXor(int row, int column, boolean isBlue, int torOrXor) {
        super(row, column, PieceType.TORXOR, isBlue);
        this.torOrXor = torOrXor;
        try {
            // Load the image for Tor and Xor pieces (red versions)
            BufferedImage torRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/Tor.png"));
            BufferedImage xorRed = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/Xor.png"));
            ArrayList<BufferedImage> torXorList = new ArrayList<>();
            torXorList.add(torRed);
            torXorList.add(xorRed);

            // Load the image for Tor and Xor pieces (blue versions)
            BufferedImage torBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/TorBlue.png"));
            BufferedImage xorBlue = ImageIO.read(ClassLoader.getSystemResourceAsStream("Piece_Images/XorBlue.png"));
            torXorList.add(torBlue);
            torXorList.add(xorBlue);

            // Set the piece images for both blue and red
            setPieceImage(torXorList);

            // Set the active image for the piece based on the color and the torOrXor state
            if (isBlue) {
                setActivePieceImage(torXorList.get(2 + torOrXor));
            } else {
                setActivePieceImage(torXorList.get(torOrXor));
            }
        } catch (IOException e) {
            // If an error occurs during image loading, print the stack trace
            e.printStackTrace();
        }
    }

    // Method to calculate the movement range of the TorXor piece, considering blocking pieces. (Done by Jarett Tan)
    public ArrayList<int[]> getPieceMovementRange(ArrayList<Piece> blockingPieces) {
        int currRow = getRow();
        int currColumn = getColumn();
        ArrayList<int[]> ranges = new ArrayList<>();

        // If the piece is Tor (torOrXor == 0), calculate linear movement
        if (torOrXor == 0) {
            // Vertical movement (up and down)
            for (int i = 0; i < 2; i++) {
                int newRow = currRow;
                for (int j = 1; j <= Math.abs((i * 7) - currRow); j++) {
                    newRow += -1 + (i * 2);
                    boolean alreadyOccupied = false;
                    boolean includeOccupy = false;

                    // Check for any blocking pieces in the new position
                    for (int k = 0; k < blockingPieces.size(); k++) {
                        if (blockingPieces.get(k).getRow() == newRow && blockingPieces.get(k).getColumn() == currColumn) {
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
                        range[1] = currColumn;
                        ranges.add(range);
                        if (includeOccupy) {
                            break;  // Stop if the piece can be captured
                        }
                    } else {
                        break;  // Stop if the position is blocked by a piece of the same color
                    }
                }
            }

            // Horizontal movement (left and right)
            for (int i = 0; i < 2; i++) {
                int newColumn = currColumn;
                for (int j = 1; j <= Math.abs((i * 4) - currColumn); j++) {
                    newColumn += -1 + (i * 2);
                    boolean alreadyOccupied = false;
                    boolean includeOccupy = false;

                    // Check for any blocking pieces in the new position
                    for (int k = 0; k < blockingPieces.size(); k++) {
                        if (blockingPieces.get(k).getRow() == currRow && blockingPieces.get(k).getColumn() == newColumn) {
                            alreadyOccupied = true;
                            if (getIsBlue() != blockingPieces.get(k).getIsBlue()) {
                                includeOccupy = true;
                            }
                            break;
                        }
                    }

                    // If the position is not occupied or can be occupied, add it to the movement range
                    if (!alreadyOccupied || includeOccupy) {
                        int[] range = new int[2];
                        range[0] = currRow;
                        range[1] = newColumn;
                        ranges.add(range);
                        if (includeOccupy) {
                            break;  // Stop if the piece can be captured
                        }
                    } else {
                        break;  // Stop if the position is blocked by a piece of the same color
                    }
                }
            }
        } else {  // If the piece is Xor (torOrXor == 1), calculate diagonal movement
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int newRow = currRow;
                    int newColumn = currColumn;
                    for (int k = 0; k < Math.min(Math.abs((i * 7) - currRow), Math.abs((j * 4) - currColumn)); k++) {
                        newRow += -1 + (i * 2);
                        newColumn += -1 + (j * 2);
                        boolean alreadyOccupied = false;
                        boolean includeOccupy = false;

                        // Check for any blocking pieces in the new position
                        for (int l = 0; l < blockingPieces.size(); l++) {
                            if (newRow == blockingPieces.get(l).getRow() && newColumn == blockingPieces.get(l).getColumn()) {
                                alreadyOccupied = true;
                                if (getIsBlue() != blockingPieces.get(l).getIsBlue()) {
                                    includeOccupy = true;
                                }
                            }
                        }

                        // If the position is not occupied or can be occupied, add it to the movement range
                        if (!alreadyOccupied || includeOccupy) {
                            int[] range = new int[2];
                            range[0] = newRow;
                            range[1] = newColumn;
                            ranges.add(range);
                            if (includeOccupy) {
                                break;  // Stop if the piece can be captured
                            }
                        } else {
                            break;  // Stop if the position is blocked by a piece of the same color
                        }
                    }
                }
            }
        }
        return ranges;  // Return the list of valid movement ranges
    }

    // Method to update the state of the piece at the end of each turn, toggling between Tor and Xor. (Done by Jarett Tan)
    @Override
    public void updateState(int turnNum, int newRow) {
        if ((turnNum + 1) % 2 == 0) {
            // Toggle between Tor and Xor when it's the player's turn
            torOrXor += 1 - (torOrXor * 2);
            // Update the active piece image based on the new piece state
            if (getIsBlue()) {
                setActivePieceImage(getPieceImage().get(2 + torOrXor));
            } else {
                setActivePieceImage(getPieceImage().get(torOrXor));
            }
        }
    }

    // Method to move the piece to a new position on the board. (Done by Jarett Tan)
    @Override
    public void move(int newRow, int newColumn) {
        setRow(newRow);  // Update the row
        setColumn(newColumn);  // Update the column
    }

    //
    public boolean isEatWin() {
        return false;
    }

    //
    public String getPieceNotation(){
        if (torOrXor == 0){
            if (getIsBlue()){
                return "t";
            } else {
                return "T";
            }
        } else {
            if (getIsBlue()){
                return "x";
            } else {
                return "X";
            }
        }
    }
}
