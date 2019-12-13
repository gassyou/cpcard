package com.tutum.cpcard.entities;

/**
 * Created by Mars on 2015/4/21.
 */
public class Member {

    private String name;
    private boolean isHost;
    private String sessionId;

    public String getName()
    {
        return  name;
    }

    public void setName(String n)
    {
        this.name = n;
    }

    public boolean getIsHost()
    {
        return  isHost;
    }

    public void  setIsHost(boolean flag)
    {
        this.isHost = flag;
    }

    public Member(String name,boolean flag)
    {
        this.name = name;
        this.isHost = flag;
    }
    
    public void setSessionId(String id)
    {
    	this.sessionId = id;
    }
    
    public String getSessionId() {
		return sessionId;
	}
}
