package com.tutum.cpcard.command;

import java.util.ArrayList;
import java.util.Map;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.respond.RespondResult;
import com.tutum.cpcard.entities.Member;
import com.tutum.cpcard.entities.Project;

public class JoinMeetingCmd implements Command {

	public Map<String, String> para;
	public JoinMeetingCmd(Map<String, String> p){
		para = p;
	}
	
	@Override
	public ArrayList<RespondResult> ExecCommand() {
		// TODO Auto-generated method stub
        Project newProject  =  ProjectManager.getInstance().getProjectByName(para.get("projectName"));
        if(newProject == null){

            RespondResult respondResult = new RespondResult();
            respondResult.toOneReceptor(para.get("sessionId"));
            respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Join Meeting Error:Project is not existed."));
         	actionResults.add(respondResult);
        }
        else {
           
        	Member member = new Member(para.get("memberName"),false);
        	member.setSessionId(para.get("sessionId"));
        	if(!newProject.addMember(member))
        	{
   
                RespondResult respondResult = new RespondResult();
                respondResult.toOneReceptor(para.get("sessionId"));
                respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Join Meeting Error:Member has already existed."));
             	actionResults.add(respondResult);
        		
        	}
        	else {
   
                RespondResult respondResult = new RespondResult();
                respondResult.toSameProject(newProject);
                respondResult.setResultContent(jsonRespondContent.returnProjectInfo(newProject));
             	actionResults.add(respondResult);
			}
		}
        
		return actionResults;
	}

}
