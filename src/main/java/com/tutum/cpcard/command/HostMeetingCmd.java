package com.tutum.cpcard.command;

import java.util.ArrayList;
import java.util.Map;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.respond.RespondResult;
import com.tutum.cpcard.entities.Member;
import com.tutum.cpcard.entities.Project;

public class HostMeetingCmd implements Command {
	
	public Map<String, String> para;
	public HostMeetingCmd(Map<String, String> p){
		para = p;
	}


	@Override
	public ArrayList<RespondResult> ExecCommand() {
		// TODO Auto-generated method stub
		
        Project newProject  =  ProjectManager.getInstance().addProject(para.get("projectName"));
        if(newProject == null){
        	
            RespondResult respondResult = new RespondResult();
            respondResult.toOneReceptor(para.get("sessionId"));
            respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Host Meeting Error:Project has been existed."));
         	actionResults.add(respondResult);
         	
        }
        else {
        	
        	Member member = new Member(para.get("memberName"),true);
        	member.setSessionId(para.get("sessionId"));
        	newProject.addMember(member);
        	
            RespondResult respondResult = new RespondResult();
            respondResult.toAll();
            respondResult.setResultContent(jsonRespondContent.returnProjectInfo(null));
         	actionResults.add(respondResult);
         	
            RespondResult respondResult2 = new RespondResult();
            respondResult2.toSameProject(newProject);
            respondResult2.setResultContent(jsonRespondContent.returnProjectInfo(newProject));
         	actionResults.add(respondResult2);
		}
		return actionResults;
	}

}
