/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai.gui;

import chess_ai.GameBoard;
import chess_ai.Tile;
import chess_ai.enums.Team;
import chess_ai.moves.AttackMove;
import chess_ai.moves.Move;
import chess_ai.moves.RepositionMove;
import chess_ai.pieces.Piece;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MarekPC
 */
public class MainViewController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextFlow textFlow;
    @FXML
    private Button btnRevert;
    private Stage stage;
    protected GameBoard board;
    private List<StackPane> tilePanes;
    private Piece selectedPiece;
    private List<Move> highlitghtedMoves;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resetGame();
        btnRevert.setOnAction((e) -> {
            revertHistory();
        });

    }

    private void renderBoard() {

        List<Tile> tiles = this.board.getBoardState();
        for (int i = 0; i < 64; i++) {
            Tile t = tiles.get(i);
            StackPane st = this.tilePanes.get(i);
            // RERENDER FIGURKU
            if (st.getChildren().size() == 2) {
                st.getChildren().remove(1);

            }
            if (t.isFree()) {
                st.getChildren().add(new EmptyTilePane(t));
            } else if (t.getPiece().getTeam() == Team.PLAYER) {
                st.getChildren().add(new PlayerPiecePane(t));
            } else if (t.getPiece().getTeam() == Team.AI) {
                st.getChildren().add(new AiPiecePane(t));
            }

        }

    }

    public void unshowMoves(List<Move> moves) {
        if (moves != null) {
            for (Move n : moves) {
                unshowMove(n);
            }
        }
    }

    public void showMoves(List<Move> moves) {
        for (Move n : moves) {
            showMove(n);
        }
    }

    public boolean isHighlited(Tile t) {

        for (Move highlitghtedMove : highlitghtedMoves) {
            if (highlitghtedMove.getEndTile() == t) {
                return true;
            }

        }
        return false;

    }

    public Move getMoveByEndTile(Tile t) {
        for (Move highlitghtedMove : highlitghtedMoves) {
            if (highlitghtedMove.getEndTile() == t) {
                return highlitghtedMove;
            }

        }
        return null;
    }

    public void showMove(Move move) {
        int tileIndex = move.getEndTile().getPosition();
        if (move instanceof RepositionMove) {
            EmptyTilePane ep = (EmptyTilePane) this.tilePanes.get(tileIndex).getChildren().get(1);
            ep.highlight();
        } else if (move instanceof AttackMove) {
            AiPiecePane am = (AiPiecePane) this.tilePanes.get(tileIndex).getChildren().get(1);
            am.highlight();
        }

    }

    public void unshowMove(Move move) {
        int tileIndex = move.getEndTile().getPosition();
        if (move instanceof RepositionMove) {
            EmptyTilePane ep = (EmptyTilePane) this.tilePanes.get(tileIndex).getChildren().get(1);
            ep.unhighlight();

        } else if (move instanceof AttackMove) {
            AiPiecePane am = (AiPiecePane) this.tilePanes.get(tileIndex).getChildren().get(1);
            am.unhighlight();
        }

    }

    private void makePanes() {
        this.tilePanes.clear();
        boolean flipColor = false;
        int counter = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane stack = new StackPane();
                VBox box = new VBox();
                Rectangle rect = new Rectangle(80, 80);
                
                if (flipColor) {
                    rect.setFill(Color.GRAY);
                } else {
                    rect.setFill(Color.WHITE);
                }
                box.getChildren().addAll(rect,new Text(""+counter++));
                stack.getChildren().add(box);
//                stack.getChildren().add(rect);
                this.gridPane.add(stack, col, row);
                this.tilePanes.add(stack);
                flipColor = !flipColor;
            }
            //flip back
            flipColor = !flipColor;
        }
    }

    public void makeMove(Move move) {
        boolean won = board.executeMove(move,true);
        selectedPiece = null;
        unshowMoves(highlitghtedMoves);
        renderBoard();
        addToHistory(move);
        if (won) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("GAME OVER");
            a.showAndWait();
            resetGame();
            System.out.println("GAME OVER");
        }

    }

    private void resetGame() {
        this.board = new GameBoard("AI_CHESS");
        this.board.init();
        this.gridPane.getChildren().clear();
        this.tilePanes = new ArrayList<>();
        makePanes();
        this.renderBoard();
    }

    private void addToHistory(Move move) {
        Text t = new Text(move.moveString() + "\n");

        textFlow.getChildren().addAll(t);
    }

    private void revertHistory() {
        Move move = board.getLastMove();
        if (move == null) {
            return;
        }
        move = move.revertMove(move);
        makeRevertMove(move);
        textFlow.getChildren().remove(textFlow.getChildren().size()-1);

    }

    private void makeRevertMove(Move move) {

        selectedPiece = null;
        board.executeMove(move,false);
        renderBoard();
    }

    private class EmptyTilePane extends StackPane {

        private Tile tile;

        public EmptyTilePane(Tile t) {
            super();
            this.tile = t;
            this.makeListeners();
        }

        private void makeListeners() {
            super.setOnMousePressed(event -> {
                System.out.println("Clicked tile # " + tile.getPosition());
                if (selectedPiece != null) {
                    Move move = getMoveByEndTile(tile);
                    if (move != null) {
                        makeMove(move);
                    } else {
                        selectedPiece = null;
                        unshowMoves(highlitghtedMoves);
                        highlitghtedMoves = null;
                    }
                }

            });
        }

        private void highlight() {
            super.getChildren().add(new Circle(5.0, Color.AQUA));

        }

        private void unhighlight() {
            super.getChildren().clear();

        }

    }

    private class AiPiecePane extends StackPane {

        private Tile tile;

        public AiPiecePane(Tile tile) {
            super();
            this.tile = tile;

            setPicture();
            this.makeListeners();

        }

        private void makeListeners() {
            super.setOnMousePressed(event -> {
                if (selectedPiece != null) {
                    Move move = getMoveByEndTile(tile);
                    if (move != null) {
                        makeMove(move);
                    }
                }

            });
        }

        private void setPicture() {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(this.getClass().getResource("res/" + tile.getPiece().toString() + ".gif").toExternalForm()));
            super.getChildren().add(imageView);

        }

        private void highlight() {
            super.getChildren().add(new Circle(10.0, Color.RED));

        }

        private void unhighlight() {
            super.getChildren().remove(1);

        }
    }

    private class PlayerPiecePane extends StackPane {

        private Tile tile;

        public PlayerPiecePane(Tile tile) {
            super();
            this.tile = tile;

            setPicture();
            this.makeListeners();

        }

        private void makeListeners() {
            super.setOnMousePressed(event -> {
                System.out.println("Clicked tile # " + tile.getPosition());
                if (selectedPiece == null) {
                    selectedPiece = tile.getPiece();
                    List<Move> moves = board.getPossibleMoves(selectedPiece);
                    highlitghtedMoves = moves;
                    showMoves(moves);

                } else if (selectedPiece != null) {
                    unshowMoves(highlitghtedMoves);
                    selectedPiece = tile.getPiece();
                    List<Move> moves = board.getPossibleMoves(selectedPiece);
                    showMoves(moves);
                    highlitghtedMoves = moves;
                }

            }
            );
        }

        private void setPicture() {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(this.getClass().getResource("res/" + tile.getPiece().toString() + ".gif").toExternalForm()));
            super.getChildren().add(imageView);

        }
    }

}
