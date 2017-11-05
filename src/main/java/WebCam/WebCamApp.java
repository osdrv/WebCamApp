package WebCam;

import Phoenix.PhoenixWebSocket;
import Phoenix.PhoenixSocketMessage;
import com.neovisionaries.ws.client.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URI;

public class WebCamApp {
    public static void main(String[] args) {

        WebSocketFactory factory = new WebSocketFactory()
                .setConnectionTimeout(5_000);
        factory.setVerifyHostname(false);

        try {
            PhoenixWebSocket ws = new PhoenixWebSocket(
                    factory, URI.create("ws://whitebox.dev:4000/socket/websocket"));

            ws.addListener(new WebCamWebSocketAdapter());

            System.out.println("Connecting to socket");
            ws.connect();

            System.out.println("Sending join message");
            ws.sendMessage(
                    new PhoenixSocketMessage.Builder()
                            .set("event", "phx_join")
                            .set("topic", "webcams:b079542f-c728-4939-a1bc-90e51499603e")
                            .set("payload", new JSONObject())
                            .set("ref", null)
                            .build()
            );

            Thread.sleep(5_000);
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException: " + ex.getMessage());
        } catch (WebSocketException ex) {
            System.err.println("WebSocketException: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }
}
