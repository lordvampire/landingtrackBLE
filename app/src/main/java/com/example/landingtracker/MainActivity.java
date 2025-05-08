package com.example.landingtracker;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.Toast;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private BLEManager bleManager;
    private static final String DEVICE_ADDRESS = "00:00:00:00:00:00";

    @Override
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

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
        layout.addView(scanButton);

        Button connectButton = new Button(this);
        connectButton.setText("connect");
        connectButton.setOnClickListener(v -> {
            bleManager.connectToDevice(DEVICE_ADDRESS, this);
        });
        layout.addView(connectButton);

        setContentView(layout);
    }

}
