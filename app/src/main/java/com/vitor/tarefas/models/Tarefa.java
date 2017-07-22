package com.vitor.tarefas.models;

import android.util.Log;

/**
 * Created by vitor on 20/07/17.
 */

public class Tarefa {
    private int codigo;
    private String nome;
    private byte[] foto;
    private String localizacao;
    private int status;

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
//        Log.i("Script", "ANSWER <-> vitor --- ADICIONOU: "+nome);
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setStatus(int status) { this.status = status; }

    public int getStatus() { return this.status; }


    @Override
    public String toString() {
        return "Cód: " + codigo + " | Nome: " + nome + " | foto: " + foto + " | localizacao: " + localizacao + " | status: " + status;
    }
}
