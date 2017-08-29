# Synopsis

This Spring Boot project is a playground for messing with websockets.  The project has a websocket server and a web-based SockJS client.

# The Websocket Server

The server generates and sends heartbeat messages at a regular interval.  The server also echoes incoming messages back to the client.  Both messages use the topic `/topic/messages`. 

# The Web Client

Navigate to `localhost:[port]` in a browser to interact with the SockJS websocket client.