package Model;

// Done by Jarett Tan
public class RamUpFactory extends PieceFactory{
    // Creates a new Ram piece with the specified position and team color that moves up.
    @Override
    public Piece createPiece(int row, int column, boolean isBlue){
        return new Ram(row, column, isBlue, -1);
    }
}
