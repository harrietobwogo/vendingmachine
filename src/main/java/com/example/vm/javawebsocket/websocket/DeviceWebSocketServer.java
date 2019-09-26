package com.example.vm.javawebsocket.websocket;

import com.example.vm.javawebsocket.websocketmodel.Device;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Harriet on 9/26/2019.
 */
@ApplicationScoped
@ServerEndpoint("/action")
public class DeviceWebSocketServer {
    @Inject
    private DeviceSessionHandler deviceSessionHandler;

    @OnOpen
    public void open(Session session) {
        deviceSessionHandler.addSession(session);

    }

    @OnClose
    public void close(Session session) {
        deviceSessionHandler.removeSession(session);
    }


    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }


    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.getString("name"));
                device.setDescription(jsonMessage.getString("description"));
                device.setType(jsonMessage.getString("type"));
                device.setStatus("Off");
                deviceSessionHandler.addDevice(device);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                deviceSessionHandler.removeDevice(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                deviceSessionHandler.toggleDevice(id);
            }
        }
    }
}
