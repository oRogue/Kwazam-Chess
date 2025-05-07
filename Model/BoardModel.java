package Model;

import java.util.ArrayList;

public class BoardModel {

    // Enum to represent the current mode of the game. (Done by Jarett Tan)
    public enum Mode{
        PIECE_SELECTION, // When selecting a piece to move
        PIECE_MOVE       // When moving a selected piece
    }

    private Mode currentMode = Mode.PIECE_SELECTION; // Current mode of the game
    private boolean turnBlue = true; // True if it's Blue's turn, false otherwise
    private int numTurns = 0; // Number of turns that have been played
    private ArrayList<Piece> pieces; // List of all pieces on the board
    private Piece selectedPiece = null; // Currently selected piece, if any
    private ArrayList<int[]> selectedPieceRange = null; // Valid movement range for the selected piece
    private boolean wonGame = false;

    // Constructor to initialize the game state. (Done by Jarett Tan)
    public BoardModel(boolean currentTurn, int numTurnsSoFar, ArrayList<Piece> boardPieces){
        turnBlue = currentTurn;
        numTurns = numTurnsSoFar;
        pieces = boardPieces;
    }

    // Gets the current game mode. (Done by Jarett Tan)
    public Mode getCurrentMode() {
        return currentMode;
    }

    // Sets the current game mode. (Done by Jarett Tan)
    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }

    // Checks if it's Blue's turn. (Done by Jarett Tan)
    public boolean isTurnBlue(){
        return turnBlue;
    }

    // Toggles the turn between Blue and the opponent. (Done by Jarett Tan)
    public void toggleTurn(){
        turnBlue = !turnBlue;
    }

    // Gets the number of turns that have been played. (Done by Jarett Tan)
    public int getNumTurns() {
        return numTurns;
    }

    // Increments the turn counter. (Done by Jarett Tan)
    public void increaseTurns(){
        numTurns++;
    }

    // Gets the list of all pieces on the board. (Done by Jarett Tan)
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    // Retrieves a piece at a specific position on the board. (Done by Jarett Tan)
    public Piece getPieceAtPosition(int row, int column){
        for (int i = 0; i < pieces.size(); i++){
            if(pieces.get(i).getRow() == row && pieces.get(i).getColumn() == column){
                return pieces.get(i);
            }
        }
        return null; // No piece found at the given position
    }

    //
    public boolean isWonGame(){
        return wonGame;
    }

    //
    public void setWonGame(boolean wonGame){
        this.wonGame = wonGame;
    }

    // Removes a piece from the board (when it is "eaten"). (Done by Jarett Tan and )
    public boolean eatPiece(Piece eatenPiece){
        pieces.remove(eatenPiece);
        System.out.println(eatenPiece.isEatWin());
        return eatenPiece.isEatWin(); //
    }

    // Gets the currently selected piece. (Done by Jarett Tan)
    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    // Sets the currently selected piece. (Done by Jarett Tan)
    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    // Gets the valid movement range for the selected piece. (Done by Jarett Tan)
    public ArrayList<int[]> getSelectedPieceRange() {
        return selectedPieceRange;
    }

    // Sets the valid movement range for the selected piece. (Done by Jarett Tan)
    public void setSelectedPieceRange(ArrayList<int[]> selectedPieceRange) {
        this.selectedPieceRange = selectedPieceRange;
    }

    // Checks if a given position is within the selected piece's movement range. (Done by Jarett Tan)
    public boolean isWithinRange(int row, int column){
        for(int i = 0; i < selectedPieceRange.size(); i++){
            if(selectedPieceRange.get(i)[0] == row && selectedPieceRange.get(i)[1] == column){
                return true;
            }
        }
        return false; // Position is not within range
    }

    // Moves the selected piece to the specified position. (Done by Jarett Tan and )
    public boolean movePiece(int row, int column){
        boolean win = false;
        for (int i = 0; i < selectedPieceRange.size(); i++){
            if(selectedPieceRange.get(i)[0] == row && selectedPieceRange.get(i)[1] == column){
                Piece overlappingPiece = getPieceAtPosition(row, column);
                // Remove the piece if it overlaps
                if(overlappingPiece != null){
                    win = eatPiece(overlappingPiece);
                }
                selectedPiece.move(selectedPieceRange.get(i)[0], selectedPieceRange.get(i)[1]); // Move the piece
            }
        }
        return win;
    }

    // Updates the state of all pieces on the board. (Done by Jarett Tan)
    public void updatePiecesState(){
        for (int i = 0; i < pieces.size(); i++){
            pieces.get(i).updateState(numTurns, pieces.get(i).getRow()); // Update each piece based on the current turn and its position
        }
    }

    //
    public void mirrorPieces(){
        for (int i = 0; i < pieces.size(); i++){
            pieces.get(i).setRow(7 - pieces.get(i).getRow());
            pieces.get(i).flipOrientation();
        }
    }

    //
    public void resetGame(){
        wonGame = false;
        numTurns = 0;
        turnBlue = true;
        pieces.clear();
        // Create a factory for Ram pieces and add them to the board
        PieceFactory pieceFactory = new RamUpFactory();
        for(int i = 0; i < 5; i++){
            pieces.add(pieceFactory.createPiece(1, i, false)); // Add non-blue Ram pieces
            pieces.add(pieceFactory.createPiece(6, i, true));  // Add blue Ram pieces
        }

        // Create a factory for Biz pieces and add them to the board
        pieceFactory = new BizFactory();
        for(int i = 0; i < 2; i++){
            pieces.add(pieceFactory.createPiece(0, 1 + 2 * i, false)); // Add non-blue Biz pieces
            pieces.add(pieceFactory.createPiece(7, 1 + 2 * i, true));  // Add blue Biz pieces
        }

        // Create a factory for Tor pieces and add them to the board
        pieceFactory = new TorFactory();
        for(int i = 0; i < 2; i++){
            pieces.add(pieceFactory.createPiece(7 * i, 4 * i, i % 2 != 0)); // Add Tor pieces based on index parity
        }

        // Create a factory for Xor pieces and add them to the board
        pieceFactory = new XorFactory();
        for (int i = 0; i < 2; i++){
            pieces.add(pieceFactory.createPiece(7 * i, 4 - 4 * i, i % 2 != 0)); // Add Xor pieces based on index parity
        }

        // Create a factory for Sau pieces and add them to the board
        pieceFactory = new SauFactory();
        for(int i = 0; i < 2; i++){
            pieces.add(pieceFactory.createPiece(7 * i, 2, i % 2 != 0)); // Add Sau pieces based on index parity
        }
    }
}
