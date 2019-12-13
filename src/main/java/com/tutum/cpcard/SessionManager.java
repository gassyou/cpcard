package com.tutum.cpcard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

public class SessionManager {
	
	private HashMap<String,Session> sessions = new HashMap<String,Session>();
	private static SessionManager instance = new SessionManager();
    private SessionManager(){}

    public static SessionManager getInstance()
    {

        return instance;
    }
    
    public ArrayList<String> getAllSessionsID()
    {
    	//return sessions.keySet()  toArray();
    	ArrayList<String> sessionIds = new ArrayList<String>();
    	sessionIds.addAll(sessions.keySet());
    	return sessionIds;
    	
    	
    }
    
    public void addSession(Session s)
    {
    	if(!isSessionExist(s.getId()))
    	{
    		sessions.put(s.getId(), s);
    	}
    	
    }
    
    public void closeSession(Session s) throws IOException
    {
    	s.close();
    }
    
    public void removeSession(String sessionId)
    {
    	sessions.remove(sessionId);
    }
    
    
    public boolean isSessionExist(String sessionId)
    {

    	if (sessions.containsKey(sessionId))
    	{
    		return true;
    	}
    	else {
			return false;
		}
    }
   
    public Session getSessionById(String sessionId)
    {
    	if(isSessionExist(sessionId))
    	{
    		return sessions.get(sessionId);
    	}
    	else
    	{
    		return null;
    	}
    	
    }
}
