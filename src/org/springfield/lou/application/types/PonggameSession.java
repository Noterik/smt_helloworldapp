package org.springfield.lou.application.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springfield.lou.application.Html5Application;
import org.springfield.lou.screen.Screen;
import org.springfield.lou.session.ISession;

public class PonggameSession implements ISession{

	private List<Screen> mainScreens;
	private HashMap<String, Screen> playerScreens;
	private Player playerLeft;
	private Player playerRight;
	private Ball Ball;
	private Html5Application app;

 	public PonggameSession(Html5Application app) {
		this.app = app;
	}
	
 	private void init(){
		mainScreens = new ArrayList<Screen>();
		playerScreens = new HashMap<String, Screen>();
	}
 	
 	public void addPlayerScreen(Screen s){
		System.out.println("-------------------------------------");		
		System.out.println("PonggameApplication.addPlayerScreen()");
		System.out.println("-------------------------------------");
		String role = s.getParameter("role");

		if(mainScreens == null){
			this.init();
		}
		
		if(role == null || role.equals("player")){
			System.out.println("----------");
			System.out.println("PlayerScreenSize: " + this.playerScreens.size());
			System.out.println("----------");
			if(this.playerScreens.size() < 2){
				initPlayerScreen(s);
				System.out.println("----------------");
				System.out.println("initPlayerScreen");
				System.out.println("----------------");
			}else{
				sendError(s);
				System.out.println("Error");
			}
		}else{
			initMainScreen(s);
			System.out.println("--------------");
			System.out.println("initMainScreen");
			System.out.println("--------------");
		}
	}
 	
 	private void initPlayerScreen(Screen s){
		System.out.println("PonggameApplication.initPlaterScreen()");
		s.setRole("player");
		if(this.playerScreens.size() == 0){
			this.playerScreens.put("playerL", s);
		}else {
			this.playerScreens.put("playerR", s);
		}
		this.resetPlayerScreen(s);
	}
 	
 	private void initMainScreen(Screen s){
		System.out.println("PonggameApplication.mainScreen()");
		s.setRole("main");

		this.mainScreens.add(s);
		this.resetMainScreen(s);
	}
 	
	private void resetMainScreen(Screen s){
		app.loadStyleSheet(s, "bootstrap");
		app.loadStyleSheet(s, "generic");
		app.loadContent(s, "mainlayout", "mainlayout");
	}
	
	private void resetPlayerScreen(Screen s) {
		app.loadStyleSheet(s, "bootstrap");
		app.loadStyleSheet(s, "generic");
		app.loadContent(s, "player", "player");
	}
 	
 	private void sendError(Screen s){
		System.out.println("PonggameApplication.sendError()");
	}
 	
	public void getControllerPosition(Screen s, String value)
	{
		System.out.println("---------------------------" + value + "------------------------------");
	}
	
	@Override
	public Html5Application getApp() {
		return this.app;
	}

	@Override
	public List<Screen> getScreens() {
		// TODO Auto-generated method stub
		return (List<Screen>) playerScreens;
	}

	@Override
	public List<Screen> getScreensByRole(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasScreen(Screen s) {
		if(playerScreens.containsValue(s)){
			return true;
		}else if(mainScreens.contains(s)){
			return true;
		}
		return false;
	}

}
