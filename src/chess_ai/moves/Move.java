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
public abstract class Move {
    
    private Piece piece;
    private Tile startTile,endTile;

    public Move(Piece piece, Tile startPos, Tile endPos) {
        this.piece = piece;
        this.startTile = startPos;
        this.endTile = endPos;
    }

    public Piece getPiece() {
        return piece;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public Tile getEndTile() {
        return endTile;
    }
    public abstract String moveString();

    public abstract Move revertMove(Move move);
    
    public abstract Move deepCopy(Move move);
    
    
    
    
    
}

