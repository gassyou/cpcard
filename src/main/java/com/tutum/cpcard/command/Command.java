package com.tutum.cpcard.command;

import java.io.IOException;
import java.util.ArrayList;

import com.tutum.cpcard.respond.JosnRespondContent;
import com.tutum.cpcard.respond.RespondResult;



public interface Command {
	
	JosnRespondContent jsonRespondContent = new JosnRespondContent();
	ArrayList<RespondResult> actionResults  = new ArrayList<RespondResult>();
	public ArrayList<RespondResult> ExecCommand() throws IOException;

}
