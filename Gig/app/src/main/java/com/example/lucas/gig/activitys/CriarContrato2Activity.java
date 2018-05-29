package com.example.lucas.gig.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lucas.gig.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class CriarContrato2Activity extends AppCompatActivity {
    private Drawer result;

    private EditText nomeBanda;
    private EditText duracaoEvento;
    private EditText diaEvento;
    private EditText horaInicio;
    private EditText horaTermino;
    private EditText pagamento;
    private EditText condicoesExtras;
    private FirebaseAuth firebaseAuth;
    private Button continuar;
    private Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_contrato2);
        firebaseAuth = FirebaseAuth.getInstance();

        //ATRIBUINDO OS VALORES REFERENTES AS ENTRADAS DO USUÁRIO
        nomeBanda          =  findViewById(R.id.nomeBanda);
        duracaoEvento      =  findViewById(R.id.duracaoEvento);
        diaEvento          =  findViewById(R.id.diaEvento);
        horaInicio         =  findViewById(R.id.horaInicio);
        horaTermino        =  findViewById(R.id.horarioTermino);
        pagamento          =  findViewById(R.id.pagamento);
        condicoesExtras    =  findViewById(R.id.condicoes);


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
                                        intent = new Intent(CriarContrato2Activity.this, TelaInicialActivity.class);
                                    }
                                    if(drawerItem.getIdentifier() == 2){
                                        intent = new Intent(CriarContrato2Activity.this, PerfilActivity.class);
                                    }
                                    if (intent != null) {
                                        CriarContrato2Activity.this.startActivity(intent);
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

    //FUNÇÃO QUE PEGA OS VALORES DO USUÁRIO E SALVA EM VARIÁVEIS
    private void criarContrato2(){

        // CONVERTENDO OS VALORES DOS EDITTEXTS EM STRING
        final String nomeBandaStr = nomeBanda.getText().toString();
        final String duracaoEventoStr = duracaoEvento.getText().toString().trim();
        final String diaEventoStr = diaEvento.getText().toString().trim();
        final String horaInicioStr = horaInicio.getText().toString();
        final String horaTerminoStr = horaTermino.getText().toString();
        final String pagamentoStr = pagamento.getText().toString();
        final String condicoesExtrasStr = condicoesExtras.getText().toString();

        //VERIFICA SE ALGUM CAMPO ESTÁ VAZIO
        if(TextUtils.isEmpty(nomeBandaStr)           ||
                TextUtils.isEmpty(duracaoEventoStr)  ||
                TextUtils.isEmpty(diaEventoStr)      ||
                TextUtils.isEmpty(horaInicioStr)     ||
                TextUtils.isEmpty(horaTerminoStr)      ||
                TextUtils.isEmpty(pagamentoStr)     ||
                TextUtils.isEmpty(condicoesExtrasStr)){
            Toast.makeText(this, "Preencha todos os campos !!",Toast.LENGTH_LONG).show();
            return;
        }

        //CHAMADA À FUNÇÃO DE CADASTRO NO BANCO
        cadastrarNovoContrato2BD(nomeBandaStr,duracaoEventoStr,diaEventoStr,horaInicioStr,horaTerminoStr,pagamentoStr,condicoesExtrasStr);

    }
    private void cadastrarNovoContrato2BD(String nomeBanda, String duracaoEvento, String diaEvento, String horaInicio, String horaTermino, String pagamento, String condicoesExtras){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("contratos");
        DatabaseReference currentUserDB = mDatabase.child(firebaseAuth.getCurrentUser().getUid());
    }
    public void onClick(View v) {
        criarContrato2();
    }
}

