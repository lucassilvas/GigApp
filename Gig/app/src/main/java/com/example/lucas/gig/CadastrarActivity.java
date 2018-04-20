package com.example.lucas.gig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastrarActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText senha;
    private Button continuar;
    ProgressDialog progressLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        continuar = (Button) findViewById(R.id.continuar);
        progressLogin = new ProgressDialog(this); // telazinha que
        continuar.setOnClickListener(this);
    }


    //função para registrar um usuário
    private void registerUser(){
        //converte o edit text para string.
        String login = email.getText().toString().trim();
        String password  = senha.getText().toString().trim();
        //verifica se os campos estão vazios
        if(TextUtils.isEmpty(login)){
            Toast.makeText(this, "Entre com seu email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Entre com sua senha",Toast.LENGTH_LONG).show();
            return;
        }
        progressLogin.setMessage("Registrando Usuário...");
        progressLogin.show();
        //função para adicionar no banco
        firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
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
