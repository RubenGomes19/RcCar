package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModoAutonomo extends AppCompatActivity {
    Button btnLed2;
    private int colorFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_autonomo);

        btnLed2 = (Button) findViewById(R.id.btnLed2);



        btnLed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Intent intent = new Intent(ModoAutonomo.this, MainActivity.class);
              // Toast.makeText(getApplicationContext(), "Ligar led", Toast.LENGTH_LONG).show();
               // btnLed2.setText("Desligar Led");
                //btnLed2.setBackgroundColor( );
               // btnLed2.setBackgroundColor(Color.parseColor("#B62E2E"));
                //startActivity(intent);
               //finish();

                if (colorFlag == 0) {
                    Toast.makeText(getApplicationContext(), "Led ligado", Toast.LENGTH_SHORT).show();
                    btnLed2.setText("Desligar Led");
                    btnLed2.setBackgroundColor(Color.parseColor("#B62E2E"));
                    colorFlag = 1;
                } else {
                    Toast.makeText(getApplicationContext(), "Led desligado", Toast.LENGTH_SHORT).show();
                    btnLed2.setText("Ligar Led");
                    //btnLed2.setBackgroundColor( );
                    btnLed2.setBackgroundColor(Color.parseColor("#4CAF50"));
                    colorFlag = 0;
                }
            }

        });



    }




}