package com.example.landingtracker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private BLEManager bleManager;
    private static final String DEVICE_ADDRESS = "00:00:00:00:00:00";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bleManager = new BLEManager(this);

        if (!bleManager.isBluetoothSupported(this)) {
            Toast.makeText(this, "BLE is not supported on this device.", Toast.LENGTH_SHORT).show();
            finish();
        }
        Button scanButton = new Button(this);
        scanButton.setText("Scan BLE Devices");
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    bleManager.scanLeDevice(true);
                }
            }
        });
        app.addContentView(scanButton,new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        Button connectButton = new Button(this);
        connectButton.setText("connect");
        connectButton.setOnClickListener(v -> {
            bleManager.connectToDevice(DEVICE_ADDRESS, this);
        });
        app.addContentView(connectButton,new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
