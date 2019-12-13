package com.tutum.cpcard.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.websocket.Session;


/**
 * Created by Mars on 2015/4/21.
 */
public class Project {

    private String name;
    private HashMap<Integer, Backlog> backlogs = new HashMap<Integer, Backlog>();
    private ArrayList<Member> members = new ArrayList<Member>();
    private Integer activeBacklogInteger = 1;

    
    public String getName()
    {
        return  name;
    }

    public void setName(String n)
    {
        this.name = n;
    }
    
    public Integer getActiveBacklogId()
    {
    	return activeBacklogInteger;
    }
    
    public void setActiveBacklogId(Integer backlogId)
    {
    	activeBacklogInteger = backlogId;
    }
    
    public void addBacklog(Integer backlogId,Backlog b)
    {
    	b.setBelongToProject(this);
    	backlogs.put(backlogId, b);
    }
    
    public boolean hasBacklog(Integer backlogId)
    {
    	if (backlogs.containsKey(backlogId))
    	{
    		return true;
    	}
    	return false;
    }
    
    public Backlog goToBacklog(Integer backlogId)
    {
    	Backlog b = null;
    	if(this.hasBacklog(backlogId))
    	{
    		b = (Backlog)backlogs.get(backlogId);
    		//b.clearVotes();
    	}
    	else
    	{
    		b = new Backlog();
    		this.addBacklog(backlogId, b);
    	}
    	return b;
    }
    
    public boolean addMember(Member m)
    {
    	Iterator<Member> iterator = (Iterator<Member>) members.iterator();
    	while(iterator.hasNext())
    	{
    		Member memberInProject = (Member)iterator.next();
    		if(memberInProject.getName().equals(m.getName()))
    		{
    			return false;
    		}
    	}
		members.add(m);
		
		java.util.Iterator it = backlogs.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
			Backlog backlog = (Backlog)entry.getValue();
			backlog.addVote(m, 0);
		}

		return true;
    }
    
    public boolean removeMember(String name)
    {
    	Iterator<Member> iterator = (Iterator<Member>) members.iterator();
    	while(iterator.hasNext())
    	{
    		Member memberInProject = (Member)iterator.next();
    		if(memberInProject.getName().equals(name))
    		{
				getBacklogById(activeBacklogInteger).removeVote(memberInProject);
				members.remove(memberInProject);
    			return true;
    		}
    	}
    	return false;

    }
    
    public Project(String name)
    {
    	this.name = name;
    }

	public ArrayList<Member> getAllMembers() {
		return members;
	}

	public Member getMemberByName(String name)
	{
		Member member = null;
		Iterator<Member> iterator = (Iterator<Member>) members.iterator();
		while(iterator.hasNext())
		{
			Member memberInProject = (Member)iterator.next();
			if(memberInProject.getName().equals(name))
			{
				member = memberInProject;
			}
		}
		return member;
	}
	
	public HashMap<Integer, Backlog>  getAllBacklogs()
	{
		return backlogs;
	}
	
	public Backlog getBacklogById(Integer backlogId)
	{
		if (hasBacklog(backlogId)) {
			return backlogs.get(backlogId);
		}
		else {
			return goToBacklog(backlogId);
		}
	}

}
