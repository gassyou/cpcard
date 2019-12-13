package com.tutum.cpcard.command;

import java.util.ArrayList;
import java.util.Map;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.respond.RespondResult;
import com.tutum.cpcard.entities.Backlog;
import com.tutum.cpcard.entities.Member;
import com.tutum.cpcard.entities.Project;
import com.tutum.cpcard.entities.Vote;

public class SubmitCPCmd implements Command {

	public Map<String, String> para;
	public SubmitCPCmd(Map<String, String> p){
		para = p;
	}
	
	@Override
	public ArrayList<RespondResult> ExecCommand() {
		// TODO Auto-generated method stub
		
        Project project  =  ProjectManager.getInstance().getProjectByName(para.get("projectName"));
   
        
        if(project == null)
        {

	    	 RespondResult respondResult = new RespondResult();
	    	 respondResult.toOneReceptor(para.get("sessionId"));
	    	 respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Submit CP Error: Project is not fond.."));
	    	 actionResults.add(respondResult);
        	
        	return actionResults;
        }
        

    	Member member = project.getMemberByName(para.get("memberName"));
    	
        if(member == null)
        {
	    	 RespondResult respondResult = new RespondResult();
	    	 respondResult.toOneReceptor(para.get("sessionId"));
	    	 respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Submit CP Error: You are not a member in this project.."));
	    	 actionResults.add(respondResult);
       	
	    	 return actionResults;
        }
    	
    	Backlog backlog = project.goToBacklog(Integer.parseInt(para.get("backlogId")));
    	
    	Vote v = new Vote(member,Integer.parseInt(para.get("worth")));
    	
    	if(backlog.isFinish())
    	{
	    	 RespondResult respondResult = new RespondResult();
	    	 respondResult.toOneReceptor(para.get("sessionId"));
	    	 respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Submit CP Error:You had a vote already.."));
	    	 actionResults.add(respondResult);
    	}
    	else
    	{
    		backlog.addVote(v);
         	RespondResult respondResult = new RespondResult();
	    	respondResult.toSameProject(project);
	    	respondResult.setResultContent(jsonRespondContent.returnProjectInfo(project));
	    	actionResults.add(respondResult);
 
    	}
    	
		return actionResults;
	}

}
