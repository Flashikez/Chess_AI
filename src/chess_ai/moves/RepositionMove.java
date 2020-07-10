/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai.moves;

import chess_ai.Tile;
import chess_ai.pieces.Piece;

/**
 *
 * @author MarekPC
 */
public class RepositionMove extends Move {

    public RepositionMove(Piece piece, Tile startPos, Tile endPos) {
        super(piece, startPos, endPos);
    }

    public String moveString() {
        return (super.getStartTile().getPosition() + "|" + super.getPiece().toString() + "  ---> " + super.getEndTile().getPosition() + "|");
    }

    @Override
    public Move revertMove(Move move) {
        RepositionMove m = new RepositionMove(move.getPiece(), move.getEndTile(), move.getStartTile());
        return m;
    }

    @Override
    public Move deepCopy(Move move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
