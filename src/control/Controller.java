package control;
import view.*;

import java.awt.event.ActionEvent;
import java.io.IOException;

import engine.*;
import units.*;
public class Controller {
	public static Game gameModel;
	Intro intro;
	public static WorldMapView worldMapView;
	public static BattleView battleView;
	public static CityView cityView;
	public static RelocateUnitView relocateView;
	
	public Controller()
	{
		intro=new Intro();
		
	}
	public static void main(String[] args) {
		
		new Controller();

		
	
		
	}
	
}
