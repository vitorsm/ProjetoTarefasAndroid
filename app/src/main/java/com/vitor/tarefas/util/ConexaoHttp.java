package com.vitor.tarefas.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vitor.tarefas.models.Tarefa;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vitor on 21/07/17.
 */

public class ConexaoHttp {
    public void editarTarefa(Tarefa tarefa) {
        StringRequest post = new StringRequest(Request.Method.POST, "",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("Script", "bom");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Script", "Erro");
                    }

        }){
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nome", "Tarefa 11");
                params.put("codigo", "3");

                return params;
            }

//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> header = new HashMap<String, String>();
//                header.put()
//            }
        };
    }
}
