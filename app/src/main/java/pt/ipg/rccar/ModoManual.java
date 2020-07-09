package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModoManual extends AppCompatActivity {
    Button buttonX;

    public void atividadeModos(View view) {

        Intent intent = new Intent(this, ModosNavegacao.class);

        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_manual);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        buttonX = (Button) findViewById(R.id.buttonX);


        buttonX.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("l");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();

                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("l");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();


                    }
                }

                return false;
            }
        });


        /*
        //GUARDAR PROGRESS BAR
        final ProgressDialog progressDialog = new ProgressDialog(this);
                            progressDialog.setTitle("A processar...");
                            progressDialog.setMessage("Aguarde por favor...");
                            progressDialog.setCancelable(false);
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    progressDialog.dismiss();
                                }
                            }).start();
                            progressDialog.show();



         */
    }
}