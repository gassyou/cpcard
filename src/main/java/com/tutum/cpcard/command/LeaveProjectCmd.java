package com.tutum.cpcard.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.SessionManager;
import com.tutum.cpcard.respond.RespondResult;
import com.tutum.cpcard.entities.Member;
import com.tutum.cpcard.entities.Project;

public class LeaveProjectCmd implements Command {

	public Map<String, String> para;
	public LeaveProjectCmd(Map<String, String> p){
		para = p;
	}
	
	@Override
	public ArrayList<RespondResult> ExecCommand() throws IOException {
		// TODO Auto-generated method stub
		
		
		Project project  =  ProjectManager.getInstance().getProjectByName(para.get("projectName"));
		
        if(project == null)
        {

	    	 RespondResult respondResult = new RespondResult();
	    	 respondResult.toOneReceptor(para.get("sessionId"));
	    	 respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Leaving Meeting Error: Project is not fond.."));
	    	 actionResults.add(respondResult);
        	
        	return actionResults;
        }
		
    	Member member = project.getMemberByName(para.get("memberName"));
    	
    	//System.out.print(para.get("memberName"));
    	
        if(member == null)
        {
        	RespondResult respondResult = new RespondResult();
	    	respondResult.toOneReceptor(para.get("sessionId"));
	    	respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Leaving Meeting Error: You are not a member in this project.."));
	    	actionResults.add(respondResult);
       	
	    	return actionResults;
        }
        
        if(member.getIsHost())
        {

//        	RespondResult respondResult = new RespondResult();
//        	respondResult.toAll();
//          respondResult.setResultContent(jsonRespondContent.returnProjectInfo(null));
//          actionResults.add(respondResult);
            
        	RespondResult respondResult1 = new RespondResult();
        	respondResult1.toSameProject(project);
            respondResult1.setResultContent(jsonRespondContent.returnErrorMsg("Termiteding Meeting:The Host has termited the meeting."));
            actionResults.add(respondResult1);
            
            ProjectManager.getInstance().removeProject(project);
        }
        else
        {
        	//System.out.print(member.getName());
            project.removeMember(member.getName());
            
        	RespondResult respondResult1 = new RespondResult();
        	respondResult1.toSameProject(project);
            respondResult1.setResultContent(jsonRespondContent.returnProjectInfo(project));
            
            //System.out.print(jsonRespondContent.returnProjectInfo(project));
            actionResults.add(respondResult1);
        }
    	return actionResults;
    	
	}

}
