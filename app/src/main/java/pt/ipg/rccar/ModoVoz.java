package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

public class ModoVoz extends AppCompatActivity {

    Button buttonVoz;
    TextView textViewTextoEnviado,  textViewTextoEstado, textViewObstaculoVoz;
    ConstraintLayout layout;
    private int controla = 0;

    private boolean modo_autonomo = false;

    private static final int ID_TEXTO_PARA_VOZ = 100;

    private int menuAtual = R.menu.modos;


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
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //count++;

                            //textView4.setText(String.valueOf(count));
                            MainActivity.connectedThread.enviar("a");

                            if(MainActivity.mensagem == 1){
                                textViewObstaculoVoz.setText("Obstaculo a menos de 50cm, efetuando manobra");

                            }else{
                                textViewObstaculoVoz.setText("Livre");


                            }


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
        setContentView(R.layout.activity_modo_voz);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        buttonVoz = (Button) findViewById(R.id.buttonVoz);
        textViewTextoEnviado = (TextView) findViewById(R.id.textViewTextoEnviado);
        textViewTextoEstado = (TextView) findViewById(R.id.textViewEstado);
        textViewTextoEstado.setText("Modo por voz: Desativo");
        textViewObstaculoVoz = (TextView) findViewById(R.id.textViewObstaculoVoz);

        layout = (ConstraintLayout) findViewById(R.id.main);


        buttonVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVoz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                iVoz.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale algo");

                try{
                    startActivityForResult(iVoz, ID_TEXTO_PARA_VOZ);
                }catch (ActivityNotFoundException a){
                    //Toast.makeText(getApplicationContext(), "Este Dispositivo não suporta comando por voz!", Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(layout, "Este Dispositivo não suporta comando por voz!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int id, int resultCodeID, Intent dados) {
        super.onActivityResult(id, resultCodeID, dados);

        switch (id) {
            case ID_TEXTO_PARA_VOZ:
                if (resultCodeID == RESULT_OK && null != dados) {
                    ArrayList<String> result = dados.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String texto = result.get(0);

                    //Toast.makeText(getApplicationContext(), "Fala: " + texto, Toast.LENGTH_LONG).show();

                    textViewTextoEnviado.setText("Clique no botão e fale \"INICIAR\" para iniciar ou \"PARAR\" para parar. \n\n" + "Fala recolhida: " + "\"" + texto + "\"");

                    String start = "iniciar";
                    String stop = "parar";

                    if (texto.equals(start) && MainActivity.conexao && controla == 0) {
                        //MainActivity.connectedThread.enviar("a");
                        controla = 1;
                        //Toast.makeText(getApplicationContext(), "Modo autonomo ativado", Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar.make(layout, "Modo por voz ativo", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        textViewTextoEstado.setText("Modo por voz: Ativo");
                        modo_autonomo = true;
                        t.start();
                        //Toast.makeText(getApplicationContext(), "Sao iguais: " + texto + " = " + start, Toast.LENGTH_LONG).show();
                    }else if(texto.equals(stop) && MainActivity.conexao && controla == 1){
                        MainActivity.connectedThread.enviar("b");
                        controla = 0;
                        //Toast.makeText(getApplicationContext(), "Modo autonomo desativado", Toast.LENGTH_SHORT).show();

                        Snackbar snackbar = Snackbar.make(layout, "Modo por voz desativo", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        modo_autonomo = false;
                        textViewTextoEstado.setText("Modo por voz: Desativo");
                        t.interrupt();
                        recreate();

                    }else if(texto != start && MainActivity.conexao){
                        //Toast.makeText(getApplicationContext(), "" + texto + " não tem nenhum comando associado!", Toast.LENGTH_LONG).show();

                        Snackbar snackbar = Snackbar.make(layout, "\"" + texto + "\"" + " não tem nenhum comando associado!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        //Toast.makeText(getApplicationContext(), "Não está conectado" , Toast.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar.make(layout, "Não está conectado", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }

                }
                break;
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuAtual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.definicoes) {
            AlertDialog.Builder adb=new AlertDialog.Builder(ModoVoz.this);
            adb.setTitle("Definições");
            adb.setMessage("Modo Voz \n" + "Desenvolvido por Rúben Gomes");

            adb.setPositiveButton("Ok", null);
            adb.show();
        }else if(id == R.id.inicio){
            /*Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);*/
            if(controla == 1){
                //Toast.makeText(getApplicationContext(), "Desligue o modo por voz primeiro.", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(layout, "Desligue o modo autonomo primeiro.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }else {
                finish();
            }
        }else if(id == R.id.mudar){

            if(controla == 1){
                //Toast.makeText(getApplicationContext(), "Desligue o modo por voz primeiro.", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(layout, "Desligue o modo autonomo primeiro.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else {
                Intent intent = new Intent(this, ModosNavegacao.class);

                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}