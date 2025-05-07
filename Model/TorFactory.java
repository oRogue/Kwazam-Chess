package Model;

// Done by Jarett Tan
public class TorFactory extends PieceFactory{
    // Creates a new Tor piece with the specified position and team color.
    @Override
    public Piece createPiece(int row, int column, boolean isBlue){
        return new TorXor(row, column, isBlue, 0);
    }
}
