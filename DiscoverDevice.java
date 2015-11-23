package com.example.erkan.my_bluetooth_controller;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
/**
 * Created by erkan on 2015-09-07.
 */
public class DiscoverDevice extends AsyncTask<String, Void, String>
{
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                
            }
        }
    };
    private void disocverDevice()
    {

    }

    protected String doInBackground(String... params)
    {
        return null;
    }
}
