var stompClient = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#message-list").show();
  }
  else {
    $("#message-list").hide();
  }
  $("#messages").html("");
}

function connect() {
  var socket = new SockJS('/sockjs');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', function (message) {
      showMessage(JSON.parse(message.body).message);
    });
  });
}

function disconnect() {
  if (stompClient != null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  stompClient.send("/app/echo", {}, $("#message").val());
}

function showMessage(message) {
  $("#messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $( "#connect" ).click(function() { connect(); });
  $( "#disconnect" ).click(function() { disconnect(); });
  $( "#send" ).click(function() { sendName(); });
});