package com.example.lucas.gig.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.gig.R;
import com.example.lucas.gig.usuarios.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class PerfilActivity extends AppCompatActivity {

    private AccountHeader headerResult = null;
    private Drawer result = null;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView nomeUser;
    private TextView sobrenomeUser;
    private static final String TAG = "teste";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //cria a barra de toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackground(getResources().getDrawable(R.color.primary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");

        //cabeçalho da conta
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .build();

        //itens do drawer
        result = new DrawerBuilder()
                //propriedades do drawer
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Início").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName("Perfil").withIcon(FontAwesome.Icon.faw_user).withIdentifier(2).withSelectable(false)
                ).withOnDrawerItemClickListener( // quando clicar em algum item do menu fazer uma ação.
                        new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                if(drawerItem != null){
                                    Intent intent = null;
                                    if(drawerItem.getIdentifier() == 1){
                                        intent = new Intent(PerfilActivity.this, BandaActivity.class);
                                    }
                                    if(drawerItem.getIdentifier() == 2){
                                        intent = new Intent(PerfilActivity.this, PerfilActivity.class);
                                    }
                                    if (intent != null) {
                                        PerfilActivity.this.startActivity(intent);
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

        nomeUser = (TextView) findViewById(R.id.nomeUser);
        sobrenomeUser = (TextView) findViewById(R.id.sobrenome);


        //pega o usuario atual
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("usuarios");
            //vai no banco de dados e pega o filho correspondente ao UID do usuario logado
            mDatabase.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Muda o texte que está no textview para o que está salvo no banco de dados
                    nomeUser.setText(String.valueOf((String)dataSnapshot.child("nome").getValue()) +" "+ String.valueOf((String)dataSnapshot.child("sobrenome").getValue()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}
