package com.example.lucas.gig.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lucas.gig.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class CriarEventoActivity extends AppCompatActivity {

    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);

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
                                        intent = new Intent(CriarEventoActivity.this, BandaActivity.class);
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
}
