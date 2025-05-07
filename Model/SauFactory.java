package Model;

// Done by Jarett Tan
public class SauFactory extends PieceFactory{
    // Creates a new Sau piece with the specified position and team color. (Done by Jarett Tan)
    @Override
    public Piece createPiece(int row, int column, boolean isBlue){
        return new Sau(row, column, isBlue);
    }
}
