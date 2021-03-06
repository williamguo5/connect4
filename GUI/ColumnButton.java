import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ColumnButton extends JLayeredPane{
	private Image backgroundImage;
	private ImageIcon player1;
	private ImageIcon player2;
	private ImageIcon player0;
	private ArrayList<JLabel> tokens;
	private int top;
	private boolean full = false;
	
	public ColumnButton(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(6,1));
        
        tokens = new ArrayList<JLabel>();
        
        for(int i = 0; i < 6; i++){
        	JLabel t = new JLabel();
        	tokens.add(t);
        }
        
       
        player1 = new ImageIcon("token1.png");
        player2 = new ImageIcon("token2.png");
        player0 = new ImageIcon("tokenBlank.png");
        
        top = 1;
        generateTokens();
//		repaint();
	}
	
	
	public void generateTokens(){
		for(JLabel t : tokens){
			add(t, 1);
			t.setIcon(player0);
		}
	}
	
	public void addToken(int playerID){
		if(top == 6){
			top = 0;
		}
		if(!full){
			if(playerID == 1){
				tokens.get(top).setIcon(player1);
			}else{
				tokens.get(top).setIcon(player2);
			}
		}
		if(top == 0){
			full = true;
		}
		top++;
	}
	
	public boolean isFull(){
		return full;
	}
	
	public void resetTokens(){
		for(JLabel t : tokens){
			t.setIcon(player0);
			repaint();
		}
		top = 1;
		full = false;
//		for(int i = 0; i < 6; i ++){
//			tokens.get(i).setIcon(player0);
//		}
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(90,480);
    }
	
	public void paintComponent(Graphics g) {
//        super.paintComponent(g);       
        
    	g.drawImage(backgroundImage, 0, 0, null);
    }
	
	public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
	}
}
