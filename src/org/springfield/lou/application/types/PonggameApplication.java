/* 
* HelloworldApplication.java
* 
* Copyright (c) 2012 Noterik B.V.
* 
* This file is part of Lou, related to the Noterik Springfield project.
* It was created as a example of how to use the multiscreen toolkit
*
* Helloworld app is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Helloworld app is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Helloworld app.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.springfield.lou.application.types;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springfield.lou.application.Html5Application;
import org.springfield.lou.homer.LazyHomer;
import org.springfield.lou.screen.Screen;
import org.springfield.lou.session.ISession;

public class PonggameApplication extends Html5Application{
	
	private boolean wantedna = false;
	private PonggameSession session;
	
	/*
	 */
	public PonggameApplication(String id) {
		super(id); 
		session = new PonggameSession(this);
	}
	
	public void init(Screen s){
		session.addPlayerScreen(s);
		
	}
	
	public String getFavicon() {
        return "/eddie/apps/euscreenxlelements/img/favicon.png";
    }
	
	public String getMetaHeaders(HttpServletRequest request) {
		
		return ""; // default is empty;
	}
	
	public void onNewUser(Screen s,String name) {
		super.onNewUser(s, name);
	}
	
	public void nextPage(Screen s){
	}
	
	public void putOnScreen(Screen s, String from, String msg) {
		ISession session = this.session;
        int pos = msg.indexOf("(");
        if (pos!=-1) {
            String command = msg.substring(0,pos);
            String content = msg.substring(pos+1,msg.length()-1);
            JSONObject params = (JSONObject) JSONValue.parse(content);
            try {
            	System.out.println("PARAMS: " + params);
            	Method method;
            	if(params != null){
            		method = session.getClass().getMethod(command, Screen.class, JSONObject.class);
            		method.invoke(session, s, params);
            	}else{
            		method = session.getClass().getMethod(command, Screen.class);
            		method.invoke(session, s);
            	}
            	
        	} catch (SecurityException e) {
        		e.printStackTrace();
        	} catch (NoSuchMethodException e) {
        		e.printStackTrace();
        	} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
 	}
	
	private boolean inDevelMode() {
    	return LazyHomer.inDeveloperMode();
    }
	
	private String setEdnaMapping(String screenshot) {
		if(screenshot != null){
			if (!wantedna) {
				screenshot = screenshot.replace("edna/", "");
			} else {
				int pos = screenshot.indexOf("edna/");
				if 	(pos!=-1) {
					screenshot = "http://images.euscreenxl.eu/"+screenshot.substring(pos+5);
				}
			}
		}
		return screenshot;
	}
}
