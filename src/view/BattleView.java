package view;
import control.Controller;
import engine.*;
import exceptions.FriendlyFireException;
import units.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class BattleView extends CommonView implements AttackListener {
	String defendingCity;
	City c;
	JButton manual;
	JButton auto;
	Army attacker;
	Army defender;
	String attackerCommand;
	String defenderCommand;
	public BattleView (Army attacker, String defendingCity)
	{	
		//	System.out.println(defendingCity);
		this.attacker=attacker;
		this.defendingCity=defendingCity;
		c=null;
		for (City x: Controller.gameModel.getAvailableCities())
			if (x.getName().equalsIgnoreCase(defendingCity))
				c=x;
		defender=c.getDefendingArmy();
		JLabel attackerLabel=new JLabel ("Your army");
		JLabel defenderLabel=new JLabel ("Defending army");
		JPanel attackerPanel=new JPanel();
		JPanel defenderPanel=new JPanel();
		
		
		int cnt=0;
		for (Unit u: attacker.getUnits())
		{	
			
			
			if ( u instanceof Archer)
			{
				JButton unitButton=new JButton("Archer");
				unitButton.setActionCommand(""+cnt+ " attacking");
				unitButton.addActionListener(this);
				attackerPanel.add(unitButton);
			}
			else if ( u instanceof Infantry)
			{
				JButton unitButton=new JButton("Infantry");
				unitButton.setActionCommand(""+cnt+ " attacking");
				unitButton.addActionListener(this);
				attackerPanel.add(unitButton);
			}
			else if ( u instanceof Cavalry)
			{
				JButton unitButton=new JButton("Cavalry");
				unitButton.setActionCommand(""+cnt+ " attacking");
				unitButton.addActionListener(this);
				attackerPanel.add(unitButton);
			}
			cnt++;
			
			
		}
		
		cnt=0;
		for (Unit u: defender.getUnits())
		{	
			if ( u instanceof Archer)
			{
				JButton unitButton=new JButton("Archer");
				unitButton.setActionCommand(""+cnt+ " defending");
				unitButton.addActionListener(this);
				defenderPanel.add(unitButton);
			}
			else if ( u instanceof Infantry)
			{
				JButton unitButton=new JButton("Infantry");
				unitButton.setActionCommand(""+cnt+ " defending");
				unitButton.addActionListener(this);
				defenderPanel.add(unitButton);
			}
			else if ( u instanceof Cavalry)
			{
				JButton unitButton=new JButton("Cavalry");
				unitButton.setActionCommand(""+cnt+ " defending");
				unitButton.addActionListener(this);
				defenderPanel.add(unitButton);
			}
			cnt++;
			
			
		}
		
		manual=new JButton("Manual Attack");
		auto=new JButton("Automatic Attack");
		manual.addActionListener(this);
		auto.addActionListener(this);
		
		
		
		
		attackerLabel.setBounds(100,100,100,100);
		defenderLabel.setBounds(750,100,100,100);
		attackerPanel.setBounds(100,230,400,400);
		defenderPanel.setBounds(750,230,400,400);
		JPanel bottom=new JPanel();
		bottom.add(manual);
		bottom.add(auto);
		bottom.setBounds(450,700,500,100);
		
		
		
		Controller.gameModel.setListener(this);
		
		buttonsPanel.setVisible(false);
		this.add(defenderLabel);
		this.add(attackerLabel);
		this.add(attackerPanel);
		this.add(defenderPanel);
		this.add(bottom);
		this.add(new JLabel(""));
		
		this.revalidate();
	    this.repaint();
	    
		
		this.setVisible(true);
		
	
	
	
	}
	public void actionPerformed(ActionEvent e)
	{
		super.actionPerformed(e);
		String[] status=e.getActionCommand().split(" ");
		if (e.getSource()==auto)
		{
			
			try {
				Controller.gameModel.autoResolve(attacker,defender);
				if (attacker.getUnits().size()==0)
				{	
					JOptionPane.showMessageDialog(null,"Your army has lost the battle and died","Your army won",JOptionPane.INFORMATION_MESSAGE);
					
				}
				else if (defender.getUnits().size()==0)
				{
					JOptionPane.showMessageDialog(null," Your army has won and invaded the defending city","Your army lost",JOptionPane.INFORMATION_MESSAGE);
				}
				this.dispose();
				Controller.worldMapView=new WorldMapView();
			} catch (FriendlyFireException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Attacking an ally",JOptionPane.ERROR_MESSAGE);
			}
			
			
			
			
		}
		else if (status[1]!=null && status[1].equals("attacking"))
		{	
			attackerCommand=e.getActionCommand();
			
			
		}
		else if (status[1]!=null && status[1].equals("defending"))
		{	
			
			defenderCommand=e.getActionCommand();
			
		}
		else if (e.getSource()==manual)
		{	
			if (attackerCommand==null || defenderCommand==null)
				JOptionPane.showMessageDialog(null,"You must choose 2 units from different armies to attack ",
						"Missing units",JOptionPane.ERROR_MESSAGE);
			else
			{
				try {
					Controller.gameModel.manualAttack(attacker,defender,attackerCommand,defenderCommand );
					this.dispose();
					Controller.battleView=new BattleView(attacker,defendingCity);
				} catch (FriendlyFireException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Attacking an ally",JOptionPane.ERROR_MESSAGE);
				}
				if (attacker.getUnits().size()==0)
				{	
					JOptionPane.showMessageDialog(null,"Your army has lost the battle and died","Your army won",JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					Controller.worldMapView=new WorldMapView();
				}
				else if (defender.getUnits().size()==0)
				{
					JOptionPane.showMessageDialog(null," Your army has won and invaded the defending city","Your army lost",JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					Controller.worldMapView=new WorldMapView();
				}
				else {
					this.dispose();
					Controller.battleView= new BattleView(attacker, defendingCity);
				}
				
				
			}
		}
		
		
		
	}
	
	public void onAttack(Unit attacker, Unit defender,int a,int b) {
		String s1="";
		String s2="";
		if (defender.getCurrentSoldierCount()==0)
			s1=" and this defending unit has died";
		if (attacker.getCurrentSoldierCount()==0)
			s2=" and this unit has died";
		
		String result="Your attacking unit has killed "+a +"soldiers from the attacked defending unit"+s1+"\n";
		result+= "A random defending unit has killed "+b+" soldiers from a random unit in your army"+s2;
	JOptionPane.showMessageDialog(null,result,
					"Attack progress",JOptionPane.INFORMATION_MESSAGE);
		
		
	}
	
}
