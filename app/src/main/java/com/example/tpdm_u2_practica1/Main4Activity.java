package com.example.tpdm_u2_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {
    EditText descripcion, ubicacion, fecha, presupuesto;
    Button eliminar, actualizar, regresar;
    Proyecto proyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        descripcion = findViewById(R.id.txt__Descripcion);
        ubicacion = findViewById(R.id.txt__Ubicacion);
        fecha = findViewById(R.id.txt__Fecha);
        presupuesto = findViewById(R.id.txt__Presupuesto);
        eliminar = findViewById(R.id.btn__Eliminar);
        actualizar = findViewById(R.id.btn__Actualizar);
        regresar = findViewById(R.id.btn__Regresar);
        proyecto=new Proyecto(this);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Proyecto proyect=new Proyecto(Main4Activity.this);
                if (proyect.eliminar(proyecto)){
                    Toast.makeText(Main4Activity.this,"Proyecto eliminado correctamente",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Main4Activity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Main4Activity.this, "Ocurrio un error", Toast.LENGTH_LONG).show();
                }
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Proyecto proyect=new Proyecto(Main4Activity.this);
                if (proyect.actualizar(new Proyecto(proyecto.getId(),descripcion.getText().toString(),ubicacion.getText().toString(),fecha.getText().toString(),Float.parseFloat(presupuesto.getText().toString())))){
                    Toast.makeText(Main4Activity.this,"Proyecto actualizado correctamente",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Main4Activity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Main4Activity.this, "Ocurrio un error", Toast.LENGTH_LONG).show();
                }
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main4Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    protected void onStart(){
        super.onStart();
        Bundle extras=getIntent().getExtras();
        proyecto=new Proyecto(extras.getInt("id"),extras.getString("des"),extras.getString("ubica"),extras.getString("fecha"),extras.getFloat("presupuesto"));
        descripcion.setText(proyecto.getDescripcion());
        ubicacion.setText(proyecto.getUbicacion());
        fecha.setText(proyecto.getFecha());
        presupuesto.setText(proyecto.getPresupuesto()+"");
    }
}