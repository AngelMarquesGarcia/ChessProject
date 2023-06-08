/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import chessproject.ChessMove;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import utilities.DrawButton;
import utilities.ResignButton;
import utilities.RestartButton;
import utilities.UndoButton;

/**
 *
 * @author Ángel Marqués García
 */
public class GameMenu extends JPanel {

    private final Dimension BOXSIZE = new Dimension(200, 350);
    private static JTextArea textArea;
    private static int halfMoves = 0;
    private static final String HALF_MOVE_SEPARATOR = "    |    ";

    public static void addHalfMove(ChessMove m) {
        String start;
        String end;
        if (halfMoves % 2 == 0) {
            start = Integer.toString(((int) halfMoves / 2) + 1) + ".  ";
            end = "";
        } else {
            start = HALF_MOVE_SEPARATOR;
            end = "\n";
        }
        textArea.append(start);
        textArea.append(m.toString());
        textArea.append(end);
        halfMoves++;
    }

    public static void removeHalfMove() {
        halfMoves--;
        String text = textArea.getText();
        String[] lines = text.split("\n");
        String allButLast = "";
        for (int i = 0; i < lines.length - 1; i++) {
            allButLast += lines[i] + "\n";
        }

        String lastElement = lines[lines.length - 1];
        String[] parts = lastElement.split(HALF_MOVE_SEPARATOR);
        String ending = "";
        if (parts.length == 3) {
            ending = parts[0].strip();
        }
        allButLast += ending;
        textArea.setText(allButLast);
    }

    public static void reset() {
        halfMoves = 0;
        if (textArea != null) {
            textArea.setText("");
        }
    }

    public GameMenu() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
        setBackground(Color.ORANGE);

        JPanel top = new JPanel();

        JButton resignButton = new ResignButton();
        JButton drawButton = new DrawButton();
        JButton undoButton = new UndoButton();

        resignButton.addActionListener((ActionListener) resignButton);
        drawButton.addActionListener((ActionListener) drawButton);
        undoButton.addActionListener((ActionListener) undoButton);

        top.add(resignButton);
        top.add(drawButton);
        top.add(undoButton);

        add(top);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane mid = new JScrollPane(textArea);
        mid.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mid.setPreferredSize(new Dimension(200, 75));
        mid.setBackground(Color.orange);
        mid.setPreferredSize(new Dimension(200, 300));
        add(mid);

        JPanel bot = new JPanel();
        JButton restartButton = new RestartButton(); //"Resign", 
        restartButton.addActionListener((ActionListener) restartButton);
        bot.add(restartButton);
        JCheckBox checkBox = new JCheckBox("Introducir FEN");
        bot.add(checkBox);
        add(bot);

        setVisible(true);
        /*JPanel mid = new JPanel();
        mid.setPreferredSize(new Dimension(200, 300));
        mid.setBackground(Color.orange);
        add(mid);*/
    }

}
