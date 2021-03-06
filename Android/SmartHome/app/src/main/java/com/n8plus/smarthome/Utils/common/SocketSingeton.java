package com.n8plus.smarthome.Utils.common;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketSingeton {
    private static Socket connect;
    public static int countEmit = 0;
    public static int countReceive = 0;

    public void connect() {
        try {
            connect = IO.socket(Constant.URL).connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void emit(final String event, final Object... objects) {
        synchronized (connect) {
            connect.emit(event, objects);
            Log.e("COUNT EMIT", "" + countEmit++);
        }
    }

    public void on(String event, Emitter.Listener emitter) {
        synchronized (connect) {
            connect.on(event, emitter);
            Log.e("COUNT RECEIVE", "" + countReceive++);
        }
    }
}
