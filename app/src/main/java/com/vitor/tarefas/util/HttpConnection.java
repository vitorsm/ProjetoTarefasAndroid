package com.vitor.tarefas.util;

import android.util.Base64;

import com.vitor.tarefas.models.Tarefa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class HttpConnection {

    public static void enviaTarefa(Tarefa tarefa) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpost = new HttpPost("http://192.168.15.17:8080/editaTarefa");

        JSONObject holder = tarefaToJson(tarefa);

        String dadosString = holder.toString();

        StringEntity se = new StringEntity(dadosString);
        httpost.setEntity(se);

        httpost.setHeader("Accept", "application/json");
        httpost.setHeader("Content-type", "application/json");

        ResponseHandler responseHandler = new BasicResponseHandler();
        httpclient.execute(httpost);
    }

	public static List<Tarefa> getTarefas() throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse resposta = httpClient.execute(new HttpGet("http://192.168.15.17:8080/tarefasPend"));
		BufferedReader br = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent(), "UTF-8"));

		String respJSON = br.readLine();

		List<Tarefa> tarefas = new ArrayList<Tarefa>();

		try{
            JSONArray jsonTarefas = new JSONArray(respJSON);

            for (int i = 0; i < jsonTarefas.length(); i++) {
                JSONObject json = jsonTarefas.getJSONObject(i);
                tarefas.add(jsonToTarefa(json));
            }

		}
		catch(NullPointerException e){ e.printStackTrace(); }
        catch(JSONException e) {e.printStackTrace();}


		return(tarefas);
	}

	public static Tarefa jsonToTarefa(JSONObject jo) {
        Tarefa tarefa = new Tarefa();
        try {
            tarefa.setCodigo(jo.getInt("codigo"));
            tarefa.setNome((String) jo.get("nome"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tarefa;
    }

    public static JSONObject tarefaToJson(Tarefa tarefa) {
        JSONObject j = new JSONObject();
        try {

            j.put("codigo", tarefa.getCodigo());
            j.put("foto", encoder64(tarefa.getFoto()));
            j.put("nome", tarefa.getNome());
            j.put("localizacao", tarefa.getLocalizacao());
            j.put("status", 2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return j;
    }

    public static String encoder64(byte[] foto) {

        return Base64.encodeToString(foto, Base64.DEFAULT);
    }
}
