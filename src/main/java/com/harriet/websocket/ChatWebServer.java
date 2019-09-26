package com.harriet.websocket;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Harriet on 9/26/2019.
 */
@ServerEndpoint(value = "/chat/{phone}")
public class ChatWebServer {
    Map<String,Session> sessionMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("phone")String phone) {
        System.out.println("Connection established ");
        sessionMap.put(phone,session);

    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("session closed");
        sessionMap.remove(session);

    }

    @OnMessage
    public void onMessage(String message,Session session,@PathParam("phone")String phone) {
        Message msg= new Gson().fromJson(message,Message.class);
        if(sessionMap.get(phone).equals(session)) {
            msg.setFrom(phone);
            deliverMessage(msg);
        }
    }
    private void logMessage(Message msg){


    }

    private void deliverMessage(Message msg) {

        Session recipientSession=sessionMap.get(msg.getRecipient());
        try {
            recipientSession.getBasicRemote().sendObject(msg.getContent());
            //successfully sent
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Error encountered at "+ session.getId());
    }
}
