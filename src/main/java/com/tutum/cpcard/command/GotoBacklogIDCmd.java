package com.tutum.cpcard.command;

import java.util.ArrayList;
import java.util.Map;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.respond.JosnRespondContent;
import com.tutum.cpcard.respond.RespondResult;
import com.tutum.cpcard.entities.Project;

public class GotoBacklogIDCmd implements Command {
	
	public Map<String, String> para;
	public GotoBacklogIDCmd(Map<String, String> p){
		para = p;
	}
	@Override
	public ArrayList<RespondResult> ExecCommand() {
		// TODO Auto-generated method stub
		
    	Project project = ProjectManager.getInstance().getProjectByName(para.get("projectName"));
    	project.setActiveBacklogId(Integer.parseInt(para.get("backlogId")));
        project.goToBacklog(Integer.parseInt(para.get("backlogId")));
    	
        
        RespondResult respondResult = new RespondResult();
        respondResult.toSameProject(project);
        respondResult.setResultContent(jsonRespondContent.returnProjectInfo(project));
     	actionResults.add(respondResult);
     	
    	return actionResults;
		
	}

}
