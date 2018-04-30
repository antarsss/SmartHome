package com.n8plus.smarthome.Utils.common;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketSingeton {
    public static int countEmit = 0;
    public static int countReceive = 0;
    private static Socket socket;
    private static int retry = 0;
    private boolean authenticated = false;

    public SocketSingeton() {
        connect();
    }

    public void connect() {
        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.reconnection = true;
        try {
            socket = IO.socket(Constant.URL, options).connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public boolean authorization(final JSONObject authen) {
        retry++;
        socket.emit("authorization", authen);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socket.on("authenticated", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject object = new JSONObject(args[0].toString());
                    authenticated = object.getBoolean("auth");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(retry);
        if (!authenticated && retry < 5) {
            return authorization(authen);
        }
        return authenticated;
    }

    public void emit(String event, Object... args) {
        Log.e(event, args[0].toString());
//        Log.e("COUNT EMIT", "" + countEmit++);
        socket.emit(event, args);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Emitter on(String event, Emitter.Listener fn) {
//        Log.e("COUNT RECEIVE", "" + countReceive++);
        return socket.on(event, fn);
    }

    public Emitter once(String event, Emitter.Listener fn) {
//        Log.e("COUNT RECEIVE", "" + countReceive++);
        return socket.once(event, fn);
    }
}
