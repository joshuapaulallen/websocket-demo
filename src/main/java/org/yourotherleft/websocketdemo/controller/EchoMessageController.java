package org.yourotherleft.websocketdemo.controller;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.yourotherleft.websocketdemo.message.EchoMessage;

@Controller
public class EchoMessageController {

	private static final Log log = LogFactory.getLog(EchoMessageController.class);

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

}
