package com.example.erkan.my_bluetooth_controller;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by erkan on 2015-09-13.
 */
public class manageConnection extends AsyncTask<Void, Void, Void> {
    private final BluetoothSocket mmSocket;
    private final OutputStream mmOutStream;
    private final InputStream mmInStream;

    private static String MANAGE_CONNECTION_ID = "Manage connection";
    private String msg = null;

    public manageConnection(BluetoothSocket socket)
    {
        mmSocket = socket;
        OutputStream tmpOutStream = null;
        InputStream tmpInStream = null;

        try {
            tmpOutStream = socket.getOutputStream();
            tmpInStream = socket.getInputStream();
        }catch (IOException streamExeception)
        {
            //TODO
        }

        mmOutStream = tmpOutStream;
        mmInStream = tmpInStream;
    }



    protected void onPostExecute()
    {

    }


    protected Void doInBackground(Void...params)
    {
        if(msg != null) {
            sendmsg(msg);
        }

        return null;
    }

    public void sendmsg(String msg)
    {
        this.msg = msg;
        try {
            mmOutStream.write(msg.getBytes());
            Log.d(MANAGE_CONNECTION_ID, "sendmsg success!" + msg);
        }catch (IOException writeExecption)
        {
            Log.e(MANAGE_CONNECTION_ID, "Error in sendmsg");
        }
    }
}
