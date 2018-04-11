package fullerton.edu.autowaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;


// Choose Table
public class MainActivity extends AppCompatActivity {

    /*
  x  1. choose table
  x  2. deliver or take order
  x  3. travel (E-Stop button)
  x  4a. deliver
  x  5a. arrive at table and stay there until they say retreat
    4b. take order
    5b. arrive at table and stay until they say retreat.
     */

    Button table1;
    Button table2;
    Button table3;
    Button table4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        table1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, TakeDeliver.class);
                myIntent.putExtra("tableChosen", "1"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

        table2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, TakeDeliver.class);
                myIntent.putExtra("tableChosen", "2"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

        table3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, TakeDeliver.class);
                myIntent.putExtra("tableChosen", "3"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

        table4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, TakeDeliver.class);
                myIntent.putExtra("tableChosen", "4"); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

    private void initialize(){
        table1 = findViewById(R.id.bTable1);
        table2 = findViewById(R.id.bTable2);
        table3 = findViewById(R.id.bTable3);
        table4 = findViewById(R.id.bTable4);
    }
}
