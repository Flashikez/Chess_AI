/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai.pieces;

import chess_ai.GameBoard;
import chess_ai.Tile;
import chess_ai.enums.Team;

/**
 *
 * @author MarekPC
 */
public class Pawn extends Piece {

    public boolean moved;

    public Pawn(String rept, Tile initTile, Team t) {
        super(rept, initTile, t,false,false);
        this.moved = false;
    }

    @Override
    public int[] legals(GameBoard board) {

        if (super.team == Team.PLAYER) {
            if (!this.moved) {
                return new int[]{-8, -16};
            }
            return new int[]{-8};

        } else {
            if (!this.moved) {
                return new int[]{8, 16};
            }
            return new int[]{8};
        }

    }
    
    public int [] attackLegals(){
        if (super.team == Team.PLAYER) {
            return new int[]{-7,-9};
        }else{
            return new int[]{-7,-9};
        }
    }
}
