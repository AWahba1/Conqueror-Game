package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import units.*;
import control.Controller;
import engine.*;
import java.awt.*;
import java.util.*;
public class InitiateArmyView extends CommonView {
	City c;
	Army a;
	public InitiateArmyView(City c) {
		super();
		this.c=c;
		String name=c.getName();
		a=c.getDefendingArmy();
		JPanel b=new JPanel();
		JLabel ca=new JLabel("Please Choose Unit You Want to Initiate new army from "+name);
		ca.setBounds(100,200, 1000, 50);
		b.setBounds(100, 250, 400, 700);
		int i=0;
		for(Unit u: a.getUnits()) {
			if(u instanceof Archer) {
				JButton temp=new JButton("Archer");
				temp.addActionListener(this);
				temp.setActionCommand(""+i);
				b.add(temp);
			}
			if(u instanceof Cavalry) {
				JButton temp=new JButton("Cavalry");
				temp.addActionListener(this);
				temp.setActionCommand(""+i);
				b.add(temp);
			}
			if(u instanceof Infantry) {
				JButton temp=new JButton("Infantry");
				temp.addActionListener(this);
				temp.setActionCommand(""+i);
				b.add(temp);
				
			}
		}
		i++;
		this.add(ca);
		this.add(b);
		JButton backButton=new JButton("Back");
		backButton.addActionListener(this);
		buttonsPanel.add(backButton);
		
		this.add(new JLabel(""));
		this.setVisible(true);
		this.repaint();
		this.revalidate();
		
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("back"))
		{
			this.dispose();
			this.setVisible(false);
			Controller.worldMapView=new WorldMapView();
		}
		else {
			if(e.getActionCommand().equals("WorldMap View")||e.getActionCommand().equals("Endturn")) {
				this.setVisible(false);
				this.dispose();
				super.actionPerformed(e);
			}
			else {
		Unit temp=c.getDefendingArmy().getUnits().get(Integer.parseInt(e.getActionCommand()));
		String s="Your Unit Level: "+temp.getLevel()+"\n";
		s+="Current Soldier Count: "+temp.getCurrentSoldierCount()+"\n";
		s+="Maximum Soldier Count: "+temp.getMaxSoldierCount()+"\n";
		s+="If you do not want to use this unit press cancel ";
		String [] options2= {"Use","Cancel"};
		 int x2 = JOptionPane.showOptionDialog(null, s,
		            "Unit Info",
		            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);
		 if(x2==0) {
			 Controller.gameModel.getPlayer().initiateArmy(c, temp);
			 this.setVisible(false);
			 Controller.worldMapView=new WorldMapView();
			 
		 }
		 
		
		
			}
			
		
		
		
		
		
		
	}

}
}