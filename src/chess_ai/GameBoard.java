/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai;

import chess_ai.enums.Team;
import chess_ai.moves.AttackMove;
import chess_ai.moves.Move;
import chess_ai.moves.RepositionMove;
import chess_ai.pieces.Bishop;
import chess_ai.pieces.King;
import chess_ai.pieces.Knight;
import chess_ai.pieces.MoveCalc;
import chess_ai.pieces.Pawn;
import chess_ai.pieces.Piece;
import chess_ai.pieces.Queen;
import chess_ai.pieces.Rook;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author MarekPC
 */
public class GameBoard {

    private List<Tile> boardTiles;
    private String boardName;
    private List<Piece> attackedPiecesPlayer, attackedPiecesAI;
    private Stack<Move> moveHistory;

    public void makeAIMove() {
        return;
    }

    public Move getLastMove() {
        if (moveHistory.isEmpty()) {
            return null;
        }
        return moveHistory.pop();
    }

    public boolean executeMove(Move move, boolean saveMove) {
        if (saveMove) {
            moveHistory.push(move);
        }
        if (move instanceof RepositionMove) {
            RepositionMove rm = (RepositionMove) move;
            Tile fromTile = rm.getStartTile();
            Tile endTile = rm.getEndTile();
            this.swapTiles(fromTile, endTile);

        } else {
            AttackMove am = (AttackMove) move;
            if (am.getAttackedPiece().getTeam() == Team.PLAYER) {
                attackedPiecesPlayer.add(am.getAttackedPiece());
            } else {
                attackedPiecesAI.add(am.getAttackedPiece());
            }
            if (am.getAttackedPiece().isWinningPiece()) {
                return true;
            }

            Tile fromTile = am.getStartTile();
            Tile endTile = am.getEndTile();
            this.swapTiles(fromTile, endTile);
            fromTile.getPiece().setTile(null);
            fromTile.setPiece(null);

        }
        System.out.println(boardString());
        return false;

    }

    public String boardString() {
        int i = 1;
        String ret = "|";
        for (Tile boardTile : boardTiles) {
            if (boardTile.getPiece() != null) {
                ret += boardTile.getPiece().toString();
            } else {
                ret += "  ";
            }

            ret += "|";

            if (i % 8 == 0) {
                ret += "\n________________________\n|";
            }
            i++;

        }
        return ret;
    }

    private void swapTiles(Tile fromTile, Tile endTile) {
//        Collections.swap(this.boardTiles, fromTile.getPosition() , endTile.getPosition() );
        Piece fromPiece = fromTile.getPiece();
        Piece toPiece = endTile.getPiece();
        fromPiece.setTile(endTile);
        if (toPiece != null) {
            toPiece.setTile(fromTile);
        }

        Piece p = fromPiece;
        fromTile.setPiece(endTile.getPiece());
        endTile.setPiece(p);

    }

    public GameBoard(String boardName) {
        this.boardName = boardName;
        init();
    }

    public Tile getTileAtIndex(int index) {
        return this.boardTiles.get(index - 1);
    }

    public List<Tile> getBoardState() {
        return boardTiles;
    }

    public List<Move> getPossibleMoves(Piece p) {
        return MoveCalc.legalMoves(p, this);
    }

    public void init() {
        this.attackedPiecesAI = new ArrayList<>();
        this.attackedPiecesPlayer = new ArrayList<>();
        this.moveHistory = new Stack<>();
        this.boardTiles = new ArrayList<>();
        this.repopulateBoard();

    }

    private void repopulateBoard() {
        // TOP TO BOTTOM
        this.boardTiles.clear();
        for (int i = 0; i < 8 * 8; i++) {
            this.boardTiles.add(new Tile(i, null));
        }
        this.boardTiles.get(0).setPiece(new Rook("RB", this.boardTiles.get(0), Team.AI));
        this.boardTiles.get(7).setPiece(new Rook("RB", this.boardTiles.get(7), Team.AI));
        this.boardTiles.get(1).setPiece(new Knight("KNB", this.boardTiles.get(1), Team.AI));
        this.boardTiles.get(6).setPiece(new Knight("KNB", this.boardTiles.get(6), Team.AI));
        this.boardTiles.get(2).setPiece(new Bishop("BB", this.boardTiles.get(2), Team.AI));
        this.boardTiles.get(5).setPiece(new Bishop("BB", this.boardTiles.get(5), Team.AI));
        this.boardTiles.get(3).setPiece(new Queen("QB", this.boardTiles.get(3), Team.AI));
        this.boardTiles.get(4).setPiece(new King("KB", this.boardTiles.get(4), Team.AI));

        this.boardTiles.get(8 + 0).setPiece(new Pawn("PB", this.boardTiles.get(8 + 0), Team.AI));
        this.boardTiles.get(8 + 7).setPiece(new Pawn("PB", this.boardTiles.get(8 + 7), Team.AI));
        this.boardTiles.get(8 + 1).setPiece(new Pawn("PB", this.boardTiles.get(8 + 1), Team.AI));
        this.boardTiles.get(8 + 6).setPiece(new Pawn("PB", this.boardTiles.get(8 + 6), Team.AI));
        this.boardTiles.get(8 + 2).setPiece(new Pawn("PB", this.boardTiles.get(8 + 2), Team.AI));
        this.boardTiles.get(8 + 5).setPiece(new Pawn("PB", this.boardTiles.get(8 + 5), Team.AI));
        this.boardTiles.get(8 + 3).setPiece(new Pawn("PB", this.boardTiles.get(8 + 3), Team.AI));
        this.boardTiles.get(8 + 4).setPiece(new Pawn("PB", this.boardTiles.get(8 + 4), Team.AI));

        this.boardTiles.get(63).setPiece(new Rook("RW", this.boardTiles.get(63), Team.PLAYER));
        this.boardTiles.get(63 - 7).setPiece(new Rook("RW", this.boardTiles.get(63 - 7), Team.PLAYER));
        this.boardTiles.get(63 - 1).setPiece(new Knight("KNW", this.boardTiles.get(63 - 1), Team.PLAYER));
        this.boardTiles.get(63 - 6).setPiece(new Knight("KNW", this.boardTiles.get(63 - 6), Team.PLAYER));
        this.boardTiles.get(63 - 2).setPiece(new Bishop("BW", this.boardTiles.get(63 - 2), Team.PLAYER));
        this.boardTiles.get(63 - 5).setPiece(new Bishop("BW", this.boardTiles.get(63 - 5), Team.PLAYER));
        this.boardTiles.get(63 - 3).setPiece(new King("KW", this.boardTiles.get(63 - 3), Team.PLAYER));
        this.boardTiles.get(63 - 4).setPiece(new Queen("QW", this.boardTiles.get(63 - 4), Team.PLAYER));

        this.boardTiles.get(63 - 8 + 0).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 0)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 7)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 7)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 1)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 1)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 6)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 6)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 2)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 2)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 5)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 5)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 3)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 3)), Team.PLAYER));
        this.boardTiles.get(63 - (8 + 4)).setPiece(new Pawn("PW", this.boardTiles.get(63 - (8 + 4)), Team.PLAYER));

    }

}
