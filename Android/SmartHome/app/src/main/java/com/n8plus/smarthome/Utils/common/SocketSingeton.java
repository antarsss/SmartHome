package com.n8plus.smarthome.Utils.common;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketSingeton {
    private final String URL_SERVER = Constant.URL;
    private static Socket socket;
    private static boolean emit = false;
    private static boolean on = false;
    public static int countEmit = 0;
    public static int countReceive = 0;

    {
        try {
            socket = IO.socket(URL_SERVER);
        } catch (URISyntaxException e) {
        }
    }

    public void connect() {
        socket.connect();
    }

    public void emit(final String event, final Object... objects) {
        synchronized (socket) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    socket.emit(event, objects);
                    Log.e("COUNT EMIT", "" + countEmit++);
                }
            }).start();
        }
    }

    public void on(String event, Emitter.Listener emitter) {
        synchronized (socket) {
            socket.on(event, emitter);
            Log.e("COUNT RECEIVE", "" + countReceive++);
        }
    }
}
