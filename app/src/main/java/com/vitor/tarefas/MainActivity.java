package com.vitor.tarefas;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


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
    }

    protected void onActivityResult(int requestCode, int resulCode, Intent data) {
        super.onActivityResult(requestCode, resulCode, data);

        InputStream stream = null;

        if (requestCode == 0 && resulCode == RESULT_OK) {
            if (bitmap != null) {
                bitmap.recycle();
            }

           bitmap = (Bitmap) data.getExtras().get("data");


            ByteArrayOutputStream s = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 500, s);
            byte[] bytes = s.toByteArray();

            tarefa.setFoto(bytes);

            try {
                HttpConnection.enviaTarefa(tarefa);
            } catch (IOException e) {
                e.printStackTrace();
            }

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

}
