package coms309.controller;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;

import com.google.gson.*;

import coms309.api.dataobjects.ApiCodeSubmission;

@Controller
@ServerEndpoint(value = "/run/assignment/{assignmentId}/user/{userId}")
public class CodeRunnerWebSocketController {
    
    @OnOpen
    public void onOpen(Session session, @PathParam("assignmentId") long assignmentId, @PathParam("userId") long userId) {

    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        Gson g = new Gson();
        ApiCodeSubmission submission = g.fromJson(message, ApiCodeSubmission.class);
        sendMessage(session, submission.getName());
    }

    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

}
