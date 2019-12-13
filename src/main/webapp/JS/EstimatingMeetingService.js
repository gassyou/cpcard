angular.module('MeetingService', [
  'ngAnimate',
  'ngWebSocket'
])
.service('EstimatingMeetingService', function($websocket) {

//	var ws = $websocket('ws://mars-0822-cpcard.daoapp.io/cpcard/meeting');
var ws = $websocket('ws://localhost:8080/cpcard/meeting');
  this.init = function(){
	  var self = this;
	  ws.onMessage(function(event) {
		  var jsonResult = {};
		  try{
			  jsonResult =  JSON.parse(event.data);
			  console.log("002:ReturnJson:"+JSON.stringify(jsonResult));
			  self.refresh(jsonResult);
		  }catch(e){
			  jsonResult = {"projects":[],"members":[],"backlog":{},"msg":event.data};
			  self.refresh(jsonResult);
		  }

	  });

	  ws.onError(function(event) {
		  var jsonResult = {"projects":[],"members":[],"backlog":{},"msg":"Connection Error."};
		  self.refresh(jsonResult);
	  });
	  
	  ws.onOpen(function() {
		  console.log("001:Connection Opened");
	  });
  };

  this.send = function(message){
	  if (angular.isString(message)) {
		  ws.send(message);
	  }
	  else if (angular.isObject(message)) {
	        ws.send(JSON.stringify(message));
	  }
  };

  //return service;
});