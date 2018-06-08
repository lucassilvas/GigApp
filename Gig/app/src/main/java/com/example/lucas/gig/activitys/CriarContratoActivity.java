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

public class CriarContratoActivity extends AppCompatActivity {
    private Drawer result;

    private EditText nomeCompleto;
    private EditText cpf;
    private EditText rg;
    private EditText estadoCivil;
    private EditText nacionalidade;
    private EditText estado;
    private EditText cidade;
    private EditText rua;
    private EditText bairro;
    private EditText numero;
    private EditText nomeBanda;
    private EditText duracaoEvento;
    private EditText dia;
    private EditText horaInicio;
    private EditText horaTermino;
    private EditText pagamento;
    private EditText condicoes;
    private Button continuar;
    private Button cancelar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_contrato);
        firebaseAuth = FirebaseAuth.getInstance();

        //ATRIBUINDO OS VALORES REFERENTES AS ENTRADAS DO USUÁRIO
        nomeCompleto    =  findViewById(R.id.nomeCompleto);
        cpf             =  findViewById(R.id.cpf);
        rg              =  findViewById(R.id.rg);
        estadoCivil     =  findViewById(R.id.estadoCivil);
        nacionalidade   =  findViewById(R.id.nacionalidade);
        estado          =  findViewById(R.id.estado);
        cidade          =  findViewById(R.id.cidade);
        rua             =  findViewById(R.id.rua);
        bairro          =  findViewById(R.id.bairro);
        numero          =  findViewById(R.id.numero);
        nomeBanda       =  findViewById(R.id.nomeBanda);
        duracaoEvento   =  findViewById(R.id.duracaoEvento);
        dia             =  findViewById(R.id.diaEvento);
        horaInicio      =  findViewById(R.id.horaInicio);
        horaTermino     =  findViewById(R.id.horaTermino);
        pagamento       =  findViewById(R.id.pagamento);
        condicoes       =  findViewById(R.id.condicoes);


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
                                        intent = new Intent(CriarContratoActivity.this, TelaInicialActivity.class);
                                    }
                                    if(drawerItem.getIdentifier() == 2){
                                        intent = new Intent(CriarContratoActivity.this, PerfilActivity.class);
                                    }
                                    if (intent != null) {
                                        CriarContratoActivity.this.startActivity(intent);
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
    private void criarContrato(){
        // CONVERTENDO OS VALORES DOS EDITTEXTS EM STRING
        final String nomeCompletoStr    = nomeCompleto.getText().toString();
        final String cidadeStr          = cidade.getText().toString().trim();
        final String estadoStr          = estado.getText().toString().trim();
        final String ruaStr             = rua.getText().toString();
        final String numeroStr          = numero.getText().toString().trim();
        final String bairroStr          = bairro.getText().toString();
        final String cpfStr             = cpf.getText().toString().trim();
        final String rgStr              = rg.getText().toString().trim();
        final String nacionalidadeStr   = nacionalidade.getText().toString().trim();
        final String estadoCivilStr     = estadoCivil.getText().toString().trim();
        final String nomeBandaStr       = nomeBanda.getText().toString().trim();
        final String duracaoEventoStr   = duracaoEvento.getText().toString().trim();
        final String diaStr             = dia.getText().toString().trim();
        final String horaInicioStr      = horaInicio.getText().toString().trim();
        final String horaTerminoStr     = horaTermino.getText().toString().trim();
        final String pagamentoStr       = pagamento.getText().toString().trim();
        final String condicoesStr       = condicoes.getText().toString().trim();



        //VERIFICA SE ALGUM CAMPO ESTÁ VAZIO
        if(TextUtils.isEmpty(nomeCompletoStr)       ||
                TextUtils.isEmpty(cidadeStr)        ||
                TextUtils.isEmpty(estadoStr)        ||
                TextUtils.isEmpty(ruaStr)           ||
                TextUtils.isEmpty(numeroStr)        ||
                TextUtils.isEmpty(bairroStr)        ||
                TextUtils.isEmpty(cpfStr)           ||
                TextUtils.isEmpty(rgStr)            ||
                TextUtils.isEmpty(nacionalidadeStr) ||
                TextUtils.isEmpty(estadoCivilStr)   ||
                TextUtils.isEmpty(nomeBandaStr)     ||
                TextUtils.isEmpty(duracaoEventoStr) ||
                TextUtils.isEmpty(diaStr)           ||
                TextUtils.isEmpty(horaInicioStr)    ||
                TextUtils.isEmpty(horaTerminoStr)   ||
                TextUtils.isEmpty(pagamentoStr)     ||
                TextUtils.isEmpty(condicoesStr)){
            Toast.makeText(this, "Preencha todos os campos !!",Toast.LENGTH_LONG).show();
            return;
        }
        //CHAMADA À FUNÇÃO DE CADASTRO NO BANCO
        cadastrarNovoContratoBD(nomeCompletoStr,cidadeStr,estadoStr,ruaStr,numeroStr,bairroStr,cpfStr,rgStr,nacionalidadeStr,estadoCivilStr,nomeBandaStr,duracaoEventoStr,diaStr,horaInicioStr,horaTerminoStr,pagamentoStr,condicoesStr);
    }


    //FUNÇÃO QUE REGISTRA O CONTRATO NO BANCO
    private void cadastrarNovoContratoBD(String nomeCompleto, String cidade, String estado, String rua, String numero, String bairro, String cpf, String rg, String nacionalidade, String estadoCivil, String nomeBanda, String duracaoEvento, String dia, String horaInicio, String horaTermino, String pagamento, String condicoes){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("contratos");
        DatabaseReference currentUserDB = mDatabase.child(firebaseAuth.getCurrentUser().getUid());
        currentUserDB.child("nomeCompleto").setValue(nomeCompleto);
        currentUserDB.child("cidade").setValue(cidade);
        currentUserDB.child("estado").setValue(estado);
        currentUserDB.child("rua").setValue(rua);
        currentUserDB.child("numero").setValue(numero);
        currentUserDB.child("bairro").setValue(bairro);
        currentUserDB.child("estadoCivil").setValue(estadoCivil);
        currentUserDB.child("cpf").setValue(cpf);
        currentUserDB.child("rg").setValue(rg);
        currentUserDB.child("nacionalidade").setValue(nacionalidade);
        currentUserDB.child("nomeBanda").setValue(nomeBanda);
        currentUserDB.child("duracaoEvento").setValue(duracaoEvento);
        currentUserDB.child("dia").setValue(dia);
        currentUserDB.child("horaInicio").setValue(horaInicio);
        currentUserDB.child("horaTermino").setValue(horaTermino);
        currentUserDB.child("pagamento").setValue(pagamento);
        currentUserDB.child("condicoes").setValue(condicoes);




    }
    public void onClick(View v) {
        criarContrato();
    }

}

