package com.example.tpdm_u2_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText descripcion, ubicacion, fecha, presupuesto;
    Button insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        descripcion = findViewById(R.id.txtDescripcion);
        ubicacion = findViewById(R.id.txtUbicacion);
        fecha = findViewById(R.id.txtFecha);
        presupuesto = findViewById(R.id.txtPresupuesto);
        insertar = findViewById(R.id.btnInsertar);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Proyecto proyecto=new Proyecto(Main2Activity.this);
                if (proyecto.insertar(new Proyecto(1,descripcion.getText().toString(),ubicacion.getText().toString(),fecha.getText().toString(),Float.parseFloat(presupuesto.getText().toString())))){
                    Toast.makeText(Main2Activity.this,"Proyecto insertado correctamente",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Main2Activity.this, "Ocurrio un error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}