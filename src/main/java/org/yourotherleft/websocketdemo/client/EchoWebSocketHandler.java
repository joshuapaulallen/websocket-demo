package org.yourotherleft.websocketdemo.client;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.yourotherleft.websocketdemo.message.EchoMessage;

@Component
public class EchoWebSocketHandler extends AbstractWebSocketHandler {

	private static final Log log = LogFactory.getLog(EchoWebSocketHandler.class);

	private final List<EchoMessage> receivedMessages;

	private WebSocketSession webSocketSession;

	public EchoWebSocketHandler() {
		this.receivedMessages = new ArrayList<>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		super.afterConnectionEstablished(webSocketSession);

		this.webSocketSession = webSocketSession;
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("oh noes, error!", exception);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.info(String.format("handling message [%s]", message));
		super.handleMessage(session, message);
	}

	public List<EchoMessage> getReceivedMessages() {
		return new ArrayList<>(receivedMessages);
	}


}
