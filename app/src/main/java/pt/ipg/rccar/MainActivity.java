package pt.ipg.rccar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnConexao, btnLed1;
    private static final int SOLICITA_ATIVACAO = 1;
    BluetoothAdapter meuBluetoothAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Pagina inicial: Bluetooth", Toast.LENGTH_SHORT).show();

        btnConexao = (Button) findViewById(R.id.btnConexao);
        btnLed1 = (Button) findViewById(R.id.btnLed1);

        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(meuBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Este disposivo não possui bluetooth", Toast.LENGTH_SHORT);
        }else if(!meuBluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case SOLICITA_ATIVACAO:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "O Bluetooth não foi ativado, a app será encerrada!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }

    }
}