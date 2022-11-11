package coms309.controller.websocket;

import coms309.api.dataobjects.ApiUser;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Websocket test
 * Updates the client whenever the user information has changed
 */
@ServerEndpoint("/user/{username}")
@Component
public class UserWebsocket {

    private static final Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static final Map<String, Session> usernameSessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        session.getBasicRemote().sendText("Connected: " + username);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.getBasicRemote().sendText("Disconnected");
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }

    public static void sendUpdatedUser(String username, ApiUser apiUser) throws EncodeException, IOException {
        Session session = usernameSessionMap.get(username);
        session.getBasicRemote().sendObject(apiUser);
    }
}
