package com.example.lucas.gig;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Abrir bandaActivity ao clicar no botão login
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent bandaActivity = new Intent(MainActivity.this, BandaActivity.class);
                startActivity(bandaActivity);
            }
        });

        Button cadastrese = (Button)findViewById(R.id.btn_cadastrar);

        cadastrese.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent cadastrarActivity = new Intent(MainActivity.this, CadastrarActivity.class);
                startActivity(cadastrarActivity);
            }
        });
    }



}
