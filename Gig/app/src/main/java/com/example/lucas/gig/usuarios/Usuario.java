package com.example.lucas.gig.usuarios;

import java.util.ArrayList;

/**
 * Created by Lucas on 23/04/2018.
 */

public class Usuario {
    private String uid; // Vai ser gerado pelo auth do firebase;
    private String nome;
    private String sobrenome;
    private String tipo; // Musico/Restaurante/Comum
    private ArrayList<Usuario> seguidores;
    private ArrayList<Usuario> seguindo;

    //Métodos SET
    public void setUid(String uid) {
        this.uid = uid;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public void addSeguidor(Usuario usuario) {
        this.seguidores.add(usuario);
    }

    //Busca o uid do seguidor a ser excluído e o remove da lista de seguidores;
    public void delSeguidor(Usuario usuario) {
        Usuario auxUser;
        for(int i=0; i<this.seguidores.size(); i++){
            auxUser = this.seguidores.get(i);
            if (auxUser.getUid().equals(usuario.getUid())){
                this.seguidores.remove(i);
                break;
            }
        }
    }

    public void follow(Usuario usuario) {
        this.seguindo.add(usuario);
    }
    public void unfollow(Usuario usuario){
        Usuario auxUser;
        for(int i=0; i<this.seguindo.size(); i++){
            auxUser = this.seguindo.get(i);
            if (auxUser.getUid().equals(usuario.getUid())){
                this.seguindo.remove(i);
                break;
            }
        }
    }

    //Métodos GET
    public String getNome() {
        return nome;
    }
    public String getUid() {
        return uid;
    }
    public String getSobrenome() {
        return sobrenome;
    }
}


