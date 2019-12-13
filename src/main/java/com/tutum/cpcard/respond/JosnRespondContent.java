package com.tutum.cpcard.respond;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.entities.Backlog;
import com.tutum.cpcard.entities.Member;
import com.tutum.cpcard.entities.Project;
import com.tutum.cpcard.entities.Vote;

public class JosnRespondContent {

	ProjectManager pm = ProjectManager.getInstance();
	
	public String returnErrorMsg(String msg)
	{
		String jsonMessage = "{\"projects\":[],\"members\":[],\"backlog\":{},\"msg\":\"\"}";
		JSONObject jsonProjectObject = new JSONObject(jsonMessage);
		try{
			jsonProjectObject.put("msg", msg);
		}catch (Exception e) {
			// TODO: handle exception

		}
		return jsonProjectObject.toString();

	}
	
	public String returnProjectInfo(Project p)
	{
		String jsonMessage = "{\"projects\":[],\"members\":[],\"backlog\":{},\"msg\":\"\"}";
		JSONObject jsonProjectObject = new JSONObject(jsonMessage); 
		try {
			
			jsonProjectObject.put("projects", getAllProjectJson());
			
			if(p!=null)
			{
				jsonProjectObject.put("members", getProjectMembersJson(p));
				jsonProjectObject.put("backlog", getBacklogJson(p));
			}
		}catch (Exception e) {
			// TODO: handle exception

		}
		return jsonProjectObject.toString();
	}
	
	private JSONArray getAllProjectJson()
	{
		JSONArray jsonProjectArray = new JSONArray();  
		for(Project p :pm.getAllProjects())
		{
			jsonProjectArray.put(p.getName());
		}
		return jsonProjectArray;
	}
	
	private  JSONArray getProjectMembersJson(Project p)
	{
		JSONArray jsonMembersArray = new JSONArray();  
		if(pm.isProjectExist(p.getName()))
		{
			for(Member m:p.getAllMembers())
			{
				jsonMembersArray.put(m.getName());
			}
		}
		return jsonMembersArray;
	}

	private  JSONObject getBacklogJson(Project p)
	{
		JSONObject jsonBacklog= new JSONObject(); 
		if(pm.isProjectExist(p.getName()))
		{
			Backlog backlog = p.getBacklogById(p.getActiveBacklogId());
			jsonBacklog.put("id",p.getActiveBacklogId());
			jsonBacklog.put("votes", getVotesJson(backlog));
	    	if(backlog.isFinish())
	    	{
	    		jsonBacklog.put("isFinish", "Yes");
	    	}
	    	else {
	    		jsonBacklog.put("isFinish", "No");
			}
		}
    	return jsonBacklog;
	}
	
	
	private JSONArray getVotesJson (Backlog backlog)
	{
		JSONArray jsonVotesArray = new JSONArray(); 
    	for(Vote vote: backlog.getAllVotes())
    	{
    		JSONObject jsonVote= new JSONObject(); 
    		jsonVote.put("name", vote.getActor().getName());
    		jsonVote.put("cp", vote.getWorth());
    		jsonVotesArray.put(jsonVote);
    	}
    	return jsonVotesArray;
	}
	
}
