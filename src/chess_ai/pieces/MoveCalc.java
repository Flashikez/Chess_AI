/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai.pieces;

import chess_ai.GameBoard;
import chess_ai.Tile;
import chess_ai.enums.Team;
import chess_ai.moves.AttackMove;
import chess_ai.moves.Move;
import chess_ai.moves.RepositionMove;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MarekPC
 */
public class MoveCalc {

    public static List<Move> legalMoves(Piece piece, GameBoard board) {

        if (piece instanceof Pawn) {
            return calculatePawn((Pawn) piece, board);
        }
        if (piece.vectorizedLegals) {
            return calculateVectorizedMoves(piece, board);
        }

        int[] legals = piece.legals(board);
        List<Move> moves = new ArrayList<Move>();
        int startPos = piece.getBoardPosition();
        List<Tile> boardTiles = board.getBoardState();
        Tile startTile = piece.tile;
        Team team = piece.team;
        for (int legalMove : legals) {
            int movedPosition = legalMove + startPos;
            boolean outOfBonds = outOfBoard(movedPosition);
            if (!outOfBonds) {
                Tile movedTile = getTileAtPos(movedPosition, boardTiles);
                boolean occupied = !movedTile.isFree();
                if (occupied && team != movedTile.getPiece().team) {
                    AttackMove attackMove = new AttackMove(piece, movedTile.getPiece(), startTile, movedTile);
                    moves.add(attackMove);
                } else if (!occupied) {
                    RepositionMove move = new RepositionMove(piece, startTile, movedTile);
                    moves.add(move);
                }

            }

        }
        return moves;
    }

    private static List<Move> calculateVectorizedMoves(Piece piece, GameBoard board) {
        int[] legals = piece.legals(board);
        List<Move> moves = new ArrayList<Move>();
        int startPos = piece.getBoardPosition();
        List<Tile> boardTiles = board.getBoardState();
        Team team = piece.team;
        Tile startTile = piece.tile;
        for (int legalMove : legals) {
            for (int i = 1; i <= 7; i++) {
                int movedPosition = startPos + (legalMove * i);
                boolean outOfBonds = outOfBoard(movedPosition);
                boolean edgeCase = isOnEdge(movedPosition);
                if (!outOfBonds) {
                    Tile movedTile = getTileAtPos(movedPosition, boardTiles);
                    boolean occupied = !movedTile.isFree();
                    if (occupied && team != movedTile.getPiece().team) {
                        AttackMove attackMove = new AttackMove(piece, movedTile.getPiece(), startTile, movedTile);
                        moves.add(attackMove);
                        break;
                    } else if (!occupied) {
                        RepositionMove move = new RepositionMove(piece, startTile, movedTile);
                        moves.add(move);
                    } else if (occupied) {
                        break;
                    }

                }
                if (edgeCase) {
                    break;
                }

            }

        }
        for (Move move : moves) {
            System.out.print(move.getEndTile().getPosition() + "  ");

        }

        return moves;

    }

    private static List<Move> calculatePawn(Pawn piece, GameBoard board) {
        int[] legals = piece.legals(board);
        List<Move> moves = new ArrayList<Move>();
        int startPos = piece.getBoardPosition();
        List<Tile> boardTiles = board.getBoardState();
        Team team = piece.team;
        Tile startTile = piece.tile;
        int[] attacks = piece.attackLegals();
        for (int legalMove : legals) {
            int movedPosition = startPos + legalMove;
            boolean outOfBonds = outOfBoard(movedPosition);
            if (!outOfBonds) {
                Tile movedTile = getTileAtPos(movedPosition, boardTiles);
                boolean occupied = !movedTile.isFree();
                if (!occupied) {
                    RepositionMove move = new RepositionMove(piece, startTile, movedTile);
                    moves.add(move);
                } else {
                    break;
                }

            }
        }
        for (int attackLegal : attacks) {
            int movedPosition = startPos + attackLegal;
            boolean outOfBonds = outOfBoard(movedPosition);
            if (!outOfBonds) {
                Tile movedTile = getTileAtPos(movedPosition, boardTiles);
                boolean occupied = !movedTile.isFree();
                if (occupied && team != movedTile.getPiece().team) {
                    AttackMove attackMove = new AttackMove(piece, movedTile.getPiece(), startTile, movedTile);
                    moves.add(attackMove);

                }
            }

        }
        return moves;
    }

    private static Tile getTileAtPos(int pos, List<Tile> tiles) {
        return tiles.get(pos);
    }

    // CROSSED BORDERS?
    private static boolean outOfBoard(int position) {

        return position < 0 || position > 63;
    }

    private static boolean isOnEdge(int movedPosition) {
        return movedPosition % 8 == 0 || movedPosition % 8 == 7;

    }



}
