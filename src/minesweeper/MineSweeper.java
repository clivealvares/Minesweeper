/*
 * Clive Alvares
 * MineSweeper
 * 
 */
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author clive
 */
public class MineSweeper implements ActionListener{
    int amtButtons = 20;
    JFrame frame = new JFrame("MineSweeper");
    JButton reset = new JButton("Reset");
    // put this into constructor
    JButton[][] buttons = new JButton[amtButtons][amtButtons];
    int[][] info = new int[amtButtons][amtButtons]; 
    Container buttonGrid = new Container();
    int amtMines = 30;
    final int BOMB = 10;
    
    public MineSweeper(){
        frame.setSize(500,600);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        reset.addActionListener(this);
        buttonGrid.setLayout(new GridLayout(20, 20));
        
// Create Buttons
        for(int i= 0;i < buttons.length; i++) {
            for(int j=0; j < buttons[0].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttonGrid.add(buttons[i][j]);
            }
        }
        createMines();
        frame.add(buttonGrid, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    public static void main(String[] args) {
       new MineSweeper();
    }
    public void createMines() {
        ArrayList<Integer> minesLoc = new ArrayList<Integer>();
        // initialize locations of mines
        for(int i = 0; i < info.length; i++) {
            for(int j = 0; j < info[0].length; j++){
                minesLoc.add(i*100+j);
                
            }
        }
        
        // reset info and choose atMines uniqe mines 
        info = new int[amtButtons][amtButtons];
        for(int b = 0; b < amtMines; b++) {
            int mine = (int) (Math.random() * minesLoc.size());
            info[minesLoc.get(mine)/100][minesLoc.get(mine)%100] = BOMB;
            minesLoc.remove(mine);
            
        }
        
        // find amount of bombs surrounding the button
        for(int c = 0; c < info.length; c++) {
            for(int d = 0; d < info[0].length; d++) {
                if(info[c][d] != BOMB) {
                    int surroundings =0;
                    // top left
                    if(c > 0 && d>0 && info[c-1][d-1] == BOMB) { 
                        surroundings++;                                
                    }
                    // top
                    if(d>0 && info[c][d-1] == BOMB ) {
                        surroundings++;
                      
                    }
                    // top right
                    if(d > 0 && c < info.length-1 && info[c + 1][d-1] == BOMB) {
                        surroundings++;
                    }
                    // left
                    if(c > 0 && info[c-1][d] == BOMB) {
                        surroundings++;
                    }
                    // right
                    if(c < info.length -1 && info[c+1][d] == BOMB) {
                        surroundings++;
                    }
                    // bottum left
                    if(c > 0 && d < info[0].length -1 && info[c -1][d+1] == BOMB) {
                        surroundings++;
                    } 
                    // bottum
                    if(d < info[0].length -1 && info[c][d+1] == BOMB) {
                        surroundings++;
                    }
                    // bottum right
                    if(c< info.length -1 && d < info[0].length -1 && info[c+1][d+1]
                            == BOMB) {
                        surroundings++;
                    }
                    info[c][d] = surroundings;
                }
            }
        }
    }
    
    
    public void gameLost(){
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[0].length; j++){
                if(buttons[i][j].isEnabled()) {
                   if(info[i][j] != BOMB){
                       buttons[i][j].setText(info[i][j]+"");
                       buttons[i][j].setEnabled(false);
                       
                   } else {
                       buttons[i][j].setText("X");
                       buttons[i][j].setEnabled(false);
                       
                   }
                
                }
                
            }
        }
    }
    
    public void zeroKiller(ArrayList<Integer> kill){
        if(kill.size() == 0) {
            return;
        } else {
        int i = kill.get(0) / 100;
        int j = kill.get(0) % 100;
        kill.remove(0);
        
            //TOP LEFT
            if(i > 0 && j> 0 && buttons[i-1][j-1].isEnabled()) {
                buttons[i-1][j-1].setText(info[i-1][j-1] + "");
                buttons[i-1][j-1].setEnabled(false);
                if(info[i-1][j-1] == 0) {
                    kill.add((i-1)*100 + (j-1));
                }
            }
            // TOP
            if(j> 0 && buttons[i][j-1].isEnabled()) {
                buttons[i][j-1].setText(info[i][j-1] + "");
                buttons[i][j-1].setEnabled(false);
                if(info[i][j-1] == 0) {
                    kill.add((i)*100 + (j-1));
                }
            }
            // TOP RIGHT
            if(i < info.length -1 && j > 0 && buttons[i+1][j-1].isEnabled() ) {
                buttons[i+1][j-1].setText(info[i+1][j-1] + "");
                buttons[i+1][j-1].setEnabled(false);
                if(info[i+1][j-1] == 0) {
                    kill.add((i+1)*100 + (j-1));
                }
                
            }
            // LEFT
            if(i > 0 && buttons[i-1][j].isEnabled()) {
                buttons[i -1][j].setText(info[i-1][j]+"");
                buttons[i-1][j].setEnabled(false);
                if(info[i-1][j] == 0) {
                    kill.add((i-1)*100 + j);
                }
            }
            // RIGHT
            if(i < info.length -1 && buttons[i+1][j].isEnabled()) {
                buttons[i + 1][j].setText(info[i+1][j] + "");
                buttons[i+1][j].setEnabled(false);
                if(info[i+1][j] == 0) {
                    kill.add((i+1)*100 + j);
                }
            }
            //Bottum LEFT
            if(i > 0 && j < info[0].length -1 && buttons[i-1][j+1].isEnabled()) {
                buttons[i-1][j+1].setText(info[i-1][j+1] + "");
                buttons[i-1][j+1].setEnabled(false);
                if(info[i-1][j+1] == 0) {
                    kill.add((i-1)*100 + (j+1));
                }
            }
            // Bottum
            if(j < info[0].length-1 && buttons[i][j+1].isEnabled()) {
                buttons[i][j+1].setText(info[i][j+1] + "");
                buttons[i][j+1].setEnabled(false);
                if(info[i][j+1] == 0) {
                    kill.add((i)*100 + (j+1));
                }
            }
            // Bottum RIGHT
            if(i < info.length -1 && j < info[0].length - 1 
                    && buttons[i+1][j+1].isEnabled()) {
                buttons[i+1][j+1].setText(info[i+1][j+1] + "");
                buttons[i+1][j+1].setEnabled(false);
                if(info[i+1][j+1] == 0) {
                    kill.add((i+1)*100 + (j+1));
                }
                
            }
        
        zeroKiller(kill);
        
        }
    }
    
    public void checkWin(){
        boolean won = true;
        for(int i = 0; i < info.length; i++ ) {
            for(int j = 0; i < info[0].length; i++) {
                if(info[i][j]!= BOMB && buttons[i][j].isEnabled() == true) {
                    won = false;
                }
            }
        }
        if(won == true){
            
          JOptionPane.showMessageDialog( frame, "you win");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource().equals(reset)) {
            for(int i = 0; i < buttons.length; i++){
                for(int j = 0; j< buttons[0].length; j++){
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setText("");
                    
                }
            }
            createMines();
        }
        else {
            for(int i = 0; i < info.length; i++) {
                for(int j=0; j < info[0].length; j++) {
                    if(event.getSource().equals(buttons[i][j])) {
                        if(info[i][j] == BOMB) {
                            buttons[i][j].setForeground(Color.red);
                            buttons[i][j].setText("X");
                            gameLost();
                            
                        } else if (info[i][j]== 0){
                            buttons[i][j].setText(info[i][j] +"");
                            buttons[i][j].setEnabled(false); 
                            ArrayList<Integer> toKill = new ArrayList<Integer>();
                            
                            toKill.add((i*100) + j);
                            zeroKiller(toKill);
                            checkWin();
                            
                        
                        } else {
                        
                        buttons[i][j].setText(info[i][j] +"");
                        buttons[i][j].setEnabled(false);
                        checkWin();
                        }
                   }
                }
            }
        }
 
    }
}
