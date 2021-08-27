package view;
import control.Controller;
import engine.*;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import buildings.*;
import units.*;
import java.util.*;
public class CityView extends CommonView{
	String cityName;
	City c;
	ArrayList <String>allBuildingsButtons=new ArrayList<>();
	ArrayList <String>allAvailable=new ArrayList<>();
	public CityView(String cityName)
	{	JButton initiate=new JButton("Initiate Army");
	initiate.addActionListener(this);
	buttonsPanel.add(initiate);
	initiate.addActionListener(this);
		//this.setLayout(null);
		this.cityName=cityName;
		 c=null;
		for (City x: Controller.gameModel.getAvailableCities())
			if (x.getName().equalsIgnoreCase(cityName))
				c=x;
		
		JLabel cityLabel =new JLabel(cityName);
		
		JLabel economicalLabel=new JLabel("Economical Buildings");
		JLabel militaryLabel=new JLabel("Military Buildings");
		JLabel armyLabel=new JLabel("Defending Army");
		JLabel unbuiltBuildingsLabel=new JLabel("Unbuilt Buildings");
		
		
		JPanel economicalPanel=new JPanel();
		JPanel militaryPanel=new JPanel();
		JPanel armyPanel=new JPanel();
		JPanel unbuiltBuildingsPanel=new JPanel();
		
		
		
		allAvailable.add("Farm");
		allAvailable.add("Market");
		allAvailable.add("Stable");
		allAvailable.add("Barracks");
		allAvailable.add("ArcheryRange");
		
		
		for (EconomicBuilding b : c.getEconomicalBuildings())
		{
			if (b instanceof Farm)
				{	
					JButton farmButton=new JButton("Farm");
					farmButton.addActionListener(this);
					economicalPanel.add(farmButton);
					allBuildingsButtons.add("Farm");
					
				}
			else if (b instanceof Market)
				{
				
				JButton marketButton=new JButton("Market");
				marketButton.addActionListener(this);
				economicalPanel.add(marketButton);
				allBuildingsButtons.add("Market");
				}
		}
		
		for (MilitaryBuilding b : c.getMilitaryBuildings())
		{
			if (b instanceof ArcheryRange)
			{
				JButton archeryButton=new JButton("ArcheryRange");
				archeryButton.addActionListener(this);
				allBuildingsButtons.add("ArcheryRange");
				militaryPanel.add(archeryButton);
				
				
			}
			else if (b instanceof Barracks)
			{
				JButton barracksButton=new JButton("Barracks");
				barracksButton.addActionListener(this);
				allBuildingsButtons.add("Barracks");
				militaryPanel.add(barracksButton);
			}
			else if (b instanceof Stable)
			{	JButton stableButton=new JButton("Stable");
				stableButton.addActionListener(this);
				allBuildingsButtons.add("Stable");
				militaryPanel.add(stableButton);
			}
		}
		int count=0;
		for (Unit u :c.getDefendingArmy().getUnits())
		{ 
			JButton unitButton=null;
			if (u instanceof Archer) 
				unitButton= new JButton("Archer");
			
			if (u instanceof Cavalry) 
				unitButton= new JButton("Cavalry");
			if (u instanceof Infantry) 
				unitButton= new JButton("Infantry");
			unitButton.addActionListener(this);
			armyPanel.add(unitButton);
			unitButton.setActionCommand(""+count);
	}
		for(String s: allAvailable) {
			if(!allBuildingsButtons.contains(s)) {
				JButton temp= new JButton(s);
				temp.addActionListener(this);
				unbuiltBuildingsPanel.add(temp);
			}
			
				
			
		}
		

		
		
		economicalLabel.setBounds(100,100,300,100);
		economicalPanel.setBounds(100,180,500,50);
		
		militaryLabel.setBounds(100,200,100,100);
		militaryPanel.setBounds(100,280 ,500 ,50 );
		
		armyLabel.setBounds(100,300 ,100 ,100 );
		armyPanel.setBounds(100,380 ,500 ,50 );
		
		unbuiltBuildingsLabel.setBounds(100,400 ,100 ,100 );
		unbuiltBuildingsPanel.setBounds(100,500 ,500 ,50 );
		
		JLabel stationaryLabel=new JLabel("Stationary armies in the city");
		JPanel stationaryPanel=new JPanel();
		
		stationaryLabel.setBounds(100,570,700,80);
		stationaryPanel.setBounds(100,650,700,50);
		int i=0;
		for (Army x: Controller.gameModel.getPlayer().getControlledArmies())
		{
			if (x.getCurrentLocation().equalsIgnoreCase(cityName))
			{
				JButton arm=new JButton(""+x.getCurrentLocation()+" 's army");
				arm.setActionCommand(i+ " stationary");
				arm.addActionListener(this);
				stationaryPanel.add(arm);
				
			}
			i++;
		}
		
		this.add(economicalLabel);
		this.add(economicalPanel);
		this.add(militaryLabel);
		this.add(militaryPanel);
		this.add(armyLabel);
		this.add(armyPanel);
		this.add(unbuiltBuildingsLabel);
		this.add(unbuiltBuildingsPanel);
		this.add(stationaryLabel);
		this.add(stationaryPanel);
		this.add(new JLabel(" "));
		
		
		
		
		
		
		

		
		//this.setVisible(true);
	    this.revalidate();
	    this.repaint();
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().length()==1) {
			Unit temp= c.getDefendingArmy().getUnits().get(Integer.parseInt(e.getActionCommand()));
			String s="Your Unit Level: "+temp.getLevel()+"\n";
			s+="Current Soldier Count: "+temp.getCurrentSoldierCount()+"\n";
			s+="Maximum Soldier Count: "+temp.getMaxSoldierCount();
			
			JOptionPane.showMessageDialog(null,s,"Unit Info",JOptionPane.INFORMATION_MESSAGE);
		}
		else {
		
		if(e.getActionCommand().equals("Initiate Army")) {
			this.dispose();
			new InitiateArmyView(c);
		}
		super.actionPerformed(e);
		String temp=e.getActionCommand();
		if(!allBuildingsButtons.contains(temp)) {
			int price=0;
			if(temp.equals("Farm"))
				price=1000;
				else
					if(temp.equals("Market"))
						price=1500;
					else
						
			if(temp.equals("ArcheryRange"))
				price=1500;
			else
			if(temp.equals("Barracks"))
				price=2000;
			
			else if (temp.equals("Stable"))
				price=2500;
			
			if (price!=0)
			{
				String [] options= {"Build","Cancel"};
				
				 int x = JOptionPane.showOptionDialog(null, "Do you want to buy this building for "+price,
				            "Shop",
				            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				 if (x==1)
				 {
					 this.dispose();
					 Controller.cityView=new CityView(cityName);
						Controller.cityView.setVisible(true);
				 }
				 if(x==0) {
					
						
						try {
							Controller.gameModel.getPlayer().build(e.getActionCommand(),cityName);
							JOptionPane.showMessageDialog(null,"Building Added Succesfully and your current gold is "+Controller.gameModel.getPlayer().getTreasury(),"New Building",JOptionPane.INFORMATION_MESSAGE);
							 this.dispose();
							 Controller.cityView=new CityView(cityName);
								Controller.cityView.setVisible(true);
								this.displayTreasury();
							
							
							
							
							
						} catch (NotEnoughGoldException e1) {
							String message=e1.getMessage();
							JOptionPane.showMessageDialog(null,message,"No Enough Gold",JOptionPane.ERROR_MESSAGE);
						}
						
						
		
					 
					 
					 
				 }
			}
				
				 	
	
		
		
		}
		
		if(allBuildingsButtons.contains(temp)) {
			Player p=Controller.gameModel.getPlayer();
			MilitaryBuilding a=null;
			EconomicBuilding d=null;
			if(temp.equals("ArcheryRange")) {
				for(MilitaryBuilding b:c.getMilitaryBuildings()) {
					if(b instanceof ArcheryRange)
						a=b;
				}
			}
			if(temp.equals("Barracks")) {
				for(MilitaryBuilding b:c.getMilitaryBuildings()) {
					if(b instanceof Barracks)
						a=b;
				}
				
			}
			if(temp.equals("Stable")) {
				for(MilitaryBuilding b:c.getMilitaryBuildings()) {
					if(b instanceof Stable)
						a=b;
				}
				
			}
			if(temp.equals("Farm")) {
				for(EconomicBuilding b:c.getEconomicalBuildings()) {
					if(b instanceof Farm)
						d=b;
				}

			}
			if(temp.equals("Market")) {
				for(EconomicBuilding b:c.getEconomicalBuildings()) {
					if(b instanceof Market)
						d=b;
				}

			}
			if(a!=null) {
				String s="Level: "+a.getLevel()+"\n";
				s+="Upgrade Cost: "+a.getUpgradeCost()+"\n";
				s+="Current Recruited Units: "+a.getCurrentRecruit()+"\n";
				s+="Maximum Units You Can Recruit: "+a.getMaxRecruit()+"\n";
				s+="Recruitment Cost: "+a.getRecruitmentCost()+"\n";
				if(a.isCoolDown())
				s+="Building Is Cooling Down!!!";
						
				String [] options= {"Upgrade","Recruit","Cancel"};
				
				 int x = JOptionPane.showOptionDialog(null, s,
				            temp,
				            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				 if(x==0) {
					 try {
						 try {
							Controller.gameModel.getPlayer().upgradeBuilding(a);
							JOptionPane.showMessageDialog(null,"Building Upgraded Succesfully and your current gold is "+Controller.gameModel.getPlayer().getTreasury(),"Upgraded Building",JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
							Controller.cityView=new CityView(cityName);
							Controller.cityView.setVisible(true);
							super.displayTreasury();
						 
						 
						 } catch (NotEnoughGoldException e1) {
							String message=e1.getMessage();
							JOptionPane.showMessageDialog(null,message,"No Enough Gold",JOptionPane.ERROR_MESSAGE);
						}
					} catch (BuildingInCoolDownException e1) {
						String message=e1.getMessage();
						JOptionPane.showMessageDialog(null,message,"Cooling Down Building",JOptionPane.ERROR_MESSAGE);
						
					} catch (MaxLevelException e1) {
						String message=e1.getMessage();
						JOptionPane.showMessageDialog(null,message,"Maximum Level Building",JOptionPane.ERROR_MESSAGE);
					
					}
				 }
				 
				 
				 if(x==1) {
					 try { if(a instanceof ArcheryRange) {
						 p.recruitUnit("Archer", cityName);
						 JOptionPane.showMessageDialog(null,"Archer Recruited Successfully and your current gold is"+Controller.gameModel.getPlayer().getTreasury(),"Recruited Unit",JOptionPane.INFORMATION_MESSAGE);
						 
					 }
					 if(a instanceof Stable) {
						 p.recruitUnit("Cavalry", cityName);
						 JOptionPane.showMessageDialog(null,"Cavalry Recruited Successfully and your current gold is"+Controller.gameModel.getPlayer().getTreasury(),"Recruited Unit",JOptionPane.INFORMATION_MESSAGE);
					 }
					 if(a instanceof Barracks) {
						 p.recruitUnit("Infantry", cityName); 
						 JOptionPane.showMessageDialog(null,"Infantry Recruited Successfully and your current gold is"+Controller.gameModel.getPlayer().getTreasury(),"Recruited Unit",JOptionPane.INFORMATION_MESSAGE);
					 }}
					 catch (BuildingInCoolDownException e1) {
							String message=e1.getMessage();
							JOptionPane.showMessageDialog(null,message,"Building is cooling down",JOptionPane.ERROR_MESSAGE);
						} catch (MaxRecruitedException e1) {
							String message=e1.getMessage();
							JOptionPane.showMessageDialog(null,message,"Maximum Recruited Units Reached",JOptionPane.ERROR_MESSAGE);
						} catch (NotEnoughGoldException e1) {
							String message=e1.getMessage();
							JOptionPane.showMessageDialog(null,message,"No Enough Gold",JOptionPane.ERROR_MESSAGE);
						}
					 this.dispose();
					 Controller.cityView=new CityView(cityName);
						Controller.cityView.setVisible(true);
		 
				 }
				 if(x==2)
				 {
					 this.dispose();
					 Controller.cityView=new CityView(cityName);
						Controller.cityView.setVisible(true);
				 }
			}
			else {
				String s="Level: "+d.getLevel()+"\n";
				s+="Upgrade Cost: "+d.getUpgradeCost()+"\n";
				
				if(d.isCoolDown())
				s+="Building Is Cooling Down!!!";
						
				String [] options= {"Upgrade","Cancel"};
				
				 int x = JOptionPane.showOptionDialog(null, s,
				            temp,
				            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				 if(x==0) {
					 try {
						 try {
							Controller.gameModel.getPlayer().upgradeBuilding(d);
							JOptionPane.showMessageDialog(null,"Building Upgraded Succesfully and your current gold is "+Controller.gameModel.getPlayer().getTreasury(),"Upgraded Building",JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
							Controller.cityView=new CityView(cityName);
							Controller.cityView.setVisible(true);
							 this.displayTreasury();
						 
						 
						 } catch (NotEnoughGoldException e1) {
							String message=e1.getMessage();
							JOptionPane.showMessageDialog(null,message,"No Enough Gold",JOptionPane.ERROR_MESSAGE);
						}
					} catch (BuildingInCoolDownException e1) {
						String message=e1.getMessage();
						JOptionPane.showMessageDialog(null,message,"Cooling Down Building",JOptionPane.ERROR_MESSAGE);
						
					} catch (MaxLevelException e1) {
						String message=e1.getMessage();
						JOptionPane.showMessageDialog(null,message,"Maximum Level Building",JOptionPane.ERROR_MESSAGE);
					
					}
				 }
				 if(x==1)
					 {
					 this.dispose();
					 Controller.cityView=new CityView(cityName);
						Controller.cityView.setVisible(true);
					 }
				 
			}
		}}
		String [] x=e.getActionCommand().split(" ");
		if ( x!=null && 1<x.length && x[1].equalsIgnoreCase("stationary"))
		{
			String [] options= {"Info"};
			Army arm=Controller.gameModel.getPlayer().getControlledArmies().get(Integer.parseInt(x[0]));
			
			
			 int z = JOptionPane.showOptionDialog(null, "Do you want to check the army's info?",
			            "Info",
			            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			 if (z==0)
			 {
				 this.dispose();
				 new UnitView(arm);
				 
			 }
		}
		
		
	
		
	}
	

}