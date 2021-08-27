package view;

import units.Army;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import units.*;
import control.Controller;
import engine.*;
import java.awt.*;
import java.util.*;
public class UnitView extends CommonView {
	Army a;
public UnitView(Army a) {
	super();
	this.a=a;
	JPanel b=new JPanel();
	JLabel c=new JLabel("Army's Units");
	c.setBounds(100,200, 200, 50);
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
	this.add(c);
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
	super.actionPerformed(e);
	if (e.getActionCommand().equalsIgnoreCase("back"))
	{
		this.dispose();
		Controller.worldMapView=new WorldMapView();
	}
	else if (e.getActionCommand().length()==1)
	{
		Unit temp= a.getUnits().get(Integer.parseInt(e.getActionCommand()));
		String s="Your Unit Level: "+temp.getLevel()+"\n";
		s+="Current Soldier Count: "+temp.getCurrentSoldierCount()+"\n";
		s+="Maximum Soldier Count: "+temp.getMaxSoldierCount();
		
		JOptionPane.showMessageDialog(null,s,"Unit Info",JOptionPane.INFORMATION_MESSAGE);
	}
}
}

