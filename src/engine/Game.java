package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import exceptions.FriendlyFireException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class Game {
	private Player player;
	private ArrayList<City> availableCities;
	private ArrayList<Distance> distances;
	private final int maxTurnCount = 50;
	private int currentTurnCount;
	public AttackListener listener;
	public EndTurnListener endlistener;

	public EndTurnListener getEndlistener() {
		return endlistener;
	}

	public void setEndlistener(EndTurnListener endlistener) {
		this.endlistener = endlistener;
	}
	public AttackListener getListener() {
		return listener;
	}

	public void setListener(AttackListener listener) {
		this.listener = listener;
	}

	public Game(String playerName, String playerCity) throws IOException {

		player = new Player(playerName);
		player.setTreasury(5000);
		availableCities = new ArrayList<City>();
		distances = new ArrayList<Distance>();
		currentTurnCount = 1;
		loadCitiesAndDistances();
		for (City c : availableCities) {
			if (c.getName().equals(playerCity)) {
				player.getControlledCities().add(c);
			}
		}
		if (playerCity.toLowerCase().equals("cairo")) {
			loadArmy("Rome", "rome_army.csv");
			loadArmy("Sparta", "sparta_army.csv");
		} else if (playerCity.toLowerCase().equals("rome")) {
			loadArmy("Cairo", "cairo_army.csv");
			loadArmy("sparta", "sparta_army.csv");
		} else {
			loadArmy("Rome", "rome_army.csv");
			loadArmy("Cairo", "cairo_army.csv");
		}

	}

	private void loadCitiesAndDistances() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("distances.csv"));
		String currentLine = br.readLine();
		ArrayList<String> names = new ArrayList<String>();

		while (currentLine != null) {

			String[] content = currentLine.split(",");
			if (!names.contains(content[0])) {
				availableCities.add(new City(content[0]));
				names.add(content[0]);
			} else if (!names.contains(content[1])) {
				availableCities.add(new City(content[1]));
				names.add(content[1]);
			}
			distances.add(new Distance(content[0], content[1], Integer.parseInt(content[2])));
			currentLine = br.readLine();

		}
		br.close();
	}

	public void loadArmy(String cityName, String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));
		String currentLine = br.readLine();
		Army resultArmy = new Army(cityName);
		while (currentLine != null) {
			String[] content = currentLine.split(",");
			String unitType = content[0].toLowerCase();
			int unitLevel = Integer.parseInt(content[1]);
			Unit u = null;
			if (unitType.equals("archer")) {

				if (unitLevel == 1)
					u = (new Archer(1, 60, 0.4, 0.5, 0.6));

				else if (unitLevel == 2)
					u = (new Archer(2, 60, 0.4, 0.5, 0.6));
				else
					u = (new Archer(3, 70, 0.5, 0.6, 0.7));
			} else if (unitType.equals("infantry")) {
				if (unitLevel == 1)
					u = (new Infantry(1, 50, 0.5, 0.6, 0.7));

				else if (unitLevel == 2)
					u = (new Infantry(2, 50, 0.5, 0.6, 0.7));
				else
					u = (new Infantry(3, 60, 0.6, 0.7, 0.8));
			} else if (unitType.equals("cavalry")) {
				if (unitLevel == 1)
					u = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

				else if (unitLevel == 2)
					u = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
				else
					u = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
			}
			resultArmy.getUnits().add(u);
			u.setParentArmy(resultArmy);
			currentLine = br.readLine();
		}
		for (City c : availableCities) {
			if (c.getName().toLowerCase().equals(cityName.toLowerCase()))
				c.setDefendingArmy(resultArmy);
		}
		br.close();
	}



	

	public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
		int turn = 1;
		while (attacker.getUnits().size() != 0 && defender.getUnits().size() != 0) {
			Unit unit1 = attacker.getUnits().get((int) (Math.random() * attacker.getUnits().size()));
			Unit unit2 = defender.getUnits().get((int) (Math.random() * defender.getUnits().size()));
			int oldCount1=unit1.getCurrentSoldierCount();
			int oldCount2=unit2.getCurrentSoldierCount();
			if (turn == 1)
			{
				unit1.attack(unit2);
			
			}
			else
			{
				unit2.attack(unit1);

			}
			turn = turn == 1 ? 0 : 1;

		}
		if (attacker.getUnits().size() != 0)
			occupy(attacker, defender.getCurrentLocation());
		
		else if (attacker.getUnits().size()==0)
		{	
			
			player.getControlledArmies().remove(attacker);
			City c=null;
			for (City city: availableCities)
			{
				if (city.getName().equalsIgnoreCase(defender.getCurrentLocation()))
					c=city;
				
			}
			if (c==null)
				return;
			c.setTurnsUnderSiege(-1);
			c.setUnderSiege(false);
			
			return;
		}

	}
	public void manualAttack(Army attackingArmy, Army defendingArmy,
			String attackerCommand, String defenderCommand) throws FriendlyFireException
	{	
		String [] y1=attackerCommand.split(" ");
		String [] y2=defenderCommand.split(" ");
		int attackerIndex=Integer.parseInt(y1[0]);
		int defenderIndex=Integer.parseInt(y2[0]);
		if (y1[1].equalsIgnoreCase(y2[1]))
			throw new FriendlyFireException("Cannot attack a friendly unit");
		Unit attacker=attackingArmy.getUnits().get(attackerIndex);
		Unit defender=defendingArmy.getUnits().get(defenderIndex);
		
		int oldCount2=defender.getCurrentSoldierCount();
		
		attacker.attack(defender);
		
		
		Unit unit1 = attackingArmy.getUnits().get((int) (Math.random() * attackingArmy.getUnits().size()));
		Unit unit2 = defendingArmy.getUnits().get((int) (Math.random() * defendingArmy.getUnits().size()));
		int oldCount1=unit1.getCurrentSoldierCount();
		unit2.attack(unit1);
		
		if (listener!=null)
			listener.onAttack(unit1, unit2, oldCount2-defender.getCurrentSoldierCount(),oldCount1-unit1.getCurrentSoldierCount());
		if (defendingArmy.getUnits().size()==0)
		{
			occupy(attackingArmy, defendingArmy.getCurrentLocation());
			//System.out.println("1");
		}
		
		else if (attackingArmy.getUnits().size()==0)
		{	
			
			player.getControlledArmies().remove(attackingArmy);
			City c=null;
			for (City city: availableCities)
			{
				if (city.getName().equalsIgnoreCase(defendingArmy.getCurrentLocation()))
					c=city;
				
			}
			if (c==null)
				return;
			c.setTurnsUnderSiege(-1);
			c.setUnderSiege(false);
			
			return;
		}

		
		
		
	}

	public void occupy(Army a, String cityName) {
		for (City c : availableCities) {
			if (c.getName().equals(cityName)) {
				player.getControlledCities().add(c);
				player.getControlledArmies().remove(a);
				c.setDefendingArmy(a);
				c.setUnderSiege(false);
				c.setTurnsUnderSiege(-1);
				a.setCurrentStatus(Status.IDLE);
			}
		}
	}

	public boolean isGameOver() {
		return player.getControlledCities().size() == availableCities.size() || currentTurnCount > maxTurnCount;
	}

	public ArrayList<City> getAvailableCities() {
		return availableCities;
	}

	public ArrayList<Distance> getDistances() {
		return distances;
	}

	public int getMaxTurnCount() {
		return maxTurnCount;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getCurrentTurnCount() {
		return currentTurnCount;
	}

	public void setCurrentTurnCount(int currentTurnCount) {
		this.currentTurnCount = currentTurnCount;
	}
	public void endTurn() {
		currentTurnCount++;
		double totalUpkeep = 0;
		
		double totalHarvestTreasury=0;
		double totalHarvestFood=0;
		double totalFoodConsumed=0;
		Boolean isEnough=true;
		for (City c : player.getControlledCities()) {
			for (MilitaryBuilding b : c.getMilitaryBuildings()) {

				b.setCoolDown(false);
				b.setCurrentRecruit(0);

			}
			for (EconomicBuilding b : c.getEconomicalBuildings()) {

				b.setCoolDown(false);
				if (b instanceof Market)
				{
					player.setTreasury(player.getTreasury() + b.harvest());
					totalHarvestTreasury+=b.harvest();
				}
				else if (b instanceof Farm)
				{
					player.setFood(player.getFood() + b.harvest());
					totalHarvestFood+=b.harvest();
				}
			}
			totalUpkeep+=c.getDefendingArmy().foodNeeded();
			
		}
		
		for (Army a : player.getControlledArmies()) {
			if (!a.getTarget() .equals("") && a.getCurrentStatus() == Status.IDLE) {
				a.setCurrentStatus(Status.MARCHING);
				a.setCurrentLocation("onRoad");
//				System.out.println(a.getCurrentStatus());
//				System.out.println(a.getTarget());
			}
			//System.out.println(a.getDistancetoTarget() +" "+a.getTarget());
			if(a.getDistancetoTarget()>0 &&!a.getTarget().equals(""))
			{
				a.setDistancetoTarget(a.getDistancetoTarget() - 1);
				if (a.getDistancetoTarget() == 0) {
					a.setCurrentLocation(a.getTarget());
					endlistener.onEndTurn2(a);
					a.setTarget("");
					a.setCurrentStatus(Status.IDLE);
				
				}
			}
			totalUpkeep +=  a.foodNeeded();

		}//
		
		totalFoodConsumed=totalUpkeep;
		if (totalUpkeep <= player.getFood())
			player.setFood(player.getFood() - totalUpkeep);
		else {
			player.setFood(0);
			isEnough=false;
			for (Army a : player.getControlledArmies()) {

				for (Unit u : a.getUnits()) {
					u.setCurrentSoldierCount(u.getCurrentSoldierCount() - (int) (u.getCurrentSoldierCount() * 0.1));
				}
			}
		}
		

		
		
			endlistener.onEndTurn1(isEnough,totalHarvestTreasury,totalHarvestFood,totalFoodConsumed);
		
		boolean isTurns3=false;

		for (City c : availableCities) {
			if (c.isUnderSiege()) {
				
				if(c.getTurnsUnderSiege() < 3){
				c.setTurnsUnderSiege(c.getTurnsUnderSiege() + 1);
				endlistener.onEndTurn3(isTurns3,c);
				
				}
				else{
					isTurns3=true;
					endlistener.onEndTurn3(isTurns3,c);
					c.setUnderSiege(false);
					return;
				}
				for (Unit u : c.getDefendingArmy().getUnits()) {
					u.setCurrentSoldierCount(u.getCurrentSoldierCount() - (int) (u.getCurrentSoldierCount() * 0.1));
				}
			
				
			}
		}

	}
	
	public void targetCity(Army army, String targetName) {

		String from = army.getCurrentLocation();
		if (army.getCurrentLocation().equals("onRoad"))
			from = army.getTarget();
		for (Distance d : distances) {
			if ((d.getFrom().equals(from) || d.getFrom().equals(targetName))
					&& (d.getTo().equals(from) || d.getTo().equals(targetName))) {
				army.setTarget(targetName);
				int distance = d.getDistance();
				if (army.getCurrentLocation().equals("onRoad"))
					distance += army.getDistancetoTarget();
				army.setDistancetoTarget(distance);
			}
		}

	}
	
	
	

}
