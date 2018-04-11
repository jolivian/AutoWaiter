package fullerton.edu.autowaiter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class TakeDeliver extends AppCompatActivity {

    Button taker;
    Button deliverer;
    TextView tableNumber;

    // BT
    int tableKey = 14;
    int command = 1;
    String address = null , name=null;
    private static final String TAG = "MyActivity";

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_deliver);
        initialize();

        final String tableChosen = getIntent().getStringExtra("tableChosen");

        taker.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // TELL ARDUINO WHICH TABLE TO GO TO
                try {
                    //bluetooth_connect_device();
                    travel(tableChosen);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                Intent myIntent = new Intent(TakeDeliver.this, Traveller.class);
                myIntent.putExtra("tableChosen", tableChosen);
                myIntent.putExtra("takeORdeliver", "0");
                TakeDeliver.this.startActivity(myIntent);
            }
        });

        deliverer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    //bluetooth_connect_device();
                    travel(tableChosen);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                Intent myIntent = new Intent(TakeDeliver.this, Traveller.class);
                myIntent.putExtra("tableChosen", tableChosen);
                myIntent.putExtra("takeORdeliver", "1");
                TakeDeliver.this.startActivity(myIntent);
            }
        });
    }

    private void initialize(){
        taker = findViewById(R.id.bTakeOrder);
        deliverer = findViewById(R.id.bDeliverOrder);
        tableNumber = findViewById(R.id.tvTableNumber);

        tableNumber.setText(getIntent().getExtras().getString("tableChosen"));

        try {bluetooth_connect_device();}
        catch (Exception e){}
    }

    // Table Destination
    private void travel(String sTable)
    {
        int iTable = Integer.parseInt(sTable);
        try
        {
            if (btSocket!=null)
            {
                btSocket.getOutputStream().write(tableKey);
                btSocket.getOutputStream().write(iTable); // Which table
                btSocket.getOutputStream().write(command);// Go
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void bluetooth_connect_device() throws IOException
    {
        try
        {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            address = myBluetooth.getAddress();
            pairedDevices = myBluetooth.getBondedDevices();
            if (pairedDevices.size()>0)
            {
                for(BluetoothDevice bt : pairedDevices)
                {
                    address=bt.getAddress().toString();name = bt.getName().toString();
                    Toast.makeText(getApplicationContext(),"Connected", Toast.LENGTH_SHORT).show();

                }
            }

        }
        catch(Exception we){}
        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
        btSocket.connect();
    }
}
