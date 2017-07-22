package com.vitor.tarefas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.vitor.tarefas.models.Tarefa;
import com.vitor.tarefas.models.TarefaAdaper;
import com.vitor.tarefas.util.HttpConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView lvTarefas;
    private List<Tarefa> tarefas;
    private Tarefa tarefa;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
//        }

        try {
            List<Tarefa>  tarefas = HttpConnection.getTarefas();
            for (Tarefa t : tarefas) {
                Log.i("Script", "ANSWER <-> vitor: "+t.toString());
            }

            lvTarefas = (ListView) findViewById(R.id.listaTarefas);

        } catch (IOException e) {
            Log.i("Script", "ANSWER <-> vitor: DEU RUIMMM");
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            tarefas = HttpConnection.getTarefas();

            TarefaAdaper tarefaAdp = new TarefaAdaper(this, tarefas);
            lvTarefas.setAdapter(tarefaAdp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void abrirCamera(Tarefa tarefa) {
        this.tarefa = tarefa;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);

//        InputStream stream = null;
//
//        if (bitmap != null) {
//            bitmap.recycle();
//        }
//
//        try {
//            stream = getContentResolver().openInputStream(intent.getData());
//            bitmap = BitmapFactory.decodeStream(stream);
//            int tam = bitmap.getRowBytes() * bitmap.getHeight();
//            ByteBuffer b = ByteBuffer.allocate(tam);
//
//            bitmap.copyPixelsToBuffer(b);
//
//            byte[] bytes = new byte[tam];
//            try {
//                b.get(bytes, 0, bytes.length);
//            } catch (BufferUnderflowException e) {
//                e.printStackTrace();
//            }
//
//            tarefa.setFoto(bytes);
//
//            Log.i("Script", "ANSWER <-> vitor dentro do abrirCamera: " + tarefa.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    protected void onActivityResult(int requestCode, int resulCode, Intent data) {
        super.onActivityResult(requestCode, resulCode, data);

        InputStream stream = null;
//
        if (requestCode == 0 && resulCode == RESULT_OK) {
//            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }
//

                bitmap = (Bitmap) data.getExtras().get("data");

//                stream = getContentResolver().openInputStream(data.getData());
//                bitmap = BitmapFactory.decodeStream(stream);
                int tam = bitmap.getRowBytes() * bitmap.getHeight();
                byte[] bytes = new byte[tam];
//
                ByteBuffer b = ByteBuffer.allocate(tam);
////
                bitmap.copyPixelsToBuffer(b);

                try {
                    b.get(bytes, 0, bytes.length);
                } catch (BufferUnderflowException e) {
                    e.printStackTrace();
                }
//
                tarefa.setFoto(bytes);
//
                Log.i("Script", "ANSWER <-> vitor dentro do abrirCamera: " + tarefa.toString());
                try {
                    HttpConnection.enviaTarefa(tarefa);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//
//
//            } catch (FileNotFoundException e) {
//
//            }
        }
    }

    public void atualizaPagina(View view) {
        try {
            tarefas = HttpConnection.getTarefas();

            TarefaAdaper tarefaAdp = new TarefaAdaper(this, tarefas);
            lvTarefas.setAdapter(tarefaAdp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    private void callServer(final String method, final String data) {
//        new Thread(){
//            public void run(){
//
//                String answer = HttpConnection.getSetDataWeb("localhost:8080/tarefas", method, data);
//
//
//                Log.i("Script", "ANSWER - vitor: "+answer);
//            }
//        }.start();
//    }

}
