package view;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
public class NewPanel extends JPanel {
	ImageIcon background;
	public NewPanel()
	{
		background=new ImageIcon("Images/intro.jpg");
		setLayout(null);
	}
	public void paintComponent(Graphics g) {
	    Graphics2D g1=(Graphics2D)g;

	    // Draw the background image.
	    g.drawImage(background.getImage(), 0, 0, this);
	  }
}
