/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai;

import chess_ai.pieces.Piece;

/**
 *
 * @author MarekPC
 */
public class Tile {
    private int position;
    private Piece piece;
    
    public Tile (int position,Piece initialPiece){
        this.position = position;
        this.piece = initialPiece;
    }
    public boolean isFree(){
        return piece == null;
    }
    
    public void setPiece(Piece newPiece){
        this.piece = newPiece;
    }

    public int getPosition() {
        return position;
    }
    
    public Piece getPiece(){
        return this.piece;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    
}
