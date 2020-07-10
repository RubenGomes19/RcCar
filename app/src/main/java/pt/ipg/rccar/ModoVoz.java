package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ModoVoz extends AppCompatActivity {

    Button buttonVoz;
    TextView textViewTextoEnviado;
    private int controla = 0;

    private static final int ID_TEXTO_PARA_VOZ = 100;



    public void atividadeModos(View view) {

        Intent intent = new Intent(this, ModosNavegacao.class);

        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_voz);

        buttonVoz = (Button) findViewById(R.id.buttonVoz);
        textViewTextoEnviado = (TextView) findViewById(R.id.textViewTextoEnviado);

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
                    Toast.makeText(getApplicationContext(), "Este Dispositivo não suporta comando por voz!", Toast.LENGTH_LONG).show();
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

                    textViewTextoEnviado.setText("Clique no botão e fale \"INICIAR\" para iniciar ou \"PARAR\" para parar." + "/n" + "Fala recolhida: " + texto);

                    String start = "iniciar";
                    String stop = "parar";

                    if (texto.equals(start) && MainActivity.conexao && controla == 0) {
                        MainActivity.connectedThread.enviar("l");
                        controla = 1;
                        //Toast.makeText(getApplicationContext(), "Sao iguais: " + texto + " = " + start, Toast.LENGTH_LONG).show();
                    }else if(texto.equals(stop) && MainActivity.conexao && controla == 1){
                        MainActivity.connectedThread.enviar("l");
                        controla = 0;

                    }else if(texto != start && MainActivity.conexao){
                        Toast.makeText(getApplicationContext(), "" + texto + " não tem nenhum comando associado!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Não está conectado" , Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }


}