/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai.pieces;

import chess_ai.GameBoard;
import chess_ai.Tile;
import chess_ai.moves.Move;
import chess_ai.enums.Team;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public abstract class Piece {

    protected String repr;

    protected boolean vectorizedLegals;
    protected Team team;
    protected Tile tile;
    protected boolean winPiece;
    
    
    
    
    
    
    public Piece(String rept, Tile initTile, Team t, boolean vectorized, boolean win) {
        this.repr = rept;
        this.team = t;
        this.tile = initTile;
        this.vectorizedLegals = vectorized;
        this.winPiece = win;
    }

    public abstract int[] legals(GameBoard board);

    public boolean isWinningPiece() {
        return winPiece;
    }

    public boolean isOut() {
        return this.tile == null;
    }

    public void setTile(Tile t) {
        this.tile = t;
    }

    public int getBoardPosition() {
        return this.tile.getPosition();
    }

    @Override
    public String toString() {
        return this.repr;
    }

    public List<Move> getLegalMoves(GameBoard board) {
        return MoveCalc.legalMoves(this, board);
    }

    public Team getTeam() {
        return this.team;
    }
}
