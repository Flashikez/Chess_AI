/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai.pieces;

import chess_ai.GameBoard;
import chess_ai.Tile;
import chess_ai.enums.Team;
import chess_ai.moves.Move;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class King  extends Piece{

    public King(String rept,Tile tile, Team t) {
        super(rept,tile, t,false,true);
    }

    @Override
    public int[] legals(GameBoard board) {
        return new int[]{-8,8,-7,7,-9,9,-1,1};
    }

    
}
