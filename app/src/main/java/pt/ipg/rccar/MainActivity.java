package pt.ipg.rccar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity {

    Button btnConexao;
    ImageView bt;
    ConstraintLayout layout;


    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;
    public static final int MESSAGE_READ = 3;

    static ConnectedThread connectedThread;

    static Handler mhandler;

    static int mensagem = 0;

    StringBuilder dadosBluetooth = new StringBuilder();


    BluetoothAdapter meuBluetoothAdapter = null;
    BluetoothDevice meuDevice = null;
    BluetoothSocket meuSocket = null;

    static boolean conexao = false;
    private static String MAC = null;

    static String recebidos;

    private int menuAtual = R.menu.menu;

    int estado_conexao = 0;


    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //UUID- Universally unique identifier

    //ISTO É PARA APAGAR! A DE BAIXO E QUE ESTA BEM, E SO TESTE
    public void atividadeModo(View view) {
        Intent intent = new Intent(this, ModosNavegacao.class);

        startActivity(intent);

        estado_conexao = 1;
        /*Intent it = new Intent(MainActivity.this, ModosNavegacao.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(it);

         */
    }


    public void atividadeModos(View view) {

        if(conexao){
            Intent intent = new Intent(this, ModosNavegacao.class);

            startActivity(intent);
        }else{
            //Toast.makeText(getApplicationContext(), "Estabeleça uma conexão primeiro!", Toast.LENGTH_SHORT).show();
            /*AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Title");

            adb.setPositiveButton("Ok", null);
            adb.show();*/


            Snackbar snackbar = Snackbar.make(layout, "Estabeleça uma conexão primeiro", Snackbar.LENGTH_LONG);
            snackbar.show();
        }





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.myToolBar);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        setSupportActionBar(toolbar);


        //Toast.makeText(getApplicationContext(), "Pagina inicial: Bluetooth", Toast.LENGTH_SHORT).show();

        btnConexao = (Button) findViewById(R.id.btnConexao);
        //btnLed1 = (Button) findViewById(R.id.btnLed1);
        layout = (ConstraintLayout) findViewById(R.id.main);
        bt = (ImageView) findViewById(R.id.imageViewBt);


        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if(meuBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Este disposivo não possui bluetooth", Toast.LENGTH_SHORT).show();
        }else if(!meuBluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }


        if(estado_conexao == 1){
            btnConexao.setBackgroundColor(Color.parseColor("#B62E2E"));
            bt.setBackgroundColor(Color.parseColor("#B62E2E"));
            bt.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
        }

        btnConexao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(conexao){
                    //desconectar
                    try {
                        meuSocket.close();
                        conexao = false;
                        estado_conexao = 0;

                        if(estado_conexao == 0) {
                            btnConexao.setText("Conectar");
                            btnConexao.setBackgroundColor(Color.parseColor("#ED3F60B5"));
                            //Toast.makeText(getApplicationContext(), "Bluetooh desconectado", Toast.LENGTH_LONG).show();
                            bt.setBackgroundColor(Color.parseColor("#ED3F60B5"));
                            bt.setImageResource(R.drawable.ic_baseline_bluetooth_24);

                            MainActivity.connectedThread.enviar("s");

                            Snackbar snackbar = Snackbar.make(layout, "HC-06 desconectado", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }catch (IOException erro){
                        //Toast.makeText(getApplicationContext(), "Ocorreu um erro: " + erro, Toast.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar.make(layout, "Ocorreu um erro.", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }

                }else{
                    //conectar
                    Intent abreLista = new Intent(MainActivity.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO);

                }

            }
        });


        mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == MESSAGE_READ){
                    recebidos = (String) msg.obj;

                    //dadosBluetooth.append(recebidos);

                    /*int fimInformacao = dadosBluetooth.indexOf("}");

                    if(fimInformacao > 0){

                        String dadosCompletos = dadosBluetooth.substring(0, fimInformacao);

                        int tamInformacao = dadosCompletos.length();

                        if(dadosBluetooth.charAt(0) == '{') {
                            //{Sostaculoobstaculo}

                            String dadosFinais = dadosBluetooth.substring(1, tamInformacao);

                            Log.d("Recebidos", dadosFinais);

                            if (dadosFinais.contains("obstaculo")) {
                                Toast.makeText(getApplicationContext(), "Obstaculo", Toast.LENGTH_LONG).show();
                                mensagem = 1;

                            }

                            if (dadosFinais.contains("Sobstaculo")) {
                                Toast.makeText(getApplicationContext(), "Livre", Toast.LENGTH_LONG).show();
                                mensagem = 0;

                            }

                            if (dadosFinais.contains("ativo")) {
                                Toast.makeText(getApplicationContext(), "Modo autonomo ativc TESTE", Toast.LENGTH_LONG).show();
                                mensagem = 2;

                            }


                        }
                        dadosBluetooth.delete(0,dadosBluetooth.length());


                    }*/



                    if(recebidos.contains("0")){
                        //Toast.makeText(getApplicationContext(), "Recebido:" + recebidos , Toast.LENGTH_LONG).show();
                        mensagem = 0;

                    }
                    if(recebidos.contains("1")){
                        //Toast.makeText(getApplicationContext(), "Recebido:" + recebidos , Toast.LENGTH_LONG).show();
                        mensagem = 1;

                    }
                    if(recebidos.contains("2")){
                        //Toast.makeText(getApplicationContext(), "Recebido:" + recebidos , Toast.LENGTH_LONG).show();
                        mensagem = 2;

                    }




                    //recebidos = "";
                    //mensagem = 0;






                    /*if(recebidos.contains("Sobstaculo")){
                        //Toast.makeText(getApplicationContext(), "Obstaculo" , Toast.LENGTH_LONG).show();
                        mensagem = 0;

                    }
                    if(recebidos.contains("obstaculo")){
                        Toast.makeText(getApplicationContext(), "Obstaculo" , Toast.LENGTH_LONG).show();
                        mensagem = 1;

                        Log.d("Recebidos", recebidos);
                    }*/
                    //mensagem = 0;





                }

            }


        };


        /*btnLed1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(conexao){
                            connectedThread.enviar("l");
                        }else{
                            Toast.makeText(getApplicationContext(), "Bluetooth não está conectado", Toast.LENGTH_LONG).show();
                        }



            }
        });

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
             AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Definições");
            adb.setMessage("Estabeleça uma conexão primeiro \n" + "Desenvolvido por Rúben Gomes");

            adb.setPositiveButton("Ok", null);
            adb.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {

            case SOLICITA_ATIVACAO:
                if(resultCode == Activity.RESULT_OK){
                    //Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado!", Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(layout, "Bluetooth ligado", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Definições");
                    adb.setMessage("O Bluetooth não foi ativado, a app será encerrada!");

                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    //Toast.makeText(getApplicationContext(), "O Bluetooth não foi ativado, a app será encerrada!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            case SOLICITA_CONEXAO:
                if(resultCode == Activity.RESULT_OK){



                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);

                    //Toast.makeText(getApplicationContext(), "MAC FINAL: " + MAC, Toast.LENGTH_LONG).show();
                    meuDevice = meuBluetoothAdapter.getRemoteDevice(MAC);




                        /*ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setTitle("A Conectar...");
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
                        progressDialog.show();*/




                    try {


                        meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID);

                        meuSocket.connect();

                        conexao = true;
                        estado_conexao = 1;

                        connectedThread = new ConnectedThread(meuSocket);
                        connectedThread.start();

                        if(estado_conexao == 1 ){
                            btnConexao.setText("Desconectar \n" + "'HC-06: " + MAC + "'");
                            btnConexao.setBackgroundColor(Color.parseColor("#B62E2E"));
                            bt.setBackgroundColor(Color.parseColor("#B62E2E"));
                            bt.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
                            Snackbar snackbar = Snackbar.make(layout, "HC-06 conectado", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                        //connectedThread.enviar("T");



                        //Toast.makeText(getApplicationContext(), "Conectado com: " + MAC , Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        conexao = false;

                        //Toast.makeText(getApplicationContext(), "Ocorreu um erro: " + erro, Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Ocorreu um erro a conectar, tente novamente!", Toast.LENGTH_SHORT).show();
                        Snackbar snackbar = Snackbar.make(layout, "Ocorreu um erro a conectar, tente novamente", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }else{
                    //Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG).show();
                    Snackbar snackbar = Snackbar.make(layout, "Falha ao obter o MAC.", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
        }

    }

    class ConnectedThread extends Thread {

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream


        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.

            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);

                    String dadosBt = new String(mmBuffer, 0, numBytes);


                    // Send the obtained bytes to the UI activity.
                    /*Message readMsg = handler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();*/

                    mhandler.obtainMessage(MESSAGE_READ, numBytes, -1, dadosBt).sendToTarget();


                } catch (IOException e) {
                    //Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }

        }

        // Call this from the main activity to send data to the remote device.
        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);

                // Share the sent message with the UI activity.
                /*Message writtenMsg = handler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();*/
            } catch (IOException e) {
               // Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                /*Message writeErrorMsg =
                        handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);*/
            }
        }
    }



}