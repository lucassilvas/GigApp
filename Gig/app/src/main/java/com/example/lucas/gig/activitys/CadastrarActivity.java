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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private EditText nascimento;
    private Button continuar;
    private RadioButton tipoComum;
    private RadioButton tipoEstabelecimento;
    private RadioButton tipoMusico;
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
        nascimento= (EditText) findViewById(R.id.nasc);
        tipoComum = (RadioButton) findViewById(R.id.typeComum);
        tipoEstabelecimento = (RadioButton) findViewById(R.id.typeEstabelecimento);
        tipoMusico = (RadioButton) findViewById(R.id.typeMusico);

        continuar = (Button) findViewById(R.id.continuar);
        progressLogin = new ProgressDialog(this);
        continuar.setOnClickListener(this);
    }

    //função para registrar um usuário
    private void registerUser(){
        //converte o edit text para string.
        final String emailStr = email.getText().toString().trim();
        final String passwordStr  = senha.getText().toString();
        final String nomeStr  = nome.getText().toString().trim();
        final String sobrenomeStr  = sobrenome.getText().toString().trim();
        final String nascimentoStr = nascimento.getText().toString();

        //verifica se os campos estão vazios
        if(TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(nomeStr) || TextUtils.isEmpty(sobrenomeStr)){
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
                            currentUserDB.child("nascimento").setValue(nascimentoStr);
                            //Verificando o tipo do usuario
                            if (tipoDoUsuario(tipoComum)==1){
                                currentUserDB.child("tipoUsuario").setValue("Comum");
                            }
                            else if (tipoDoUsuario(tipoMusico)==2){
                                currentUserDB.child("tipoUsuario").setValue("Musico");
                            }
                            else if (tipoDoUsuario(tipoEstabelecimento)==3){
                                currentUserDB.child("tipoUsuario").setValue("Estabelecimento");
                            }

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
    public int tipoDoUsuario(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.typeComum:
                if (checked)
                    return 1;
                break;
            case R.id.typeMusico:
                if (checked)
                    return 2;
                break;
            case R.id.typeEstabelecimento:
                if (checked)
                    return 3;
                break;
        }
        return 0;
    }


    //chama o a função para registrar um usuário
    @Override
    public void onClick(View v) {
        registerUser();
    }
}
