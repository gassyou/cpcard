package com.tutum.cpcard.command;

import java.util.ArrayList;
import java.util.Map;

import com.tutum.cpcard.ProjectManager;
import com.tutum.cpcard.entities.Project;
import com.tutum.cpcard.respond.RespondResult;

public class SelectProjectCmd implements Command {
	
	public Map<String, String> para;
	public SelectProjectCmd(Map<String, String> p){
		para = p;
	}

	@Override
	public ArrayList<RespondResult> ExecCommand() {
		// TODO Auto-generated method stub
		
		 Project newProject  =  ProjectManager.getInstance().getProjectByName(para.get("projectName"));
		 
	     if(newProject == null){

	    	 RespondResult respondResult = new RespondResult();
	    	 respondResult.toOneReceptor(para.get("sessionId"));
	    	 respondResult.setResultContent(jsonRespondContent.returnErrorMsg("Select Project Error:Project is not existed."));
	    	 actionResults.add(respondResult);
	     }
	     else
	     {
	    	 RespondResult respondResult = new RespondResult();
	    	 respondResult.toOneReceptor(para.get("sessionId"));
	    	 respondResult.setResultContent(jsonRespondContent.returnProjectInfo(newProject));
	    	 actionResults.add(respondResult);
	     }
    	
    	return actionResults;
    	
	}

}
