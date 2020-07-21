package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InterruptedIOException;

public class ModoAutonomo extends AppCompatActivity {
    Button btnLed2, buttonPress;
    TextView textView4;
    private int colorFlag = 0;
    int estado_botao = 0;
    private boolean modo_autonomo = false;
    int count;



    private Chronometer ch;

    //private static final int ID_TEXTO_PARA_VOZ = 100;

    private int menuAtual = R.menu.modos;



    /*public void start() {
        if (!modo_autonomo){
            ch.setBase(SystemClock.elapsedRealtime());
            ch.start();
            MainActivity.connectedThread.enviar("a");
            modo_autonomo = true;
        }

    }

    public void stop(){
        if (modo_autonomo) {
            MainActivity.connectedThread.enviar("b");
            ch.stop();
            modo_autonomo = false;
        }
    }*/

    public void atividadeModos(View view) {

        Intent intent = new Intent(this, ModosNavegacao.class);

        startActivity(intent);

    }


    Thread t = new Thread(){
        @Override
        public void run() {
            while (!isInterrupted()){
                if(!modo_autonomo){
                    return;
                }
                try{
                    Thread.sleep(200);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //count++;

                            //textView4.setText(String.valueOf(count));
                            MainActivity.connectedThread.enviar("a");


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_autonomo);

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        btnLed2 = (Button) findViewById(R.id.btnLed2);
        //buttonPress = (Button) findViewById(R.id.buttonPress);
        textView4 = (TextView) findViewById(R.id.textView4);

        ch = (Chronometer) findViewById(R.id.Chronometer);



        btnLed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (MainActivity.conexao && colorFlag == 0) {
                    modo_autonomo = true;

                    Toast.makeText(getApplicationContext(), "Modo autonomo ativado", Toast.LENGTH_SHORT).show();
                    btnLed2.setBackgroundResource(R.drawable.stop);
                    //modo_autonomo = true;

                    //MainActivity.connectedThread.enviar("a");
                    //start();

                    t.start();
                    ch.setBase(SystemClock.elapsedRealtime());
                    ch.start();

                    colorFlag = 1;

                    //btnLed2.setText("Desligar Led");
                    //btnLed2.setBackgroundColor(Color.parseColor("#B62E2E"));



                } else if (MainActivity.conexao && colorFlag == 1) {
                    //modo_autonomo = false;
                    //MainActivity.connectedThread.enviar("b");
                    //Toast.makeText(getApplicationContext(), "Led desligado", Toast.LENGTH_SHORT).show();
                    //btnLed2.setBackgroundResource(R.drawable.start);
                    //stop();

                    modo_autonomo = false;
                    t.interrupt();
                    Toast.makeText(getApplicationContext(), "Modo autonomo desativado", Toast.LENGTH_SHORT).show();
                    btnLed2.setBackgroundResource(R.drawable.start);
                    MainActivity.connectedThread.enviar("b");
                    colorFlag = 0;
                    ch.stop();
                    recreate();

                    //btnLed2.setText("Ligar Led");
                    //btnLed2.setBackgroundColor(Color.parseColor("#4CAF50"));

                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não esta conectado", Toast.LENGTH_LONG).show();
                }
            }

        });





      /*
       buttonPress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent iVoz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

               iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

               iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

               iVoz.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale algo");

               try{
                   startActivityForResult(iVoz, ID_TEXTO_PARA_VOZ);
               }catch (ActivityNotFoundException a){
                   Toast.makeText(getApplicationContext(), "Este Dispositivo não suporta comando por voz!", Toast.LENGTH_LONG).show();
               }
            }
       });

       */







        /*buttonPress.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.conexao && colorFlag == 0){

                        MainActivity.connectedThread.enviar("l");
                        Toast.makeText(getApplicationContext(), "Pressionado", Toast.LENGTH_SHORT).show();
                        buttonPress.setText("STOP");
                        buttonPress.setBackgroundColor(Color.parseColor("#B62E2E"));
                        colorFlag = 1;
                    }
                }

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.conexao && colorFlag == 1){
                        MainActivity.connectedThread.enviar("l");
                        Toast.makeText(getApplicationContext(), "Nao pressionado", Toast.LENGTH_SHORT).show();
                        buttonPress.setText("Pressionar");
                        //btnLed2.setBackgroundColor( );
                        buttonPress.setBackgroundColor(Color.parseColor("#4CAF50"));
                        colorFlag = 0;

                    }
                }

                return false;				  					  // Retorna false o onTouch
            }
        });

         */

/*
        buttonPress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (MainActivity.conexao && colorFlag == 0) {
                    MainActivity.connectedThread.enviar("l");
                    Toast.makeText(getApplicationContext(), "Led ligado", Toast.LENGTH_SHORT).show();
                    buttonPress.setText("Desligar Led");
                    buttonPress.setBackgroundColor(Color.parseColor("#B62E2E"));
                    colorFlag = 1;
                } else if (MainActivity.conexao && colorFlag == 1) {
                    MainActivity.connectedThread.enviar("l");
                    Toast.makeText(getApplicationContext(), "Led desligado", Toast.LENGTH_SHORT).show();
                    buttonPress.setText("Ligar Led");
                    //btnLed2.setBackgroundColor( );
                    buttonPress.setBackgroundColor(Color.parseColor("#4CAF50"));
                    colorFlag = 0;
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não esta conectado", Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });
*/

    }


    public void enviar(){
        //colorFlag = 1;

        MainActivity.connectedThread.enviar("a");


        /*while (modo_autonomo = true){
            MainActivity.connectedThread.enviar("a");
            break;

        }*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuAtual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.definicoes) {
            AlertDialog.Builder adb=new AlertDialog.Builder(ModoAutonomo.this);
            adb.setTitle("Definições");
            adb.setMessage("Modo Autonomo \n" + "Desenvolvido por Rúben Gomes");

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

/*
    @Override
    protected void onActivityResult(int id, int resultCodeID, Intent dados) {
        super.onActivityResult(id, resultCodeID, dados);

        switch (id){
            case ID_TEXTO_PARA_VOZ:
                if(resultCodeID == RESULT_OK && null != dados){
                    ArrayList<String> result = dados.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String texto = result.get(0);

                    Toast.makeText(getApplicationContext(), "Fala: " + texto, Toast.LENGTH_LONG).show();

                    textViewTexto.setText(texto);

                    String start = "start";

                    if(texto.equals(start) ){
                        MainActivity.connectedThread.enviar("l");
                        Toast.makeText(getApplicationContext(), "Sao iguais: " + texto + " = " + start, Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }


    }

 */


}