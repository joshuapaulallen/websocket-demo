package org.yourotherleft.websocketdemo.client;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.yourotherleft.websocketdemo.message.EchoMessage;

/**
 * A session handler that listens for messages
 */
public class EchoStompSessionHandler extends StompSessionHandlerAdapter {

	private static final Log log = LogFactory.getLog(EchoStompSessionHandler.class);

	private final String destination;
	private final List<EchoMessage> receivedMessages;
	private StompSession session;

	public EchoStompSessionHandler(String destination) {
		this.destination = destination;
		receivedMessages = new ArrayList<>();
		session = null;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		this.session = session;
		session.subscribe(destination, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return EchoMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				if (payload instanceof EchoMessage) {
					log.info(String.format("processed echo message [%s]", payload));
					receivedMessages.add((EchoMessage) payload);
				} else {
					log.error(String.format("unexpected message [%s] of type [%s]", payload, payload.getClass()));
				}
			}
		});
	}

	public List<EchoMessage> getReceivedMessages() {
		return new ArrayList<>(receivedMessages);
	}

	public void sendEcho(String message) {
		session.send(destination, new EchoMessage(message, new Date()));
	}
}