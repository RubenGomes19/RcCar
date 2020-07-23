package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModoManual extends AppCompatActivity {
    Button buttonX, buttonBola, buttonQuadrado, buttonTriangulo, buttonEsquerda, buttonDireita;

    private int menuAtual = R.menu.modos;

    public void atividadeModos(View view) {

        Intent intent = new Intent(this, ModosNavegacao.class);

        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_manual);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        buttonX = (Button) findViewById(R.id.buttonX);
        buttonBola = (Button) findViewById(R.id.buttonBola);
        buttonQuadrado = (Button) findViewById(R.id.buttonQuadrado);
        buttonTriangulo = (Button) findViewById(R.id.buttonTriangulo);
        buttonDireita = (Button) findViewById(R.id.buttonDireita);
        buttonEsquerda = (Button) findViewById(R.id.buttonEsquerda);



        buttonX.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("f");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonX.setBackgroundResource(R.drawable.x_click);



                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("f");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonX.setBackgroundResource(R.drawable.x);



                    }
                }

                return false;
            }
        });

        buttonTriangulo.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("t");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonTriangulo.setBackgroundResource(R.drawable.triangulo_click);

                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("t");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonTriangulo.setBackgroundResource(R.drawable.triangulo);


                    }
                }

                return false;
            }
        });

        buttonQuadrado.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("p");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonQuadrado.setBackgroundResource(R.drawable.quadrado_click);
                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("p");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonQuadrado.setBackgroundResource(R.drawable.quadrado);

                    }
                }

                return false;
            }
        });

        buttonBola.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("s");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonBola.setBackgroundResource(R.drawable.bola_click);
                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("s");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonBola.setBackgroundResource(R.drawable.bola);

                    }
                }

                return false;
            }
        });


        buttonEsquerda.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("e");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonEsquerda.setBackgroundResource(R.drawable.s_esquerda_click);
                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("e");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonEsquerda.setBackgroundResource(R.drawable.s_esquerda);


                    }
                }

                return false;
            }
        });


        buttonDireita.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao){

                        MainActivity.connectedThread.enviar("d");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonDireita.setBackgroundResource(R.drawable.s_direita_click);

                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao ){
                        MainActivity.connectedThread.enviar("d");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonDireita.setBackgroundResource(R.drawable.s_direita);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuAtual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.definicoes) {
            AlertDialog.Builder adb=new AlertDialog.Builder(ModoManual.this);
            adb.setTitle("Definições");
            adb.setMessage("Modo Manual \n" + "Desenvolvido por Rúben Gomes");

            adb.setPositiveButton("Ok", null);
            adb.show();
        }else if(id == R.id.inicio){
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }else if(id == R.id.mudar){
            Intent intent = new Intent(this, ModosNavegacao.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}