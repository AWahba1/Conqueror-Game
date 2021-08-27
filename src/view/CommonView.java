package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import control.*;
import engine.*;
import exceptions.FriendlyCityException;
import exceptions.TargetNotReachedException;
import units.*;
public class CommonView extends JFrame implements ActionListener, EndTurnListener {
	JButton worldMap;
	JButton endTurn;
	JLabel turnCount;
	JLabel food;
	JLabel gold;
	JPanel buttonsPanel;
	String endTurnMessage="";
	boolean showWorldMap=true;
	public CommonView() 
	{
		Controller.gameModel.setEndlistener(this);
		 buttonsPanel=new JPanel();
		JPanel topPanel=new JPanel();
		
		//this.setLayout(null);
		 worldMap=new JButton("WorldMap View");
		 
		
		 endTurn=new JButton("Endturn");
		
		worldMap.addActionListener(this);
		
		endTurn.addActionListener(this);
		
		buttonsPanel.add(worldMap);
		
		buttonsPanel.add(endTurn);
		
		JLabel playerName=new JLabel("Your name: "+ Controller.gameModel.getPlayer().getName());
		 turnCount=new JLabel("Turn Count: "+Controller.gameModel.getCurrentTurnCount());
		 gold=new JLabel("Gold: "+Controller.gameModel.getPlayer().getTreasury());
		 food=new JLabel("Food: "+Controller.gameModel.getPlayer().getFood());
		
		topPanel.add(playerName);
		topPanel.add(turnCount);
		topPanel.add(gold);
		topPanel.add(food);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(buttonsPanel,BorderLayout.SOUTH);
		topPanel.setBounds(400,50,100,40);
		buttonsPanel.setBounds(400,550,100,40);
		
		
		
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   // this.setVisible(true);
	    this.revalidate();
	    this.repaint();
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==worldMap)
		{
			
			this.dispose();
			Controller.worldMapView.gold.setText("Gold: "+Controller.gameModel.getPlayer().getTreasury());
			Controller.worldMapView.setVisible(true); //check --call fillPanels in any action
		}
		
		if (e.getActionCommand().equals("Endturn"))
		{	
			
			if(Controller.gameModel.getPlayer().getControlledCities().size() == Controller.gameModel.getAvailableCities().size() )
			{	
				String [] options= {"Exit","Play Again"};
				 int x = JOptionPane.showOptionDialog(null, "YOU WON!!!!",
				            "Game Over",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				 if (x==0 ||x==1)
				 {
					 this.dispose();
					 showWorldMap=false;
				 }
				 if (x==1)
				 {
					 this.dispose();
					 this.setVisible(false);
					 new Controller();
					 showWorldMap=false;
				 }
			}
			else if (Controller.gameModel.getCurrentTurnCount()> Controller.gameModel.getMaxTurnCount())
			{
				String [] options= {"Exit","Play Again"};
				 int x = JOptionPane.showOptionDialog(null, "YOU LOST!!!",
				            "Game Over",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				 if (x==0 ||x==1)
				 {
					 this.dispose();
					 showWorldMap=false;
				 }
				 if (x==1)
				 {
					 this.dispose();
					 this.setVisible(false);
					 new Controller();
					 showWorldMap=false;
				 }
			}
			Controller.gameModel.endTurn();
			this.dispose();
			if (showWorldMap)
				Controller.worldMapView=new WorldMapView();
		
		
			displayFood();
			displayTreasury();
			displayTurnCount();
			
		
		
		
			
			
			
			
		
		}//endturn condition
	
		
		
	}
	
	public void displayTreasury()
	{
		gold.setText("Gold: "+Controller.gameModel.getPlayer().getTreasury());
	}
	public void displayFood()
	{
		food.setText("Food: "+Controller.gameModel.getPlayer().getFood());
	}
	public void displayTurnCount()
	{
		turnCount.setText("Turn Count: "+Controller.gameModel.getCurrentTurnCount());
	}


	@Override
	public void onEndTurn1(Boolean f, double a, double b, double c) {
		endTurnMessage+="New turn count: "+Controller.gameModel.getCurrentTurnCount()+"\n";
		endTurnMessage+="All buildings aren't cooling down now \n";
		endTurnMessage+="Current recruit for all military buildings is now 0 \n";
		endTurnMessage+="New turn count: "+Controller.gameModel.getCurrentTurnCount()+"\n";
		endTurnMessage+="Total gold harvested:  "+a+"\n";
		endTurnMessage+="Total food harvested:  "+b+"\n";
		endTurnMessage+="Total food consumed:  "+c+"\n";
		
		if (f==false)
		{
			endTurnMessage+="Food wasn't enough and all armies units soldier count is decreased by 10%";
		}
		endTurnMessage+="Gold & Food have been updated accordingly  "+c+"\n";
		
		
	}


	@Override
	public void onEndTurn2(Army a) {
		String [] options= {"Go to Battle","Lay Siege","Cancel"};
		 int x = JOptionPane.showOptionDialog(null, "Your army has reached "+a.getTarget(),
		            "Army reached city",
		            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		 
		 if (x==0)
		 {
			 this.dispose();
			 Controller.battleView=new BattleView(a, a.getTarget());
			 showWorldMap=false;
		 }
		 //lay siege logic hena
		 else if (x==1)
		 {	
			City c=null;
			for (City z: Controller.gameModel.getAvailableCities())
				if (z.getName().equalsIgnoreCase(a.getTarget()))
					c=z;
			try {
				Controller.gameModel.getPlayer().laySiege(a, c);
			} catch (TargetNotReachedException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage() );
			} catch (FriendlyCityException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage() );
			}
			//Controller.worldMapView=new WorldMapView();
			 
		 }
		
		 
		 
		
	}


	
	public void onEndTurn3(Boolean f, City c) {
		 Army attacking=null;
		 for (Army a: Controller.gameModel.getPlayer().getControlledArmies())
			 if (a.getCurrentLocation().equalsIgnoreCase(c.getName()))
				 attacking=a;
		if (f==false)
		{	
			if (c.getTurnsUnderSiege()==0)
				return;
			String [] options= {"Go to Battle"};
			 int x = JOptionPane.showOptionDialog(null,c.getName()+" has been besieged for "+ c.getTurnsUnderSiege()+" turns "
			 		+ "and all city's defending army units has lost 10% soldiers  \n" 
					+"Do you want to attack?" ,
			            "Besiegied city",
			            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			
			 if (x==0)
			 {
				 this.dispose();
				 Controller.battleView=new BattleView(attacking,c.getName());
				 showWorldMap=false;
			 }
		}
		else
		{	
			this.dispose();
			Controller.battleView=new BattleView(attacking,c.getName());
			JOptionPane s=new JOptionPane();
			s.showMessageDialog(Controller.battleView, "City has been besieged for 3 turns and you have to attack now");
			showWorldMap=false;
			
		}
		
	}
	
	
}
