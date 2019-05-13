package com.example.tpdm_u2_practica1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    Proyecto[] listaProyectoCivil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista=findViewById(R.id.listaProyectos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alerta=new AlertDialog.Builder(MainActivity.this);
                alerta.setTitle("Alerta")
                        .setMessage("¿Desea actualizar/eliminar el proyecto seleccionado?")
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent datos=new Intent(MainActivity.this,Main4Activity.class);
                                datos.putExtra("ID",listaProyectoCivil[position].getId());
                                datos.putExtra("DESCRIPCION",listaProyectoCivil[position].getDescripcion());
                                datos.putExtra("FECHA",listaProyectoCivil[position].getFecha());
                                datos.putExtra("UBICACION",listaProyectoCivil[position].getUbicacion());
                                datos.putExtra("PRESUPUESTO",listaProyectoCivil[position].getPresupuesto());

                                startActivity(datos);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Proyecto proyecto=new Proyecto(this);
        Proyecto vector[]=proyecto.consultar();
        listaProyectoCivil=vector;
        String[] descripcion=null;

        if (vector==null){
            descripcion=new String[1];
            descripcion[0]="No existen proyectos registrados";
            lista.setEnabled(false);
        }
        else{
            descripcion=new String[vector.length];
            for (int i=0; i<vector.length; i++){
                Proyecto temperal=vector[i];
                descripcion[i]=temperal.getDescripcion();
            }
            lista.setEnabled(true);
        }
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,descripcion);
        lista.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.insertar:
                Intent insertar=new Intent(this,Main2Activity.class);
                startActivity(insertar);
                finish();
                break;
            case R.id.consultar:
                Intent consultar=new Intent(this,Main3Activity.class);
                startActivity(consultar);
                finish();
                break;
            case R.id.eliminar:
                Intent eliminar=new Intent(this,Main3Activity.class);
                startActivity(eliminar);
                finish();
                break;
            case R.id.actualizar:
                Intent actualizar=new Intent(this,Main3Activity.class);
                startActivity(actualizar);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
