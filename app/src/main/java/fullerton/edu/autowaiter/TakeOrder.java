package fullerton.edu.autowaiter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;
import java.util.UUID;

public class TakeOrder extends AppCompatActivity {

    int GoodMealCount;
    int GoodChunksCount;
    int GoodChickwichCount;

    String fullOrder;

    Button addGoodMeal;
    Button subGoodMeal;
    Button addGoodChunks;
    Button subGoodChunks;
    Button addGoodChickwich;
    Button subGoodChickwich;
    Button order;

    TextView tableNumber;

    TextView GoodMealQuantity;
    TextView GoodChunksQuantity;
    TextView GoodChickwichQuantity;

    EditText specialOrder;

    // UDP
    int server_port = 12345;
    int msg_length;
    byte[] message;

    // BT
    int homeKey = 15;
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
        setContentView(R.layout.activity_take_order);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initialize();

        addGoodMeal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GoodMealCount++;
                GoodMealQuantity.setText(String.valueOf(GoodMealCount));
            }
        });

        subGoodMeal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(GoodMealCount > 0) {
                    GoodMealCount--;
                }

                GoodMealQuantity.setText(String.valueOf(GoodMealCount));
            }
        });

        addGoodChunks.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GoodChunksCount++;
                GoodChunksQuantity.setText(String.valueOf(GoodChunksCount));
            }
        });

        subGoodChunks.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(GoodChunksCount > 0) {
                    GoodChunksCount--;
                }

                GoodChunksQuantity.setText(String.valueOf(GoodChunksCount));
            }
        });

        addGoodChickwich.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                GoodChickwichCount++;
                GoodChickwichQuantity.setText(String.valueOf(GoodChickwichCount));
            }
        });

        subGoodChickwich.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(GoodChickwichCount > 0) {
                    GoodChickwichCount--;
                }

                GoodChickwichQuantity.setText(String.valueOf(GoodChickwichCount));
            }
        });

        order.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                // SEND STRING TO CHEF
                fullOrder = "Table: " + getIntent().getExtras().getString("tableChosen") + "\n"
                        + "Good Meals: " + String.valueOf(GoodMealCount) + "\n"
                        + "Good Chunks: " + String.valueOf(GoodMealCount) + "\n"
                        + "Good Chickwich: " + String.valueOf(GoodMealCount) + "\n"
                        + "Special Order" + "\n"
                        + specialOrder.getText() + "\n";

                try{
                    DatagramSocket s = new DatagramSocket();
                    InetAddress local = InetAddress.getByName("192.168.1.102"); // TODO: REPLACE WITH REAL IP ADDRESS
                    msg_length= fullOrder.length();
                    message = fullOrder.getBytes();
                    DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
                    s.send(p);
                }catch (Exception e) {
                    System.out.println("Unable to Connect");
                }

                // TELL ARDUINO TO RETURN

                try {
                    //bluetooth_connect_device();
                    travel();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                // Start New Screen
                Intent myIntent = new Intent(TakeOrder.this, Traveller.class);
                myIntent.putExtra("takeORdeliver", "2"); // Reset
                TakeOrder.this.startActivity(myIntent);
            }
        });
    }


    private void initialize(){

        GoodMealCount = 0;
        GoodChunksCount = 0;
        GoodChickwichCount = 0;

        addGoodMeal = findViewById(R.id.bAddGoodMeal);
        subGoodMeal = findViewById(R.id.bSubGoodMeal);
        addGoodChunks = findViewById(R.id.bAddGoodChunks);
        subGoodChunks = findViewById(R.id.bSubGoodChunks);
        addGoodChickwich = findViewById(R.id.bAddGoodChickwich);
        subGoodChickwich = findViewById(R.id.bSubGoodChickwich);

        order = findViewById(R.id.bOrder);

        specialOrder = findViewById(R.id.etSpecialOrder);

        GoodMealQuantity = findViewById(R.id.tvGoodMealQuantity);
        GoodChunksQuantity = findViewById(R.id.tvGoodChunksQuantity);
        GoodChickwichQuantity = findViewById(R.id.tvGoodChickwichQuantity);
        tableNumber = findViewById(R.id.tvTableNumber);

        specialOrder = findViewById(R.id.etSpecialOrder);

        tableNumber.setText(getIntent().getExtras().getString("tableChosen"));

        try {bluetooth_connect_device();}
        catch (Exception e){}
    }

    // Return Home
    private void travel()
    {
        try
        {
            if (btSocket!=null)
            {
                btSocket.getOutputStream().write(homeKey);
                btSocket.getOutputStream().write(command);
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
