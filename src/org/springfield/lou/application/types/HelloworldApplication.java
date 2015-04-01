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
import org.springfield.lou.application.*;
import org.springfield.lou.screen.*;

public class HelloworldApplication extends Html5Application{
	
 	public HelloworldApplication(String id) {
		super(id); 
	}
	
    public void onNewScreen(Screen s) {
        loadStyleSheet(s, "generic");
        loadContent(s, "titlepart");
        String role = s.getParameter("role");
        if(role.equalsIgnoreCase("teacher")) {
        	loadContent(s, "mainscreen");
        	s.setRole("teacher");
        } else {
        	s.setRole("student");
        	s.setContent("titlepart", "<h1>Wait to see a picture.</h1>");
        }
    }
    
    public void openImage(Screen s, String imgId) {
    	String body = "<div id=\"img1\">" + "imagee1</div>";
    	setContentAllScreensWithRole("student","defaultoutput","img" + body);
    }
}
