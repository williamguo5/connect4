import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Settings extends JPanel implements ActionListener{
	
	private JLabel player1Label;
	private JLabel player2Label;
	private JLabel themeLabel;
	
	private JComboBox<String> opponentDropDown;
	private JComboBox<String> playerDropDown;
	private JComboBox<String> themeDropDown;
	
	private String[] opponentChoices = {"Human", "Easy", "Intermediate", "Hard"};
	private String[] defaultPlayer= {"Human"};
	private String[] themeChoices = {"Classic", "2016 Election", "Wes Anderson", "Retro"};
	
	/**
	 * In player 2 field:
	 * 	0 represent human
	 * 	1 represents easy AI
	 * 	2 represents med AI
	 * 	3 represents hard AI
	 */
	private int player2;
	private String theme;
	
	private Dimension standard;
	private Color background;
	private Color text;
	private Font style;
	
	public Settings() {
		super();
		setLayout(new GridLayout(3, 2));
		background = new Color(233,232,207);
		setOpaque(false);
		theme = "Classic";				
		
		standard = new Dimension(125, 70);
		text = new Color(78, 128, 166);
		style = new Font("Myriad Pro", Font.BOLD, 17);
		
		player1Label = new JLabel("Player1", SwingConstants.CENTER);
		player2Label = new JLabel("Player2", SwingConstants.CENTER);
		themeLabel = new JLabel("Theme", SwingConstants.CENTER);
		
		player1Label.setFont(style);
		player1Label.setForeground(text);
		player2Label.setFont(style);
		player2Label.setForeground(text);
		themeLabel.setFont(style);
		themeLabel.setForeground(text);
		
		opponentDropDown = new JComboBox<String>(opponentChoices);
		playerDropDown = new JComboBox<String>(defaultPlayer);
		playerDropDown.setEnabled(false);
		themeDropDown = new JComboBox<String>(themeChoices);
		
		opponentDropDown.setPreferredSize(standard);
		playerDropDown.setPreferredSize(standard);
		themeDropDown.setPreferredSize(standard);
		
		
		playerDropDown.setFont(style);
		playerDropDown.setForeground(text);
		
		opponentDropDown.setSelectedIndex(0);
		opponentDropDown.addActionListener(this);
		opponentDropDown.setFont(style);
		opponentDropDown.setForeground(text);
			
		themeDropDown.setSelectedIndex(0);
		themeDropDown.setFont(style);
		themeDropDown.setForeground(text);
		themeDropDown.addActionListener(this);
		
		add(player1Label);
		add(playerDropDown);
		add(player2Label);
		add(opponentDropDown);
		add(themeLabel);
		add(themeDropDown);
	}
	
	/**
	 * Action for the combo boxes. Sets theme as string.
	 * Turns opponent choice into a number 0 - 3 inclusive.
	 * 0 meaning a second person. 1-3 meaning Ai easy - hard.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox activeBox = (JComboBox) e.getSource();
		String choice = (String)activeBox.getSelectedItem();
		if(activeBox == opponentDropDown) {	//check this equals method
			switch(choice) {
			case "Human": player2 = 0;
				break;
			case "Easy": player2 = 1;
				break;
			case "Intermediate": player2 = 2;
				break;
			case "Hard": player2 = 3;
				break;
			}
		} else if (activeBox == themeDropDown) {
			theme = choice;	// could be changed to ints like above if easier
		}
		
	}
	
	/**
	 * Sets the background field according to the theme
	 * @param theme
	 */
	public void setThemeBackground(Color theme) {
		background = theme;
	}

	/**
	 * Gets opponent selected
	 */
	public int getOpponent() {
		return player2;
	}
	
	/**
	 * Gets theme selected for New Game
	 * @return
	 */
	public String getTheme() {
		return theme;
	}
	
	/**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(250,150);
    }
	
}