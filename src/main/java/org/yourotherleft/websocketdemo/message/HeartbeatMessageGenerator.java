package org.yourotherleft.websocketdemo.message;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Generates and publishes outgoing {@link EchoMessage}s to a websocket.
 */
@Component
public class HeartbeatMessageGenerator {

	private static final Log log = LogFactory.getLog(HeartbeatMessageGenerator.class);

	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public HeartbeatMessageGenerator(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@Scheduled(fixedRate = 1000)
	public void doHeartbeat() {
		Date date = new Date();
		EchoMessage message = new EchoMessage(String.format("thump thump [%s]", date), date);
		log.info(String.format("heartbeat message [%s]", message));
		simpMessagingTemplate.convertAndSend("/topic/messages", message);
	}

}
