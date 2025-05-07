package Model;

// Done by Jarett Tan
public abstract class PieceFactory {
    // Abstract method to create a piece of a specific type. (Done by Jarett Tan)
    public abstract Piece createPiece(int row, int column, boolean isBlue);
}
