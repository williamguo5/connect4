import java.awt.event.*;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class Connect4Board extends JLayeredPane implements ActionListener {
	
	private Integer[][] board;
	private Image backgroundImage;
	private String colBlank = "colBlank.png";
	private String colMouseOver = "colMouseOver.png";
	private String colClick = "colClick.png";
	private ColumnButton col0Button;
	private ColumnButton col1Button;
	private ColumnButton col2Button;
	private ColumnButton col3Button;
	private ColumnButton col4Button;
	private ColumnButton col5Button;
	private ColumnButton col6Button;

	
	/**
	 * Constructor for a soduku panel
	 * @throws IOException 
	 */
	public Connect4Board() throws IOException {
        setBorder(BorderFactory.createLineBorder(Color.black));
        
		setLayout(new GridLayout(1, 7));
        col0Button = new ColumnButton();
		col1Button = new ColumnButton();
		col2Button = new ColumnButton();
		col3Button = new ColumnButton();
		col4Button = new ColumnButton();
		col5Button = new ColumnButton();
		col6Button = new ColumnButton();

		generateBoard();
    }

	/**
	 * Method to generate a new board
	 * NOTE: this isn't a real/valid soduku board.
	 * @throws IOException 
	 */
    public void generateBoard() throws IOException {
    	add(col0Button);
		add(col1Button);
		add(col2Button);
		add(col3Button);
		add(col4Button);
		add(col5Button);
		add(col6Button);
		
		col0Button.setBackgroundImg(colBlank);
		col1Button.setBackgroundImg(colBlank);
		col2Button.setBackgroundImg(colBlank);
		col3Button.setBackgroundImg(colBlank);
		col4Button.setBackgroundImg(colBlank);
		col5Button.setBackgroundImg(colBlank);
		col6Button.setBackgroundImg(colBlank);
		
		createListeners();
	}
    
    public void createListeners(){
    	col0Button.addMouseListener(new MouseListener() {
          @Override
          public void mouseReleased(MouseEvent e) {
              try {
					col0Button.setBackgroundImg(colMouseOver);
					col0Button.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          }
          @Override
          public void mousePressed(MouseEvent e) {
				col0Button.addToken();
                try {
  					col0Button.setBackgroundImg(colClick);
  					col0Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
          }
          @Override
          public void mouseClicked(MouseEvent e) {
//          	try {
//					col0Button.setBackgroundImg("colBlank.png");
//					col0Button.repaint();
//					connect4Board.repaint();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
          }
			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					col0Button.setBackgroundImg(colMouseOver);
					col0Button.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					col0Button.setBackgroundImg(colBlank);
					col0Button.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
    	
    	col1Button.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
  					col1Button.setBackgroundImg(colMouseOver);
  					col1Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	col1Button.addToken();
            	try {
  					col1Button.setBackgroundImg(colClick);
  					col1Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//            	try {
//  					col0Button.setBackgroundImg("colBlank.png");
//  					col0Button.repaint();
//  					connect4Board.repaint();
//  				} catch (IOException e1) {
//  					// TODO Auto-generated catch block
//  					e1.printStackTrace();
//  				}
            }
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				try {
  					col1Button.setBackgroundImg(colMouseOver);
  					col1Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  			@Override
  			public void mouseExited(MouseEvent e) {
  				try {
  					col1Button.setBackgroundImg(colBlank);
  					col1Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  		});
    	
    	col2Button.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
  					col2Button.setBackgroundImg(colMouseOver);
  					col2Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	col2Button.addToken();
                try {
  					col2Button.setBackgroundImg(colClick);
  					col2Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//            	try {
//  					col0Button.setBackgroundImg("colBlank.png");
//  					col0Button.repaint();
//  					connect4Board.repaint();
//  				} catch (IOException e1) {
//  					// TODO Auto-generated catch block
//  					e1.printStackTrace();
//  				}
            }
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				try {
  					col2Button.setBackgroundImg(colMouseOver);
  					col2Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  			@Override
  			public void mouseExited(MouseEvent e) {
  				try {
  					col2Button.setBackgroundImg(colBlank);
  					col2Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  		});
    
    	col3Button.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
  					col3Button.setBackgroundImg(colMouseOver);
  					col3Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	col3Button.addToken();
                try {
  					col3Button.setBackgroundImg(colClick);
  					col3Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//            	try {
//  					col0Button.setBackgroundImg("colBlank.png");
//  					col0Button.repaint();
//  					connect4Board.repaint();
//  				} catch (IOException e1) {
//  					// TODO Auto-generated catch block
//  					e1.printStackTrace();
//  				}
            }
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				try {
  					col3Button.setBackgroundImg(colMouseOver);
  					col3Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  			@Override
  			public void mouseExited(MouseEvent e) {
  				try {
  					col3Button.setBackgroundImg(colBlank);
  					col3Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  		});
    
    	col4Button.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
  					col4Button.setBackgroundImg(colMouseOver);
  					col4Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	col4Button.addToken();
                try {
  					col4Button.setBackgroundImg(colClick);
  					col4Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//            	try {
//  					col0Button.setBackgroundImg("colBlank.png");
//  					col0Button.repaint();
//  					connect4Board.repaint();
//  				} catch (IOException e1) {
//  					// TODO Auto-generated catch block
//  					e1.printStackTrace();
//  				}
            }
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				try {
  					col4Button.setBackgroundImg(colMouseOver);
  					col4Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  			@Override
  			public void mouseExited(MouseEvent e) {
  				try {
  					col4Button.setBackgroundImg(colBlank);
  					col4Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  		});
    	
    	col5Button.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
  					col5Button.setBackgroundImg(colMouseOver);
  					col5Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	col5Button.addToken();
                try {
  					col5Button.setBackgroundImg(colClick);
  					col5Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//            	try {
//  					col0Button.setBackgroundImg("colBlank.png");
//  					col0Button.repaint();
//  					connect4Board.repaint();
//  				} catch (IOException e1) {
//  					// TODO Auto-generated catch block
//  					e1.printStackTrace();
//  				}
            }
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				try {
  					col5Button.setBackgroundImg(colMouseOver);
  					col5Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  			@Override
  			public void mouseExited(MouseEvent e) {
  				try {
  					col5Button.setBackgroundImg(colBlank);
  					col5Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  		});
   
    	col6Button.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
  					col6Button.setBackgroundImg(colMouseOver);
  					col6Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	col6Button.addToken();
                try {
  					col6Button.setBackgroundImg(colClick);
  					col6Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//            	try {
//  					col0Button.setBackgroundImg("colBlank.png");
//  					col0Button.repaint();
//  					connect4Board.repaint();
//  				} catch (IOException e1) {
//  					// TODO Auto-generated catch block
//  					e1.printStackTrace();
//  				}
            }
  			@Override
  			public void mouseEntered(MouseEvent e) {
  				try {
  					col6Button.setBackgroundImg(colMouseOver);
  					col6Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  			@Override
  			public void mouseExited(MouseEvent e) {
  				try {
  					col6Button.setBackgroundImg(colBlank);
  					col6Button.repaint();
  				} catch (IOException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  			}
  		});
    
    
    }

    /**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(650,550);
    }

	/**
	 * Method to paint the board on the screen
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
    	g.drawImage(backgroundImage, 0, 0, null);
    }
    
    public void setBackgroundImg(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
      }
 

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}  

}
