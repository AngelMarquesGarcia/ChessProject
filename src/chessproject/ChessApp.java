/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chessproject;

import Game.ChessMatch;
import java.io.Serializable;
import java.util.List;
import views.containers.AppContainer;

/**
 * This is a singleton.
 * @author Ángel Marqués García 
 */
public class ChessApp implements Serializable {
    
    private static ChessApp app;
    public static ChessApp getChessApp(){
        if (app == null){
            app = new ChessApp();
        }
        return app;
    }
            
    private ChessMatch currentMatch;


    private List<ChessMatch> matchHistory;
    private List<ChessMatch> unfinishedMatches;
    
    private ChessApp(){
        UILauncher launcher = new UILauncher();
        launcher.launch();
        readFiles();
        if (currentMatch == null){
            currentMatch = new ChessMatch();
        }
    }

    public void removeFocusFromMatch(){
        currentMatch.removeFocus();
        AppContainer.getAppContainer().repaint();
    }
    
    private void readFiles() {
        System.out.println("ChessApp.readFiles() is incomplete");
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    public ChessMatch getCurrentMatch() {
        return currentMatch;
    }
    
    public void restart(String fen){
        System.out.println("ChessApp.restart() is incomplete. It should save the current match as completed?");
        if (fen == null || "".equals(fen)){
            currentMatch = new ChessMatch();
        } else{
            currentMatch = new ChessMatch(fen);
        }
        AppContainer.getAppContainer().repaint();
    }
}
