package fullerton.edu.autowaiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeliverOrder extends AppCompatActivity {

    Button returner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_order);

        initialize();

        returner.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntent = new Intent(DeliverOrder.this, MainActivity.class);
                DeliverOrder.this.startActivity(myIntent);
            }
        });
    }
    private void initialize() {
        returner = findViewById(R.id.bReturn);
    }
}
