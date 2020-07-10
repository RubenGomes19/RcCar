package pt.ipg.rccar;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Set;

public class ListaDispositivos extends ListActivity {

    private BluetoothAdapter meuBluetoothAdapter2 = null;

    static String ENDERECO_MAC = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        meuBluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> dispositivosEmparelhados = meuBluetoothAdapter2.getBondedDevices();

        if(dispositivosEmparelhados.size() > 0){
            for(BluetoothDevice dispositivos : dispositivosEmparelhados){
                String nomeBt = dispositivos.getName();
                String macBt = dispositivos.getAddress();
                ArrayBluetooth.add(nomeBt + "\n" + macBt);

            }
        }
        setListAdapter(ArrayBluetooth);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();

        //Toast.makeText(getApplicationContext(), "Info: " + informacaoGeral, Toast.LENGTH_LONG).show();

        String enderecoMac = informacaoGeral.substring(informacaoGeral.length() - 17);
        //Toast.makeText(getApplicationContext(), "mac: " + enderecoMac, Toast.LENGTH_SHORT).show();

        Intent retornaMac = new Intent();
        retornaMac.putExtra(ENDERECO_MAC, enderecoMac);
        setResult(RESULT_OK, retornaMac);


        finish();


        /*Intent nomeBt = new Intent();
        nomeBt.putExtra(NOME_BT, informacaoGeral);
        setResult(RESULT_OK);
        finish();*/

    }
}
