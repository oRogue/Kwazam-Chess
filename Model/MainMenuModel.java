package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuModel {
    //  check for a save file
    private boolean checkSaveFile() {
        return new java.io.File("save.txt").exists();
    }

    public BoardModel initialize(){
        // Create a factory for Ram pieces and add them to the board
        PieceFactory pieceFactory = new RamUpFactory();
        ArrayList<Piece> pieces = new ArrayList<>();
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
        return new BoardModel(true, 0,pieces);
    }

    //
    public BoardModel loadGame() throws IOException{
        BoardModel boardModel = initialize();
        try {
            File saveFile = new File("save.txt");
            if (saveFile.exists()) {
                Scanner myReader = new Scanner(saveFile);
                int numTurnSoFar = Integer.parseInt(myReader.nextLine());
                boolean isBlueTurn = Boolean.parseBoolean(myReader.nextLine());
                ArrayList<Piece> pieces = new ArrayList<>();
                int gridIndex = 0;
                while (myReader.hasNextLine()) {
                    String[] rowPieces = myReader.nextLine().split(" ");
                    PieceFactory ramUpFactory = new RamUpFactory();
                    PieceFactory ramDownFactory = new RamDownFactory();
                    PieceFactory bizFactory = new BizFactory();
                    PieceFactory torFactory = new TorFactory();
                    PieceFactory xorFactory = new XorFactory();
                    PieceFactory sauFactory = new SauFactory();
                    for (int i = 0; i < rowPieces.length; i++) {
                        switch (rowPieces[i]) {
                            case "a": {
                                pieces.add(ramUpFactory.createPiece(gridIndex / 5, gridIndex % 5, true));
                                break;
                            }
                            case "A": {
                                pieces.add(ramUpFactory.createPiece(gridIndex / 5, gridIndex % 5, false));
                                break;
                            }
                            case "v": {
                                pieces.add(ramDownFactory.createPiece(gridIndex / 5, gridIndex % 5, true));
                                break;
                            }
                            case "V": {
                                pieces.add(ramDownFactory.createPiece(gridIndex / 5, gridIndex % 5, false));
                                break;
                            }
                            case "b": {
                                pieces.add(bizFactory.createPiece(gridIndex / 5, gridIndex % 5, true));
                                break;
                            }
                            case "B": {
                                pieces.add(bizFactory.createPiece(gridIndex / 5, gridIndex % 5, false));
                                break;
                            }
                            case "t": {
                                pieces.add(torFactory.createPiece(gridIndex / 5, gridIndex % 5, true));
                                break;
                            }
                            case "T": {
                                pieces.add(torFactory.createPiece(gridIndex / 5, gridIndex % 5, false));
                                break;
                            }
                            case "x": {
                                pieces.add(xorFactory.createPiece(gridIndex / 5, gridIndex % 5, true));
                                break;
                            }
                            case "X": {
                                pieces.add(xorFactory.createPiece(gridIndex / 5, gridIndex % 5, false));
                                break;
                            }
                            case "s": {
                                pieces.add(sauFactory.createPiece(gridIndex / 5, gridIndex % 5, true));
                                break;
                            }
                            case "S": {
                                pieces.add(sauFactory.createPiece(gridIndex / 5, gridIndex % 5, false));
                                break;
                            }
                        }
                        gridIndex++;
                    }
                }
                for (int i = 0; i < pieces.size(); i++) {
                    if (!isBlueTurn) {
                        pieces.get(i).flipOrientation();
                    }
                }

                return new BoardModel(isBlueTurn, numTurnSoFar, pieces);
            } else {
                throw new IOException();
            }

        } catch (IOException e) {
            throw e;
        }
    }
}
