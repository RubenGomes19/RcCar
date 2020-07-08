package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ModoAutonomo extends AppCompatActivity {
    Button btnLed2;
    private int colorFlag = 0;

    //MainActivity.ConnectedThread connectedThread;

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

                if (MainActivity.conexao && colorFlag == 0) {
                    MainActivity.connectedThread.enviar("l");
                    Toast.makeText(getApplicationContext(), "Led ligado, flag: " + colorFlag, Toast.LENGTH_SHORT).show();
                    btnLed2.setText("Desligar Led");
                    btnLed2.setBackgroundColor(Color.parseColor("#B62E2E"));
                    colorFlag = 1;
                } else if (MainActivity.conexao && colorFlag == 1) {
                    MainActivity.connectedThread.enviar("l");
                    Toast.makeText(getApplicationContext(), "Led desligado, flag: " + colorFlag, Toast.LENGTH_SHORT).show();
                    btnLed2.setText("Ligar Led");
                    //btnLed2.setBackgroundColor( );
                    btnLed2.setBackgroundColor(Color.parseColor("#4CAF50"));
                    colorFlag = 0;
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não esta conectado", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

}