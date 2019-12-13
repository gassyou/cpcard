package com.tutum.cpcard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;  

import com.tutum.cpcard.respond.JosnRespondContent;
import com.tutum.cpcard.respond.RespondResult;
import com.tutum.cpcard.command.CloseSessionCmd;
import com.tutum.cpcard.command.Command;
import com.tutum.cpcard.command.GotoBacklogIDCmd;
import com.tutum.cpcard.command.HostMeetingCmd;
import com.tutum.cpcard.command.JoinMeetingCmd;
import com.tutum.cpcard.command.LeaveProjectCmd;
import com.tutum.cpcard.command.SelectProjectCmd;
import com.tutum.cpcard.command.SubmitCPCmd;

@ServerEndpoint(value="/meeting")
public class WebSocketMeeting {
	
	SessionManager sm = SessionManager.getInstance();
	
	@OnOpen
	public void onOpen(Session session) throws IOException {
		// 開始時
		try {
			
			sm.addSession(session);
			JosnRespondContent josnRespond = new JosnRespondContent();
			session.getBasicRemote().sendText(josnRespond.returnProjectInfo(null));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@OnMessage
	public void onMessage(Session session,String message) throws IOException{
		try {
			
			HashMap<String, String> para = new HashMap<String, String>();

			JSONObject projectCpJSONObject = new JSONObject(message); 
			String action = projectCpJSONObject.getString("action");

			para.put("projectName", projectCpJSONObject.getString("project"));
			para.put("memberName", projectCpJSONObject.getString("name"));
			para.put("isHost", projectCpJSONObject.getString("ishost"));
			para.put("backlogId", String.valueOf(projectCpJSONObject.getInt("backlogid")));
			para.put("worth", String.valueOf(projectCpJSONObject.getInt("cp")));
			para.put("sessionId", session.getId());
			
			Command command = getCommandByType(action, para);
			ArrayList<RespondResult> actionResults = command.ExecCommand();
			echo(actionResults);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.toString());
		}
		
	}
	
	@OnClose
	public void onClose(Session session){
	}
	
	@OnError
	public void onError(Throwable t){
		System.out.println(t.getMessage());
	}
	
	private Command getCommandByType(String type, Map p)
	{
		Command command = null;
		if(type.equals("JoinMeeting")){
			command = new JoinMeetingCmd(p);
		}else if(type.equals("HostMeeting")){
			command = new HostMeetingCmd(p);
		}else if(type.equals("SubmitCP")){
			command = new SubmitCPCmd(p);
		}else if(type.equals("SelectAProject")){
			command = new SelectProjectCmd(p);
		}else if(type.equals("GotoBacklog")){
			command = new GotoBacklogIDCmd(p);
		}else if(type.equals("LeaveProject")){
			command = new CloseSessionCmd(new LeaveProjectCmd(p));
		}
		
		return command;
	}
	
	
	private void echo(ArrayList<RespondResult> actionResults) throws IOException
	{
		for(RespondResult actionResult :actionResults)
		{
			ArrayList<String> sessionIDs = actionResult.getReceptor();
			for (String id : sessionIDs) {
				Session s = sm.getSessionById(id);
				
				if(s !=null)
				{
					s.getBasicRemote().sendText(actionResult.getResultContent());
				}
			}
		}
		
	}
}
