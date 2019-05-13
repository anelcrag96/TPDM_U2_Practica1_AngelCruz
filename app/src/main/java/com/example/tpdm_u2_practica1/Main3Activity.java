package com.example.tpdm_u2_practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    EditText descripcion_ID, descripcion, ubicacion, fecha, presupuesto;
    Button consultar, actualizar, eliminar, regresar;
    Proyecto proyect,proyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        descripcion_ID = findViewById(R.id.txt_DescripcionID);
        consultar = findViewById(R.id.btn_Consultar);
        descripcion = findViewById(R.id.txt_Descripcion);
        ubicacion = findViewById(R.id.txt_Ubicacion);
        fecha = findViewById(R.id.txt_Fecha);
        presupuesto = findViewById(R.id.txt_Presupuesto);
        actualizar = findViewById(R.id.btn_Actualizar);
        eliminar =findViewById(R.id.btn_Eliminar);
        regresar = findViewById(R.id.btn_Regresar);
        proyect=new Proyecto(this);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda=descripcion_ID.getText().toString();
                int id=0;
                try{
                    id=Integer.parseInt(busqueda);
                }
                catch(NumberFormatException e){
                    e.getMessage();
                }

                if (id==0){
                    proyecto=proyect.consultar(busqueda);
                }
                else{
                    proyecto=proyect.consultar(id);
                }

                if (proyecto==null){
                    Toast.makeText(Main3Activity.this, "No existe registro", Toast.LENGTH_LONG).show();
                }
                else{
                    descripcion.setText(proyecto.getDescripcion());
                    ubicacion.setText(proyecto.getUbicacion());
                    fecha.setText(proyecto.getFecha());
                    presupuesto.setText(proyecto.getPresupuesto()+"");
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proyect.eliminar(proyecto)){
                    Toast.makeText(Main3Activity.this, "Proyecto eliminado correctamente", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Main3Activity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Main3Activity.this, "Ocurrio un error", Toast.LENGTH_LONG).show();
                }
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proyect.actualizar(new Proyecto(proyecto.getId(),descripcion.getText().toString(),ubicacion.getText().toString(),fecha.getText().toString(),Float.parseFloat(presupuesto.getText().toString())))){
                    Toast.makeText(Main3Activity.this,"Proyecto actualizado correctamente",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Main3Activity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Main3Activity.this, "Ocurrio un error", Toast.LENGTH_LONG).show();
                }
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main3Activity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
