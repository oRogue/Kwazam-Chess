package Model;

// Done by Jarett Tan
public class XorFactory extends PieceFactory{
    // Creates a new Xor piece with the specified position and team color. (Done by Jarett Tan)
    @Override
    public Piece createPiece(int row, int column, boolean isBlue){
        return new TorXor(row, column, isBlue, 1);
    }
}