package fullerton.edu.autowaiter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Traveller extends AppCompatActivity {

    Button stop;
    Button arrived;
    TextView tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveller);
        initialize();

        final String tableChosen = getIntent().getExtras().getString("tableChosen");
        final String command = getIntent().getExtras().getString("takeORdeliver");

        arrived.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                switch(command){
                    case "0": // Take Order
                        Intent myIntent0 = new Intent(Traveller.this, TakeOrder.class);
                        myIntent0.putExtra("tableChosen", tableChosen); //Optional parameters
                        Traveller.this.startActivity(myIntent0);
                        break;
                    case "1": // Deliver Order
                        Intent myIntent1 = new Intent(Traveller.this, DeliverOrder.class);
                        myIntent1.putExtra("tableChosen", tableChosen); //Optional parameters
                        Traveller.this.startActivity(myIntent1);
                        break;
                    case "2": // Return
                        Intent myIntent2 = new Intent(Traveller.this, MainActivity.class);
                        Traveller.this.startActivity(myIntent2);
                        break;
                    default:
                        break;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // TODO: SEND/RECIEVE STOP BIT TO ARDUINO
            }
        });
    }

    private void initialize(){
        stop = findViewById(R.id.bStop);
        arrived = findViewById(R.id.bArrived);
        tableNumber = findViewById(R.id.tvTableNumber);

        tableNumber.setText(getIntent().getExtras().getString("tableChosen"));
    }
}
