package com.vitor.tarefas.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.vitor.tarefas.MainActivity;
import com.vitor.tarefas.models.Tarefa;

import java.util.List;

/**
 * Created by vitor on 20/07/17.
 */

public class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<Tarefa>> {

    ProgressDialog dialog;

    @Override
    protected List<Tarefa> doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
