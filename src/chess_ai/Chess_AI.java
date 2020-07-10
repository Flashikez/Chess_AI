/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess_ai;

import chess_ai.gui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MarekPC
 */
public class Chess_AI  extends Application{

    /**
     * @param args the command line arguments
     * 
     * 
     * 
     */
    
     @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(MainViewController.class.getResource("mainView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
        
        
        
        
        
    }
    
}
