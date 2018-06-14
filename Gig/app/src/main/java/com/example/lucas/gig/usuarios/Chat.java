package com.example.lucas.gig.usuarios;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
;

import static android.content.ContentValues.TAG;

public class Chat {

    public String emissor;
    public String receptor;
    public String emissorUid;
    public String receptorUid;
    public String mensagem;
    public long tempo;

    public Chat() {}

    public Chat(String emissor, String receptor, String emissorUid, String receptorUid, String mensagem, long tempo) {
        this.emissor = emissor;
        this.receptor = receptor;
        this.emissorUid = emissorUid;
        this.receptorUid = receptorUid;
        this.mensagem = mensagem;
        this.tempo = tempo;
    }
    // FUNÇÃO DE CADASTRAR MENSAGEM ENVIADA NO BD
    public void enviarMensagemParaUsuario(final Context context,
                                          final Chat chat,
                                          final String receiverFirebaseToken) {
        //DEFININDO O FORMATO DA SALA: QUEM ENVIA A PRIMEIRA MENSAGEM FICA COM O NOME PRIMEIRO NO BD
        final String sala_tipo_1 = chat.emissorUid + "_" + chat.receptorUid;
        final String sala_tipo_2 = chat.receptorUid + "_" + chat.emissorUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();

        databaseReference.child("Chat")
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(sala_tipo_1)) {
                            Log.e(TAG, "enviarMensagemParaUsuario: " + sala_tipo_1 + " existe");
                            databaseReference.child("Chat")
                                    .child(sala_tipo_1)
                                    .child(String.valueOf(chat.tempo))
                                    .setValue(chat);
                        } else if (dataSnapshot.hasChild(sala_tipo_2)) {
                            Log.e(TAG, "enviarMensagemParaUsuario: " + sala_tipo_2 + " existe");
                            databaseReference.child("Chat")
                                    .child(sala_tipo_2)
                                    .child(String.valueOf(chat.tempo))
                                    .setValue(chat);
                        } else {
                            Log.e(TAG, "enviarMensagemParaUsuario: successo");
                            databaseReference.child("Chat")
                                    .child(sala_tipo_1)
                                    .child(String.valueOf(chat.tempo))
                                    .setValue(chat);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Não manda mensagem.

                    }
                });
    }

    public void pegarMensagemDoUsuario(String emissorUid, String receptorUid) {
        final String sala_tipo_1 = emissorUid + "_" + receptorUid;
        final String sala_tipo_2 = receptorUid + "_" + emissorUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference();

        databaseReference.child("Chat")
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(sala_tipo_1)) {
                            Log.e(TAG, "pegarMensagemDoUsuario: " + sala_tipo_1 + " existe");
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Chat")
                                    .child(sala_tipo_1)
                                    .addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            // RECUPERANDO A MENSAGEM DO BD.
                                            Chat chat = dataSnapshot.getValue(Chat.class);
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Impossível pegar mensagem.
                                        }
                                    });
                        } else if (dataSnapshot.hasChild(sala_tipo_2)) {
                            Log.e(TAG, "pegarMensagemDoUsuario: " + sala_tipo_2 + " existe");
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("Chat")
                                    .child(sala_tipo_2)
                                    .addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            // RECUPERANDO A MENSAGEM DO BD.
                                            Chat chat = dataSnapshot.getValue(Chat.class);
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Impossível pegar mensagem.
                                        }
                                    });
                        } else {
                            Log.e(TAG, "pegarMensagemDoUsuario: sala não disponível");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Impossível pegar mensagem.
                    }
                });
    }
}
