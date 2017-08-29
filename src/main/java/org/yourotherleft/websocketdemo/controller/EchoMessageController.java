package org.yourotherleft.websocketdemo.controller;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.yourotherleft.websocketdemo.message.EchoMessage;

@Controller
@EnableScheduling
public class EchoMessageController {

	private static final Log log = LogFactory.getLog(EchoMessageController.class);

	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public EchoMessageController(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	/**
	 * Endpoint for incoming messages that echos the message back to the client.
	 *
	 * @param message The message.
	 * @return The message echoed back to the client.
	 */
	@MessageMapping("/echo")
	@SendTo("/topic/messages")
	public EchoMessage send(String message) {
		log.info(String.format("echoing message [%s]", message));
		return new EchoMessage(message, new Date());
	}

	/**
	 * Generate a heartbeat message by-hand at a fixed interval.
	 */
	@Scheduled(fixedRate = 1000)
	public void doHeartbeat() {
		Date date = new Date();
		EchoMessage message = new EchoMessage(String.format("thump thump [%s]", date), date);
		log.info(String.format("heartbeat message [%s]", message));
		simpMessagingTemplate.convertAndSend("/topic/messages", message);
	}

}
