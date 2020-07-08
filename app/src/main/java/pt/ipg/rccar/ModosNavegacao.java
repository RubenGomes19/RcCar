package pt.ipg.rccar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ModosNavegacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modos_navegacao);
    }


    public void atividadeModoAutonomo(View view) {

        Intent intent = new Intent(this, ModoAutonomo.class);

        startActivity(intent);
    }
}