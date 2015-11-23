package com.example.erkan.my_bluetooth_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public ImageView mImageView;
    static final int REQUEST_ENABLE_BT = 1; //For enableBt method startActivityforResult
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mmDevice;

    public AcceptThread acceptConnection;

    int[] coord = new int[]{0,0};

    DrawBall myBall2;
    MyOwnDraw jahap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.mImageView);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        supportBl(mBluetoothAdapter);
        getBLGoing(coord);

        //new DownloadImageFromWeb(this).execute("http://dreamatico.com/data_images/monkey/monkey-3.jpg");


        RelativeLayout left_layout = (RelativeLayout) findViewById(R.id.left_layout);
        RelativeLayout right_layout = (RelativeLayout) findViewById(R.id.right_layout);

        Context left_context = left_layout.getContext();
        Context right_context = right_layout.getContext();



        if(acceptConnection != null) {
            jahap = new MyOwnDraw(left_context, coord, acceptConnection,myBall2);
            myBall2 = new DrawBall(right_context, acceptConnection);
        }else{
            jahap = null;
            myBall2 = null;
        }



        left_layout.addView(jahap);
        right_layout.addView(myBall2);

        int[] a = jahap.getThrottleNdYaw();
        Log.d("throttle and yaw?", java.util.Arrays.toString(a) + "");



       // getBLGoing(coord);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getBLGoing(int[] co)
    {
        getDevice();
        if(mmDevice != null) {
            acceptConnection = new AcceptThread(mmDevice, this);
            Log.d("accept", "We created a new acceptThread");
            acceptConnection.execute();
            acceptConnection.onPostExcecute("Sucess");
            Log.d("efter post execute", acceptConnection.toString());
        }
    }

    public void getCord(int[] coords)
    {
       // if(mmDevice != null) {
            Log.d("vad?", java.util.Arrays.toString(coords));
            acceptConnection.onPostExcecute(java.util.Arrays.toString(coords));
        //}
    }

    private BluetoothDevice getDevice()
    {

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if(pairedDevices.size() > 0 ){
            for(BluetoothDevice b : pairedDevices){
                if(b.getName().equals("HC-06")){
                    mmDevice = b;
                    mBluetoothAdapter.cancelDiscovery();
                    Log.d("Return","mmDevice IALLFALL");
                    return mmDevice;
                }
            }
        }
        return null;
    }

    private void enableBl(BluetoothAdapter mAdapter)
    {
        if(!mAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private Boolean supportBl(BluetoothAdapter mAdapter)
    {
        if(mAdapter == null){
            return false; //Not supported
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.connect) {
            getBLGoing(coord);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
