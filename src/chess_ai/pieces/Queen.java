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
public class Queen extends Piece {

    public Queen(String rept, Tile initTile, Team t) {
        super(rept, initTile, t, true,false);
    }

    @Override
    public int[] legals(GameBoard board) {
        return new int[]{-8, 8, -7, 7, -9, 9, -1, 1};
    }

}
