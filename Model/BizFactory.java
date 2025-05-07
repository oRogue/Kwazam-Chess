package Model;

// Done by Jarett Tan
public class BizFactory extends PieceFactory{
    // Creates a new Biz piece with the specified position and team color. (Done by Jarett Tan)
    @Override
    public Piece createPiece(int row, int column, boolean isBlue){
        return new Biz(row, column, isBlue);
    }
}