package coms309.controller.websocket;

import coms309.api.dataobjects.ApiGrade;
import coms309.database.dataobjects.Grade;
import coms309.database.dataobjects.User;
import coms309.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

/**
 * Websocket in order to tell the client when a grade has been updated.
 */
@ServerEndpoint("/grade/{username}")
@Component
public class GradeWebsocket {

    private static final Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static final Map<String, Session> usernameSessionMap = new Hashtable<>();
    @Autowired
    private static UserService us;

    public static boolean isActive(String username) {
        return usernameSessionMap.containsKey(username);
    }

    public static boolean isActive(Long id) {
        Optional<User> user = us.findById(id);
        if (user.isPresent()) {
            String username = user.get().getUsername();
            return isActive(username);
        }
        return false;
    }

    // Assume the session is active
    public static void sendUpdate(String username, Grade grade) throws EncodeException, IOException {
        Session session = usernameSessionMap.get(username);
        ApiGrade ag = new ApiGrade(grade);
        session.getBasicRemote().sendObject(ag);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        session.getBasicRemote().sendText("Connected: " + username);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.getBasicRemote().sendText("Disconnected: " + sessionUsernameMap.get(session));
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }

}
