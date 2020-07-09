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

    private static final int ID_TEXTO_PARA_VOZ = 100;

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

                    Toast.makeText(getApplicationContext(), "Fala: " + texto, Toast.LENGTH_LONG).show();

                    textViewTextoEnviado.setText(texto);

                    String start = "start";

                    if (texto.equals(start)) {
                        MainActivity.connectedThread.enviar("l");
                        Toast.makeText(getApplicationContext(), "Sao iguais: " + texto + " = " + start, Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }


}