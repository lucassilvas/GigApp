package com.example.lucas.gig.activitys;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucas.gig.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class CriarEventoActivity extends AppCompatActivity {

    private Drawer result;
    private EditText nomeEvento;
    private EditText horarioInicio;
    private EditText horarioTermino;
    private EditText atracoes;
    private EditText descricao;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nomeEvento    = (EditText) findViewById(R.id.nomeEvento);
        horarioInicio = (EditText) findViewById(R.id.horarioInicio);
        horarioTermino= (EditText) findViewById(R.id.horarioTermino);
        descricao     = (EditText) findViewById(R.id.descricao);



        //cria a barra de toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackground(getResources().getDrawable(R.color.primary));
        toolbar.setTitle("Criar Evento");


        //cabeçalho da conta
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .build();
        setSupportActionBar(toolbar);


        //itens do drawer
        result = new DrawerBuilder()
                //propriedades do drawer
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Início").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Perfil").withIcon(FontAwesome.Icon.faw_user).withIdentifier(2)
                ).withOnDrawerItemClickListener(
                        new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                if(drawerItem != null){
                                    Intent intent = null;
                                    if(drawerItem.getIdentifier() == 1){
                                        intent = new Intent(CriarEventoActivity.this, TelaInicialActivity.class);
                                    }
                                    if(drawerItem.getIdentifier() == 2){
                                        intent = new Intent(CriarEventoActivity.this, PerfilActivity.class);
                                    }
                                    if (intent != null) {
                                        CriarEventoActivity.this.startActivity(intent);
                                    }
                                }
                                return false;
                            }
                        }
                )
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
        result.addStickyFooterItem(new PrimaryDrawerItem().withName("Sair"));
    }


    //FUNÇÃO PARA REGISTRAR UM EVENTO NO BANCO
    private void registerEvent(){
        //CONVERTER O EDITTEXT PARA STRING
        final String nomeEventoStr = nomeEvento.getText().toString().trim();
        final String horarioInicioStr  = horarioInicio.getText().toString();
        final String horarioTerminoStr  = horarioTermino.getText().toString().trim();
        final String descricaoStr = descricao.getText().toString();


        //VERIFICAÇÃO DE CAMPOS OBRIGATÓRIOS NULOS
        if(TextUtils.isEmpty(nomeEventoStr) || TextUtils.isEmpty(horarioInicioStr)  || TextUtils.isEmpty(descricaoStr)){
            Toast.makeText(this, "Erro ao se cadastrar",Toast.LENGTH_LONG).show();
            return;
        }
        //CHAMADA À FUNÇÃO DE CADASTRO
        cadastrarNovoEventoBD(nomeEventoStr,horarioInicioStr,horarioTerminoStr,descricaoStr);
    }


    //FUNÇÃO QUE CADASTRA UM NOVO EVENTO NO BANCO
    private void cadastrarNovoEventoBD(String nomeEvento, String horaInicio, String horaTermino, String descricao) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("eventos");
        DatabaseReference currentUserDB = mDatabase.child(firebaseAuth.getCurrentUser().getUid());
        currentUserDB.child("nome").setValue(nomeEvento);
        currentUserDB.child("horaInicio").setValue(horaInicio);
        currentUserDB.child("horaTermino").setValue(horaTermino);
        currentUserDB.child("descricao").setValue(descricao);
    }
}



