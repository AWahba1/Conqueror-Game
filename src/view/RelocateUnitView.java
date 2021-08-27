package view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Controller;
import engine.City;
import exceptions.MaxCapacityException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;
import engine.*;
import units.*;
public class RelocateUnitView extends CommonView{
	Army a;
	Unit u=null;
	public RelocateUnitView(Army a) {
		
		this.a=a;
		
//		for(Army t:Controller.gameModel.getPlayer().getControlledArmies()) {
//			for(Unit tu: t.getUnits()) {
//				allunitss.add(tu);
//			}
//			for(City cu:Controller.gameModel.getPlayer().getControlledCities()) {
//				for(Unit tu: cu.getDefendingArmy().getUnits()) {
//					allunitss.add(tu);
//				}
//			}
//		}
		JPanel b=new JPanel();
		JLabel c=new JLabel("Please Choose Unit you want to relocate to your army");
		c.setBounds(100,200, 1000, 50);
		b.setBounds(100, 250, 400, 700);
		int i=0; //index army in controlled armies
		int j=0; //index unit in the army
		for(Army x: Controller.gameModel.getPlayer().getControlledArmies()) {
			
			if (!a.equals(x))
			{	
				j=0;
				for (Unit u: x.getUnits())
				{
					if(u instanceof Archer) {
						JButton temp=new JButton("Archer");
						temp.addActionListener(this);
						temp.setActionCommand("1 "+i+" "+j);
						b.add(temp);
					}
					if(u instanceof Cavalry) {
						JButton temp=new JButton("Cavalry");
						temp.addActionListener(this);
						temp.setActionCommand("1 "+i+" "+j);
						b.add(temp);
					}
					if(u instanceof Infantry) {
						JButton temp=new JButton("Infantry");
						temp.addActionListener(this);
						temp.setActionCommand("1 "+i+" "+j);
						b.add(temp);
						
					}
				}
			
				j++;
			
			}
			i++;
			
		}
		
		i=0; // index city
		for(City city:Controller.gameModel.getPlayer().getControlledCities()) {
			j=0;
			for(Unit u: city.getDefendingArmy().getUnits()) {
				if(u instanceof Archer) {
					JButton temp=new JButton("Archer");
					temp.addActionListener(this);
					temp.setActionCommand("2 "+i+" "+j);
					b.add(temp);
				}
				if(u instanceof Cavalry) {
					JButton temp=new JButton("Cavalry");
					temp.addActionListener(this);
					temp.setActionCommand("2 "+i+" "+j);
					b.add(temp);
				}
				if(u instanceof Infantry) {
					JButton temp=new JButton("Infantry");
					temp.addActionListener(this);
					temp.setActionCommand("2 "+i+" "+j);
					b.add(temp);
					
				}
			}
			i++;
			j++;
		}
		
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
		if (e.getActionCommand().equalsIgnoreCase("back"))
		{
			this.dispose();
			this.setVisible(false);
			Controller.worldMapView=new WorldMapView();
		}
		else if (e.getActionCommand().equals("WorldMap View")||e.getActionCommand().equals("Endturn"))
			 {
				this.setVisible(false);
				this.dispose();
				super.actionPerformed(e);
			}
			else {
				
				String []y=e.getActionCommand().split(" ");
				int x1=Integer.parseInt(y[0]);
				int i=Integer.parseInt(y[1]);
				int j=Integer.parseInt(y[2]);
				if (x1==1)
				{
					Army arm=Controller.gameModel.getPlayer().getControlledArmies().get(i);
					Unit temp=arm.getUnits().get(j);
					String s="Your Unit Level: "+temp.getLevel()+"\n";
					s+="Current Soldier Count: "+temp.getCurrentSoldierCount()+"\n";
					s+="Maximum Soldier Count: "+temp.getMaxSoldierCount()+"\n";
					s+="If you do not want to use this unit press cancel ";
					String [] options2= {"Use","Cancel"};
					 int x2 = JOptionPane.showOptionDialog(null, s,
					            "Unit Info",
					            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);
					 if(x2==0) {
						 try {
							 arm.relocateUnit(temp);
							
						} catch (MaxCapacityException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage());
						}
						 this.setVisible(false);
						 Controller.worldMapView=new WorldMapView(); //check
						 
					 			}
					 
					
				}
				else if (x1==2)
				{
					Army arm=Controller.gameModel.getPlayer().getControlledCities().get(i).getDefendingArmy();
					Unit temp=arm.getUnits().get(j);
					String s="Your Unit Level: "+temp.getLevel()+"\n";
					s+="Current Soldier Count: "+temp.getCurrentSoldierCount()+"\n";
					s+="Maximum Soldier Count: "+temp.getMaxSoldierCount()+"\n";
					s+="If you do not want to use this unit press cancel ";
					String [] options2= {"Use","Cancel"};
					 int x2 = JOptionPane.showOptionDialog(null, s,
					            "Unit Info",
					            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);
					 if(x2==0) {
						 try {
							 arm.relocateUnit(temp);
							
						} catch (MaxCapacityException e1) {
							JOptionPane.showMessageDialog(null,e1.getMessage());
						}
						 this.setVisible(false);
						 Controller.worldMapView=new WorldMapView(); //check
						 
					 			}
				}
				
//		Unit temp=a.getUnits().get(Integer.parseInt(e.getActionCommand()));
//		String s="Your Unit Level: "+temp.getLevel()+"\n";
//		s+="Current Soldier Count: "+temp.getCurrentSoldierCount()+"\n";
//		s+="Maximum Soldier Count: "+temp.getMaxSoldierCount()+"\n";
//		s+="If you do not want to use this unit press cancel ";
//		String [] options2= {"Use","Cancel"};
//		 int x2 = JOptionPane.showOptionDialog(null, s,
//		            "Unit Info",
//		            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, null);
//		 if(x2==0) {
//			 try {
//				 Controller.worldMapView.iwanttorelocateto.relocateUnit(temp);
//				
//			} catch (MaxCapacityException e1) {
//				JOptionPane.showMessageDialog(null,e1.getMessage());
//			}
//			 this.setVisible(false);
//			 Controller.worldMapView=new WorldMapView();
//			 
//		 }
//		 
//		
//		
//			}
			
		
		
		
		
		
		
	}
	}
}




