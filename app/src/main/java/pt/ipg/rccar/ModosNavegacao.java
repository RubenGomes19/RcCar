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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ModosNavegacao extends AppCompatActivity {
    private int menuAtual = R.menu.menu_modos;
    Button btnConexao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modos_navegacao);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        btnConexao = (Button) findViewById(R.id.btnConexao);

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);



    }


    public void atividadeModoAutonomo(View view) {

        Intent intent = new Intent(this, ModoAutonomo.class);

        startActivity(intent);
        finish();
    }

    public void atividadeModoManual(View view) {

        Intent intent = new Intent(this, ModoManual.class);

        startActivity(intent);
        finish();

    }

    public void atividadeModoVoz(View view) {

        Intent intent = new Intent(this, ModoVoz.class);

        startActivity(intent);
        finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuAtual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.definicoes) {
            AlertDialog.Builder adb=new AlertDialog.Builder(ModosNavegacao.this);
            adb.setTitle("Definições");
            adb.setMessage("Escolha um modo \n" + "Desenvolvido por Rúben Gomes");

            adb.setPositiveButton("Ok", null);
            adb.show();
        }else if(id == R.id.inicio){
            finish();

            /*Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);


            Intent it = new Intent(ModosNavegacao.this, MainActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(it);*/
        }

        return super.onOptionsItemSelected(item);
    }
}