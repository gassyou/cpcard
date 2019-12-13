(function(angular) {
  'use strict';
angular.module('EstimatingMeeting', ["ui.bootstrap","ngSanitize","MeetingService"])

  .controller('EstimatingMeetingController', ['$scope', "$modal","EstimatingMeetingService",function($scope,$modal,EstimatingMeetingService) {
	
	$scope.selectProjectName = 'Please select a Project';
	$scope.selectedCpValue = 0;
	$scope.backlogID = 1;
	
	EstimatingMeetingService.refresh = function(jsonData){
		
		 $scope.showErrorMsg = "hidden";
		  if(jsonData.msg){
			  $scope.showErrorMsg = "";
			  $scope.errorMsg = jsonData.msg;
			  
			  if($scope.errorMsg.indexOf("Host Meeting Error")>=0 || $scope.errorMsg.indexOf("Join Meeting Error")>=0 ){
				  $scope.joinMeetingPanelHidden = "";
			  }
		  }else{
			if(jsonData.projects.length > 0){$scope.projects = jsonData.projects;}
			if(jsonData.members.length > 0){$scope.members = jsonData.members;}
			if(jsonData.backlog.id){
				var backlog = jsonData.backlog;
				$scope.votes = [];
				
				if(backlog.id){
					$scope.backlogID = backlog.id;
					var nHidden = "hidden";
					if(backlog.isFinish == "Yes"){
						nHidden = "";
					}
					var votes = eval(backlog.votes);
		
					for(var i=0;i<votes.length;i++){ 
						votes[i].nameHidden = nHidden;
						if(votes[i].cp == 0){
							votes[i].voteStyle = "readyVote";
						}
						else{
							votes[i].voteStyle = "finishVote";
						}
						if(backlog.isFinish == "No")
						{
							votes[i].cp = votes[i].name ;
						}
						$scope.votes.push(votes[i]);
					} 
				}
			}
		  }
		$scope.$digest();
	}
	
	
	EstimatingMeetingService.init();
	
	$scope.ishost = "";
	$scope.sendData = {
			action:"",
			project:"",
			name:"",
			ishost:"",
			backlogid:"",
			cp:"",
	};
	
	window.onbeforeunload = function (event) {    
		var message = 'Are you sure you want to exit this meeting ?  If you are host ,you will terminate this meeting.';
		if (typeof event == 'undefined') {
			event = window.event;
		}
		if (event) {
			event.returnValue = message;
		}
		return message;    
	}
	
	window.onunload = function(){
		$scope.exitProject();
	}
		  
	$scope.hostMeeting = function()
	{
		//$scope.showErrorMsg = "hidden";
		$scope.showWhenHost = '';
		$scope.projectName = "";
		$scope.selectedProjectFromList = "Host a Meeting";
		$scope.ishost = "Yes";
		
	}
	$scope.selectAMeeting = function(x)
	{
		//$scope.showErrorMsg = "hidden";
		$scope.showWhenHost = 'hidden';
		$scope.projectName = x;
		$scope.selectedProjectFromList = x;
		$scope.ishost = "No";
		
		//$scope.joinMeetingPanelHidden = "hidden";
		$scope.sendData.action = "SelectAProject";
		$scope.sendData.project = $scope.projectName;
		$scope.sendData.name = "";
		$scope.sendData.ishost = $scope.ishost;
		$scope.sendData.backlogid = 0;
		$scope.sendData.cp = 0;
		EstimatingMeetingService.send($scope.sendData);
		
	}
	
	$scope.JoinProject = function()
	{
		//$scope.showErrorMsg = "hidden";
		if(!$scope.projectName)
		{
			$scope.errorMsg = "Please input or select project name.";
			$scope.showErrorMsg ="";
			return;
		}
		if(!$scope.memberName)
		{
			$scope.errorMsg = "Please input your name.";
			$scope.showErrorMsg = "";
			return;
		}
		$scope.joinMeetingPanelHidden = "hidden";
		if($scope.ishost == "No")
		{
			$scope.sendData.action = "JoinMeeting";
		}
		else
		{
			$scope.sendData.action = "HostMeeting";
		}
		$scope.sendData.project = $scope.projectName;
		$scope.sendData.name = $scope.memberName;
		$scope.sendData.ishost = $scope.ishost;
		$scope.sendData.backlogid = 0;
		$scope.sendData.cp = 0;
		EstimatingMeetingService.send($scope.sendData);
	}
	
	$scope.backlogIDReduction = function()
	{
		if($scope.projectName == null || $scope.memberName == null){
			$scope.showErrorMsg = "";
			$scope.errorMsg = "Error: You need host or join a meeting.";
			
			return;
		}
		
		if($scope.backlogID>1)
		{
			$scope.backlogID--;
			$scope.sendData.action = "GotoBacklog";
			$scope.sendData.project = $scope.projectName;
			$scope.sendData.name = $scope.memberName;
			$scope.sendData.ishost = $scope.ishost;
			$scope.sendData.backlogid = $scope.backlogID;
			$scope.sendData.cp = 0;
			EstimatingMeetingService.send($scope.sendData);
		}
	}
	$scope.backlogIDPlus = function()
	{
		if($scope.projectName == null || $scope.memberName == null){
			$scope.showErrorMsg = "";
			$scope.errorMsg = "Error: You need host or join a meeting.";
			
			return;
		}
		
		$scope.backlogID++;
		$scope.sendData.action = "GotoBacklog";
		$scope.sendData.project = $scope.projectName;
		$scope.sendData.name = $scope.memberName;
		$scope.sendData.ishost = $scope.ishost;
		$scope.sendData.backlogid = $scope.backlogID;
		$scope.sendData.cp = 0;
		EstimatingMeetingService.send($scope.sendData);
	}
	
	$scope.gotoBacklogID = function()
	{
		if($scope.projectName == null || $scope.memberName == null){
			$scope.showErrorMsg = "";
			$scope.errorMsg = "Error: You need host or join a meeting.";
			
			return;
		}
		
		$scope.sendData.action = "GotoBacklog";
		$scope.sendData.project = $scope.projectName;
		$scope.sendData.name = $scope.memberName;
		$scope.sendData.ishost = $scope.ishost;
		$scope.sendData.backlogid = $scope.backlogID;
		$scope.sendData.cp = 0;
		EstimatingMeetingService.send($scope.sendData);
	}
	
	$scope.openDialogForm = function() 
	{
		$modal.open({
			templateUrl:"T_dialogForm",
			scope:$scope
		});
	}
	
	$scope.exitProjectConfirm = function() {
		
		//window.close();
		//window.opener=null;
		//window.open('','_self');
		//window.close();
		//$scope.msg = "Are you sure you want to exit this meeting ? <br/> If you are host ,you will terminate this meeting.";
		//$scope.openDialogForm();
		
		//window.open('','_self').close();
		
		var userAgent = navigator.userAgent;
		if (userAgent.indexOf("Firefox") != -1
		|| userAgent.indexOf("Chrome") != -1) {
			window.location.href = "about:blank";
		} else {
		window.opener = null;
		window.open("", "_self");
		window.close();
		}
	}
	
	$scope.exitProject = function(){
		
		$scope.sendData.action = "LeaveProject";
		$scope.sendData.project = $scope.projectName!=null ? $scope.projectName:"";
		$scope.sendData.name = $scope.memberName!=null ? $scope.memberName:"";
		$scope.sendData.ishost = $scope.ishost;
		$scope.sendData.backlogid = $scope.backlogID;
		$scope.sendData.cp = 0;
		EstimatingMeetingService.send($scope.sendData);
	}
	
	$scope.submitCP = function(){
		
		if($scope.projectName == null || $scope.memberName == null){
			$scope.showErrorMsg = "";
			$scope.errorMsg = "Error: You need host or join a meeting.";
			
			return;
		}
		
		if($scope.selectedCpValue == 0){
			
			$scope.showErrorMsg = "";
			$scope.errorMsg = "Error: You need select a cp value.";
			return;
		}
		
		$scope.sendData.action = "SubmitCP";
		$scope.sendData.project = $scope.projectName;
		$scope.sendData.name = $scope.memberName;
		$scope.sendData.ishost = $scope.ishost;
		$scope.sendData.backlogid = $scope.backlogID;
		$scope.sendData.cp = $scope.selectedCpValue;
		EstimatingMeetingService.send($scope.sendData);
	}
	
	$scope.isMe = function(x){
		if(x == $scope.memberName){
			return "";
		}
		else{
			return "hidden";
		}
	}
	
	$scope.showErrorPanel = function(x){
		console.log("errorShow:"+x);
		return "alert alert-warning alert-dismissible " + x;
	}
	
  }])
  
})(window.angular);