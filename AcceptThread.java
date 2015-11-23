package com.example.erkan.my_bluetooth_controller;
import android.app.Activity;
import android.bluetooth.*;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by erkan on 2015-09-12.
 */
public class AcceptThread extends AsyncTask<Void, String, BluetoothSocket>{


    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final String NAME = null;
    private BluetoothAdapter mBluetoothAdapter;
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;

    private manageConnection connection;
    private Activity activity;

    public AcceptThread(BluetoothDevice device, Activity mainActivity)
    {
        BluetoothSocket tmp = null;
        mmDevice = device;

        this.activity = mainActivity;

        try{
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e){}
        mmSocket = tmp;
    }


    protected Void onPostExcecute(String input)
    {

            connection = new manageConnection(mmSocket);
            Log.d("What input?", input);
            connection.sendmsg(input);

            Button sendButton = (Button) activity.findViewById(R.id.send_button);

            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    EditText messageTxt = (EditText) activity.findViewById(R.id.send_txt);
                    connection.sendmsg(messageTxt.getText().toString());
                    messageTxt.setText("");
                }
            });

            return null;
    }


    public void sendshit(String msg){

       if(mmSocket != null){
           connection.sendmsg(msg);
       }
    }
    public BluetoothSocket getSocket()
    {
        if(mmSocket != null){
            return mmSocket;
        }else{
            return null;
        }
    }
    protected BluetoothSocket doInBackground(Void... param)
    {
        try{
            mmSocket.connect();
        }catch (IOException connectionException){
            try{
                mmSocket.close();
            }catch (IOException closeException){}
        }

        return mmSocket;
    }

    private void cancel()
    {
        try{
            mmSocket.close();
        } catch (IOException e){}
    }


}
