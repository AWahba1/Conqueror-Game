package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import units.*;
import control.Controller;
import engine.*;
import exceptions.FriendlyCityException;
import exceptions.TargetNotReachedException;

import java.awt.*;
import java.util.*;
public class WorldMapView extends CommonView  {
	Army  iwanttorelocateto;
	JPanel allCities;
	JPanel idleArmiesPanel;
	JPanel marchingArmiesPanel;
	JPanel besiegingArmiesPanel;
	JButton cairo;
	JButton rome;
	JButton sparta;
	ArrayList<JButton> idleArmies=new ArrayList<>();
	ArrayList<JButton> marchingArmies=new ArrayList<>();
	ArrayList<JButton> besiegingArmies=new ArrayList<>();
	boolean armyInitiated=false;
	JButton initiateArmy;
	public WorldMapView()
	{	
		
		JLabel cities=new JLabel("Available cities");
		JLabel idleArmies=new JLabel("Idle Armies");
		JLabel marchingArmies=new JLabel("Marching Armies");
		JLabel besiegingArmies=new JLabel("Besieging Armies");
		
		
		
		//this.setLayout(null);
		 
		 cairo= new JButton("Cairo");
		 rome= new JButton("Rome");
		 sparta= new JButton("Sparta");
		
		cairo.addActionListener(this);
		rome.addActionListener(this);
		sparta.addActionListener(this);
		
		cairo.setEnabled(false);
		rome.setEnabled(false);
		sparta.setEnabled(false);
		
		
		
		 allCities=new JPanel();
		 idleArmiesPanel=new JPanel();
		 marchingArmiesPanel=new JPanel();
		 besiegingArmiesPanel=new JPanel();
		
		
		allCities.add(cairo);
		allCities.add(sparta);
		allCities.add(rome);
		
		cities.setBounds(100,100,100,100);
		allCities.setBounds(100,180,220,50);
		
		idleArmies.setBounds(100,200,100,100);
		idleArmiesPanel.setBounds(100,280 ,800 ,50 );
		
		marchingArmies.setBounds(100,300 ,100 ,100 );
		marchingArmiesPanel.setBounds(100,380 ,220 ,50 );
		
		besiegingArmies.setBounds(100,400 ,100 ,100 );
		besiegingArmiesPanel.setBounds(100,500 ,220 ,100 );
		
		
		
		this.add(cities);
		this.add(allCities);
		this.add(idleArmies);
		this.add(idleArmiesPanel);
		this.add(marchingArmies);
		this.add(marchingArmiesPanel);
		this.add(besiegingArmies);
		this.add(besiegingArmiesPanel);
		this.add(new JLabel(" "));
		
		
//		if((Controller.gameModel.getPlayer().getControlledCities().size()==1)&&!armyInitiated) {
//			
//			initiateArmy=new JButton("initiateArmy");
//			initiateArmy.addActionListener(this);
//			buttonsPanel.add(initiateArmy);
//		}
	
		
		
		
		
		fillPanels();
		
		this.revalidate();
	    this.repaint();
	    
		
		this.setVisible(true);
		
		
		
	
	
	
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);	
		
		if (e.getActionCommand().equals("Cairo"))
		{
			
			Controller.cityView=new CityView("Cairo");
			Controller.cityView.setVisible(true);
			Controller.worldMapView.setVisible(false);
		}
		if (e.getActionCommand().equals("Rome"))
		{
			
			Controller.cityView=new CityView("Rome");
			Controller.cityView.setVisible(true);
			Controller.worldMapView.setVisible(false);
		}
		if (e.getActionCommand().equals("Sparta"))
		{
			
			Controller.cityView=new CityView("Sparta");
			Controller.cityView.setVisible(true);
			Controller.worldMapView.setVisible(false);
		}
		
		boolean isIdle=idleArmies.contains(e.getSource());
		boolean isMarching=marchingArmies.contains(e.getSource());
		boolean isBesieging=besiegingArmies.contains(e.getSource());
		ArrayList<Army> controlledArmiesCopy= new ArrayList<Army>();
		if (isMarching)
		{
			
			//Army a= Controller.gameModel.getPlayer().getControlledArmies().get(Integer.parseInt(e.getActionCommand()));
			String []y=e.getActionCommand().split(" ");
			Army a=null;
			for (Army x: Controller.gameModel.getPlayer().getControlledArmies()) {
				if (x.getTarget().equals(y[2]))
					a=x;
				else
					controlledArmiesCopy.add(x);
			}
			
				
			
			String s="Target City: "+a.getTarget()+"\n Turns until reaching city: "+a.getDistancetoTarget();
			
			String [] options= {"Units Info","relocateUnit"};
			 int x1 = JOptionPane.showOptionDialog(null, s,
			            "Marching Army",
			            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (x1==0)
			{
			this.dispose();
			new UnitView(a);	
			}
			
		}
		else if (isBesieging)
		{
			String []y=e.getActionCommand().split(" ");
			//Army a= Controller.gameModel.getPlayer().getControlledArmies().get(Integer.parseInt(e.getActionCommand()));
			Army a=null;
			for (Army x: Controller.gameModel.getPlayer().getControlledArmies()) {
				if (x.getTarget().equals(y[1]))
					a=x;
				else
					controlledArmiesCopy.add(x);
			}
			City c1=null;
			for (City c: Controller.gameModel.getAvailableCities())
				if (c.getName().equalsIgnoreCase(a.getTarget()))
					c1=c;
			
			String s="Besieged City: "+a.getTarget()+"\n Turns that the city was under siege: "+c1.getTurnsUnderSiege();

			
			String [] options= {"Units Info","relocateUnit"};
			 int x1 = JOptionPane.showOptionDialog(null, s,
			            "Besieging Army",
			            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (x1==0)
			{
				this.dispose();
				new UnitView(a);
			}
			
		}
		else if (isIdle)
		{
			//Army a= Controller.gameModel.getPlayer().getControlledArmies().get(Integer.parseInt(e.getActionCommand()));
			String []y=e.getActionCommand().split(" ");
			Army a=null;
			for (Army x: Controller.gameModel.getPlayer().getControlledArmies()) {
				if (x.getCurrentLocation().equals(y[0]))
					a=x;
				else
					controlledArmiesCopy.add(x);
			}
			
			String [] options= {"Units Info","Target A City","relocateUnit"};
			String s="Current Location: "+a.getCurrentLocation();
			 int x1 = JOptionPane.showOptionDialog(null, s,
			            "Idle Army",
			            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (x1==0)
			{
				this.dispose();
				new UnitView(a);
			}
			if (x1==1)
			{	
				String [] options2= {"Cairo","Rome","Sparta"};
				 int x2 = JOptionPane.showOptionDialog(null, "Choose a city for the army to target",
				            "Target A City",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
				 Controller.gameModel.targetCity(a, options2[x2]);
				
				
			}
			if(x1==2) {
				this.dispose();
				Controller.relocateView=new RelocateUnitView(a);
				
			}
		
		}
//		if(e.getActionCommand().equals("initiateArmy")) {
//			City current=Controller.gameModel.getPlayer().getControlledCities().get(0);
//			String [] options= {"Archer","Cavalry","Infantry"};
//			 int x = JOptionPane.showOptionDialog(null, "Please Choose Type OF Unit You Want to Start With",
//			            "InitiateArmy",
//			            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//			 
//			 if(x==0) {
//				 Archer a=new Archer(1,60,0.4,0.5,0.6);
//				 Controller.gameModel.getPlayer().initiateArmy(current, a);
//			 }
//			 if(x==1) {
//				 Cavalry a=new Cavalry(1,40,0.6,0.7,0.75);
//				 Controller.gameModel.getPlayer().initiateArmy(current, a);
//			 }
//			 if(x==2) {
//				 Infantry a=new Infantry(1,50,0.5,0.6,0.7);
//				 Controller.gameModel.getPlayer().initiateArmy(current, a); 
//			 }
//			 buttonsPanel.remove(initiateArmy);
//			 fillPanels();
//			 this.dispose();
//			 new WorldMapView();
//			 
//		}
		
		
		
		
	}
	//adjust any changes in armies or cities
	public void fillPanels()
	{
		
		JButton newArmy;
		int i=0;
		//System.out.println(Controller.gameModel.getPlayer().getControlledArmies().get(0).getCurrentStatus());
		for(Army a :Controller.gameModel.getPlayer().getControlledArmies())
		{	
			//kalam ely 3la button??
			if (a.getCurrentStatus()==Status.IDLE)
			{
				 newArmy=new JButton(a.getCurrentLocation()+" 's army");
				newArmy.addActionListener(this);
				idleArmies.add(newArmy);
				idleArmiesPanel.add(newArmy);
				System.out.println(a.getTarget());
			}
			else if (a.getCurrentStatus()==Status.MARCHING)
			{
				 newArmy=new JButton("Going to "+a.getTarget());
				newArmy.addActionListener(this);
				marchingArmies.add(newArmy);
				marchingArmiesPanel.add(newArmy);
				
			}
			else
			{
				 newArmy=new JButton("Besieging "+a.getTarget());
				newArmy.addActionListener(this);
				besiegingArmies.add(newArmy);
				besiegingArmiesPanel.add(newArmy);
			}
			//newArmy.setActionCommand(""+i);
			i++;
			
		}
		
		for(City a :Controller.gameModel.getPlayer().getControlledCities())
		{
			if (a.getName().equalsIgnoreCase("cairo"))
				cairo.setEnabled(true);
			if (a.getName().equalsIgnoreCase("rome"))
				rome.setEnabled(true);
			if (a.getName().equalsIgnoreCase("sparta"))
				sparta.setEnabled(true);
			
		}
	
	
	
	
	}







}
