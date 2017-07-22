package com.vitor.tarefas.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vitor.tarefas.MainActivity;
import com.vitor.tarefas.R;

import java.util.List;

/**
 * Created by vitor on 20/07/17.
 */

public class TarefaAdaper extends BaseAdapter {
    private List<Tarefa> tarefas;
    private Context context;
    private Tarefa tarefa;

    public TarefaAdaper(Context context, List<Tarefa> tarefas) {
        this.context = context;
        this.tarefas = tarefas;
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int i) {
        return tarefas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tarefas.get(i).getCodigo();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.lista_tarefa, viewGroup, false);
        tarefa = tarefas.get(i);

        TextView tvNome = (TextView) rootView.findViewById(R.id.tNmTarefa);
        Button btFazer = (Button) rootView.findViewById(R.id.button);
        btFazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity m = (MainActivity) context;
                m.abrirCamera(tarefas.get(i));
            }
        });



        tvNome.setText(tarefa.getNome());

        return rootView;
    }
}
