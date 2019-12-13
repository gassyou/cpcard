package com.tutum.cpcard.entities;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Mars on 2015/4/21.
 */
public class Backlog {
	
	private Project belongToProject;
	private ArrayList<Vote> votes = new ArrayList<Vote>();
	
	public void setBelongToProject(Project p)
	{
		this.belongToProject = p;
		
		Iterator<Member> iterator = (Iterator<Member>) p.getAllMembers().iterator();
		while (iterator.hasNext()) {
			Member member = (Member)iterator.next();
			votes.add(new Vote(member, 0));
		}
	}
	
	public Project getBelongToProject()
	{
		return this.belongToProject;
	}
	
	public void addVote(Member m,Integer voteValue)
	{
		Iterator<Vote> iterator = (Iterator<Vote>) votes.iterator();
    	while(iterator.hasNext())
    	{
    		Vote vote = (Vote)iterator.next();
    		if(vote.getActor().getName().equals(m.getName()))
    		{
    			//votes.remove(vote);
    			vote.setWorth(voteValue);
    			return;
    		}
    	}
    	votes.add(new Vote(m, voteValue));
	}
	
    public void addVote(Vote v)
    {
    	Iterator<Vote> iterator = (Iterator<Vote>) votes.iterator();
    	while(iterator.hasNext())
    	{
    		Vote vote = (Vote)iterator.next();
    		
    		//System.out.println(vote.getActor().getName());
    		System.out.println(v.getActor().getName());
    		
    		if(vote.getActor().getName().equals(v.getActor().getName()))
    		{
    			//votes.remove(vote);
    			vote.setWorth(v.getWorth());
    			return;
    		}
    	}
    	votes.add(v);
    }
	
	public boolean isFinish()
	{
		Iterator<Vote> iterator = (Iterator<Vote>) votes.iterator();
    	while(iterator.hasNext())
    	{
    		Vote vote = (Vote)iterator.next();
    		if(vote.getWorth() != 0){
    			continue;
    		}
    		else {
				return false;
			}
    	}
    	return true;
	}
	
	public Integer getVoteValueByMember(Member m)
	{
		Integer voteValue = 0;
		Iterator<Vote> iterator = (Iterator<Vote>) votes.iterator();
    	while(iterator.hasNext())
    	{
    		Vote vote = (Vote)iterator.next();
    		if(vote.getActor().getName().equals(m.getName()))
    		{
    			voteValue = vote.getWorth();
    			break;
    		}
    	}
    	return voteValue;
	}
	
	public boolean hasVote(Member m)
	{
    	Iterator<Vote> iterator = (Iterator<Vote>) votes.iterator();
    	while(iterator.hasNext())
    	{
    		Vote vote = (Vote)iterator.next();
    		if(vote.getActor().getName().equals(m.getName()))
    		{
    			return true;
    		}
    	}
    	return false;
	}
	
	public ArrayList<Vote> getAllVotes()
	{
		return votes;
	}
	
	public void clearVotes()
	{
		votes.clear();
	}
	
	public void removeVote(Member m)
	{
		Iterator<Vote> iterator = (Iterator<Vote>) votes.iterator();
    	while(iterator.hasNext())
    	{
    		Vote vote = (Vote)iterator.next();
    		if(vote.getActor().getName().equals(m.getName()))
    		{
    			votes.remove(vote);
    			return;
    		}
    	}
	}
	
}
