package engine;
import units.*;
import engine.*;
public interface EndTurnListener {
	public void onEndTurn1(Boolean f, double a, double b, double c); //handling buildings harvest,gold,food
	public void onEndTurn2(Army a); //when army reaches city
	public void onEndTurn3(Boolean f, City c); // city being besieged
}
