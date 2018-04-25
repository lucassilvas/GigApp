package com.example.lucas.gig.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucas.gig.R;
import com.example.lucas.gig.usuarios.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private EditText email;
    private EditText senha;
    private EditText nome;
    private EditText sobrenome;
    private EditText usuario;
    private Button continuar;
    ProgressDialog progressLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        nome = (EditText) findViewById(R.id.nome);
        sobrenome = (EditText) findViewById(R.id.sobrenome);
        usuario= (EditText) findViewById(R.id.usuario);

        continuar = (Button) findViewById(R.id.continuar);
        progressLogin = new ProgressDialog(this);
        continuar.setOnClickListener(this);
    }


    //função para registrar um usuário
    private void registerUser(){
        //converte o edit text para string.
        final String emailStr = email.getText().toString().trim();
        final String passwordStr  = senha.getText().toString().trim();
        final String nomeStr  = nome.getText().toString().trim();
        final String sobrenomeStr  = sobrenome.getText().toString().trim();
        final String usuarioStr  = usuario.getText().toString().trim();


        //verifica se os campos estão vazios
        if(TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(nomeStr) || TextUtils.isEmpty(sobrenomeStr) || TextUtils.isEmpty(usuarioStr)){
            Toast.makeText(this, "Erro ao se cadastrar",Toast.LENGTH_LONG).show();
            return;
        }


        progressLogin.setMessage("Registrando Usuário...");
        progressLogin.show();

        //função para adicionar no banco
        firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            firebaseAuth.signInWithEmailAndPassword(emailStr, passwordStr);

                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("usuarios");
                            DatabaseReference currentUserDB = mDatabase.child(firebaseAuth.getCurrentUser().getUid());
                            currentUserDB.child("nome").setValue(nomeStr);
                            currentUserDB.child("sobrenome").setValue(sobrenomeStr);
                            currentUserDB.child("usuario").setValue(usuarioStr);


                            Toast.makeText(CadastrarActivity.this, "Sucesso",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nome", email.getText().toString().trim());
                            startActivity(intent);
                        }else{
                            Toast.makeText(CadastrarActivity.this, "Erro",Toast.LENGTH_LONG).show();
                        }
                        progressLogin.dismiss();
                    }
                });
    }



    //chama o a função para registrar um usuário
    @Override
    public void onClick(View v) {
        registerUser();
    }
}
