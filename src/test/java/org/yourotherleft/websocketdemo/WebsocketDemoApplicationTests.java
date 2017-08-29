package org.yourotherleft.websocketdemo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.yourotherleft.websocketdemo.client.EchoStompSessionHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class WebsocketDemoApplicationTests {

	@LocalServerPort
	private String port;

	@Test
	public void testWithClient() throws InterruptedException {
		WebSocketClient webSocketClient = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		EchoStompSessionHandler sessionHandler = new EchoStompSessionHandler("/topic/messages");
		stompClient.connect("ws://localhost:" + port + "/stompClient", sessionHandler);
		stompClient.start();
		assertTrue(stompClient.isRunning());

		// wait a few seconds, and make sure we've successfully processed some messages
		Thread.sleep(3000);
		assertFalse(sessionHandler.getReceivedMessages().isEmpty());

		String uuid = UUID.randomUUID().toString();
		sessionHandler.sendEcho(uuid);
		Thread.sleep(2000);
		assertTrue(sessionHandler.getReceivedMessages().stream()
			.anyMatch(echoMessage -> echoMessage.getMessage().contains(uuid))
		);

		// stop the client and
		stompClient.stop();
		assertFalse(stompClient.isRunning());
	}

}
