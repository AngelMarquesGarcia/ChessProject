package views.containers;

import chessproject.ChessMove;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.metal.MetalCheckBoxIcon;
import views.buttons.DrawButton;
import views.buttons.ParentButton;
import views.buttons.ResignButton;
import views.buttons.RestartButton;
import views.buttons.UndoButton;

/**
 *
 * @author Ángel Marqués García
 */
public class GameMenu extends JPanel {

    private static final Dimension BOXSIZE = new Dimension(200, 350);
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

    private ParentButton[] buttons = new ParentButton[4];
    
    public GameMenu() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
        mid.setBackground(Color.orange);
        mid.setPreferredSize(new Dimension(200, 300));
        add(mid);

        JPanel bot = new JPanel();
        JButton restartButton = new RestartButton(); //"Resign", 
        restartButton.addActionListener((ActionListener) restartButton);
        bot.add(restartButton);
        JCheckBox checkBox = new JCheckBox("Guardar partida", true);
        bot.add(checkBox);
        add(bot);
        
        buttons[0] = (ParentButton) resignButton;
        buttons[1] = (ParentButton) drawButton;
        buttons[2] = (ParentButton) undoButton;
        buttons[3] = (ParentButton) restartButton;
        setPreferredSize(BOXSIZE);
        setVisible(true);
        /*JPanel mid = new JPanel();
        mid.setPreferredSize(new Dimension(200, 300));
        mid.setBackground(Color.orange);
        add(mid);*/
        
        //This can be used to have the checkbox be scalable, but it looks horrifying
        //checkBox.setIcon (new MetalCheckBoxIcon () {
        //    protected int getControlSize() { return 20; }
        //});
    }

    @Override
    public final void setPreferredSize(Dimension dim){
        BOXSIZE.setSize(dim);
        super.setPreferredSize(BOXSIZE);
        setSize(BOXSIZE);
        Component mid = this.getComponents()[1];
        mid.setPreferredSize(new Dimension(dim.width,dim.height*6/7));
        Dimension btnSize = new Dimension(dim.height/7,dim.height/12);
        for (ParentButton button:buttons){           
            ImageIcon imageIcon = new ImageIcon(button.ICON_PATH);
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(dim.width/10, dim.width/10, Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back
            button.setIcon(imageIcon);
            
            button.setPreferredSize(btnSize);
        }
    }
}
