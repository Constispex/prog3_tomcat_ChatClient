package de.medieninformatik.server;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/chatClient")
public class ChatServer {
    private static final List<Session> verbindung = new CopyOnWriteArrayList<>();
    private static final Logger LOGGER =
            Logger.getLogger(ChatServer.class.getName());
    private boolean isSameUser;

    public static Runnable quelle() {
        return () -> System.out.println("new Runnable");
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("%s: onOpen aufgerufen%n", session.getId());
        verbindung.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.printf("%s: onClose aufgerufen%n", session.getId());
        verbindung.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.err.printf("%s: %s%n", session.getId(), error.getMessage());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        LOGGER.log(Level.INFO, "New message from Client [{0}]: {1}",
                new Object[]{session.getId(), message});
        return "Server received [" + message + "]";
    }
}
