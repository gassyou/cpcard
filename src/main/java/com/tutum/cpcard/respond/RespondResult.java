package com.tutum.cpcard.respond;

import java.util.ArrayList;

import com.tutum.cpcard.SessionManager;
import com.tutum.cpcard.entities.Member;
import com.tutum.cpcard.entities.Project;

public class RespondResult {
	
	private ArrayList<String> receptor = new ArrayList<String>();
	private String content;
	
	
	public void toAll()
	{
		receptor = SessionManager.getInstance().getAllSessionsID();
	}
	
	public void toSameProject(Project p)
	{
		for (Member m : p.getAllMembers()) {
			receptor.add(m.getSessionId());
		}
	}
	
	public void toOneReceptor(String sessionId)
	{
		receptor.add(sessionId);
	}
	
	public ArrayList<String> getReceptor()
	{
		return receptor;
	}
	
	public String getResultContent()
	{
		return content;
	}
	
	public void setResultContent(String result)
	{
		content = result;
	}

}