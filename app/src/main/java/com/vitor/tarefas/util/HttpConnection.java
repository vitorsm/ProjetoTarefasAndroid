package com.vitor.tarefas.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import com.vitor.tarefas.models.Tarefa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class HttpConnection {

    public static void enviaTarefa(Tarefa tarefa) throws IOException {

        //instantiates httpclient to make request
        Log.i("Script", "ANSWER <-> vitor: foi 1");
        HttpClient httpclient = new DefaultHttpClient();

        Log.i("Script", "ANSWER <-> vitor: foi 2");
        //url with the post data
        HttpPost httpost = new HttpPost("http://192.168.15.17:8080/editaTarefa");

//        String encoding = Base64.encodeToString("u" + "" + "");
//        httpost.setHeader("Authorization", "Basic" + encoding);
        Log.i("Script", "ANSWER <-> vitor: foi 3");
        //convert parameters into JSON object
        JSONObject holder = tarefaToJson(tarefa);

        Log.i("Script", "ANSWER <-> vitor: foi 4");
        //passes the results to a string builder/entity

        String dadosString = holder.toString();

//        StringEntity se = new StringEntity(encoder64(dadosString));
        StringEntity se = new StringEntity(dadosString);

        Log.i("Script", "ANSWER <-> vitor: foi 5");
        //sets the post request as the resulting string
        httpost.setEntity(se);
        //sets a request header so the page receving the request
        //will know what to do with it
        Log.i("Script", "ANSWER <-> vitor: foi 6");
        httpost.setHeader("Accept", "application/json");
        Log.i("Script", "ANSWER <-> vitor: foi 7");
        httpost.setHeader("Content-type", "application/json");

        //Handles what is returned from the page
        Log.i("Script", "ANSWER <-> vitor: foi 8");
        ResponseHandler responseHandler = new BasicResponseHandler();
        Log.i("Script", "ANSWER <-> vitor: foi 9");
        httpclient.execute(httpost);
        Log.i("Script", "ANSWER <-> vitor: foi 10");
    }

	public static List<Tarefa> getTarefas() throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse resposta = httpClient.execute(new HttpGet("http://192.168.15.17:8080/tarefasPend"));
		BufferedReader br = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent(), "UTF-8"));

		String respJSON = br.readLine();
        Log.i("Script", "ANSWER <-> vitor: "+respJSON);
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
//            tarefa.setFoto((byte[]) jo.get("foto"));
            tarefa.setNome((String) jo.get("nome"));
//            tarefa.setLocalizacao(jo.getString("localizacao"));
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

//            String teste = "";
//            for (int i = 0; i < tarefa.getFoto().length; i++) {
//                teste += tarefa.getFoto()[i];
//            }

//            Log.i("Script", "ANSWER <-> Foto enviada: "+teste);


//            j.put("codigo", tarefa.getCodigo());
//            j.put("nome", tarefa.getNome());
//            j.put("status", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return j;
    }

    public static String encoder64(byte[] foto) {
//        String str = "";
//        for (int i = 0; i < foto.length; i++) {
//            str += foto[i];
//        }

        return Base64.encodeToString(foto, Base64.DEFAULT);
//        return str;
    }
}
