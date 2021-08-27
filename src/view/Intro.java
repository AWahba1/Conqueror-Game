package view;

import javax.swing.*;

import engine.*;
import control.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import units.*;
public class Intro extends JFrame implements ActionListener {
	public static JTextField textfield;
	String chosenCity="";
	public Intro()
	{
		
		
		
		 textfield=new JTextField();
		
		
	    NewPanel jp=new NewPanel();
	    jp.add(textfield);
	    
	    
	  
	    JLabel enterName=new JLabel("Enter your name");
	    enterName.setBounds(570,100, 500, 100 );
	    enterName.setForeground(Color.RED);
	   
	    textfield.setBounds(570,170, 150, 50);
	    jp.add(enterName);
	    jp.add(textfield);
	    
	    JButton start=new JButton("Start");
	    start.addActionListener(this);
	    
	    JButton cairo=new JButton("Cairo");
	    JButton rome=new JButton("Rome");
	    JButton sparta=new JButton("Sparta");
	    cairo.addActionListener(this);
	    rome.addActionListener(this);
	    sparta.addActionListener(this);
	   
	    JLabel chooseCity=new  JLabel("Choose a city");
	    chooseCity.setForeground(Color.red);
	    chooseCity.setBounds(600,250,500,100);
	    cairo.setBounds(500,350,90,90);
	    rome.setBounds(600,350,90,90);
	    sparta.setBounds(700,350,90,90);
	    start.setBounds(600,500,110,70);
	    
	    jp.add(cairo);
	    jp.add(rome);
	    jp.add(sparta);
	    jp.add(start);
	    jp.add(chooseCity);
	    
	    
	    this.add(jp);
			
		    this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		    this.setUndecorated(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		    this.revalidate();
		    this.repaint();
	}

	
	public void actionPerformed(ActionEvent e){
	
		if(e.getActionCommand().equals("Cairo"))
			chosenCity="Cairo";
		if(e.getActionCommand().equals("Rome"))
			chosenCity="Rome";
		if(e.getActionCommand().equals("Sparta"))
			chosenCity="Sparta";
		if(e.getActionCommand().equals("Start"))
		{
			if (Intro.textfield.getText().equals(""))
				JOptionPane.showMessageDialog(null,"Please enter a name","Name is empty ",JOptionPane.WARNING_MESSAGE);
			else if (chosenCity.equals(""))
				JOptionPane.showMessageDialog(null,"Please choose a city to start with","No Chosen City ",JOptionPane.WARNING_MESSAGE);
				
			else
			{
				try {
					Controller.gameModel=new Game(Intro.textfield.getText(),chosenCity);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				this.dispose();
//				Army test=new Army("Cairo");
//				Archer a=new Archer(1,5,1,1,1);
//				a.setParentArmy(test);
//				test.getUnits().add(a);
				//Controller.gameModel.getPlayer().getControlledArmies().add(test);
				//Controller.gameModel.getPlayer().getControlledArmies().add(test);
//				
//				
//				
//				
//				
//				Army test2=new Army("Sparta");
//				test2.setCurrentStatus(Status.IDLE);
				//test2.setTarget("Rome");
				//Infantry b=new Infantry(1,3,1,1,1);
				//test2.setDistancetoTarget(0);
//				b.setParentArmy(test2);
//				test2.getUnits().add(b);
				
				//Controller.gameModel.getPlayer().getControlledArmies().add(test2);
				
				
				
//				City c=null;
//				for (City x: Controller.gameModel.getAvailableCities())
//					if (x.getName().equalsIgnoreCase("Rome"))
//						c=x;
				//c.setDefendingArmy(test2);
//				c.setUnderSiege(true);
//				c.setTurnsUnderSiege(0);
				
				
				
				
				Controller.worldMapView=new WorldMapView();
				//Controller.battleView=new BattleView(test,c.getName());
				//Controller.cityView=new CityView(chosenCity);
				
			}
		//}
		
	}

	
	
	
}
}