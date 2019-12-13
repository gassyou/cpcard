package com.tutum.cpcard.command;

import java.io.IOException;
import java.util.ArrayList;

import com.tutum.cpcard.SessionManager;
import com.tutum.cpcard.respond.RespondResult;

public class CloseSessionCmd implements Command {
	
	public LeaveProjectCmd lpCmd;
	public CloseSessionCmd(LeaveProjectCmd cmd){
		lpCmd = cmd;
	}

	@Override
	public ArrayList<RespondResult> ExecCommand() throws IOException {
		// TODO Auto-generated method stub
		
		ArrayList<RespondResult> result = (ArrayList<RespondResult>) lpCmd.ExecCommand();
		SessionManager.getInstance().closeSession(SessionManager.getInstance().getSessionById(lpCmd.para.get("sessionId")));
		SessionManager.getInstance().removeSession(lpCmd.para.get("sessionId"));
		return result;
	}

}
