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
public class AttackMove extends Move {

    private Piece attackedPiece;

    public AttackMove(Piece piece, Piece attackedPiece, Tile startPos, Tile endPos) {
        super(piece, startPos, endPos);
        this.attackedPiece = attackedPiece;
    }

    public Piece getAttackedPiece() {
        return this.attackedPiece;
    }

    @Override
    public String moveString() {
        return (super.getStartTile().getPosition() + "|" + super.getPiece().toString() + "  ---> " + super.getEndTile().getPosition() + "|" + this.attackedPiece);
    }

    @Override
    public Move revertMove(Move m) {
        AttackMove move = (AttackMove) m;
        AttackMove newMove = new AttackMove(move.getAttackedPiece(), move.getPiece(), move.getEndTile(), move.getStartTile());
        return newMove;
    }

    @Override
    public Move deepCopy(Move move) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
