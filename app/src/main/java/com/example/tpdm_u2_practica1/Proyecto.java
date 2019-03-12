package com.example.tpdm_u2_practica1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Date;

public class Proyecto {
    String descripcion, ubicacion, fecha, error;
    float presupuesto;
    int id;
    BaseDatos base;

    public Proyecto (int id, String d, String u, String f, float p){
        this.id=id;
        descripcion=d;
        ubicacion=u;
        fecha=f;
        presupuesto=p;
    }

    public Proyecto(Activity proyecto){
        base=new BaseDatos(proyecto,"civil",null,1);
    }

    public boolean insertar(Proyecto proyecto){
        try {
            SQLiteDatabase transaccionInsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION", proyecto.getDescripcion());
            datos.put("UBICACION", proyecto.getUbicacion());
            datos.put("FECHA", proyecto.getFecha());
            datos.put("PRESUPUESTO", proyecto.getPresupuesto());
            long respuesta = transaccionInsertar.insert("PROYECTOS", "IDPROYECTO", datos);
            transaccionInsertar.close();
            if (respuesta<0){//respuesta==-1
                return false;
            }
        }
        catch (SQLiteException e){
            Log.e("Error: ",e.getMessage());
            return false;
        }
        return true;
    }//insertar

    public Proyecto consultarDescripcion(String descripcion){
        Proyecto proyect=null;
        try {
            SQLiteDatabase tabla=base.getReadableDatabase();
            String SQL="SELECT*FROM PROYECTOS WHERE DESCRIPCION=?";
            String claves[]={descripcion};
            Cursor c=tabla.rawQuery(SQL,claves);
            if(c.moveToFirst()){
                proyect=new Proyecto(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getFloat(4));
            }
            tabla.close();
        }
        catch (SQLiteException e){
            return null;
        }
        return proyect;
    }//consultarDescripcion

    public Proyecto consultarId(Object dato){
        Proyecto proyect=null;
        /*try {
            SQLiteDatabase transaccionConsultar=base.getReadableDatabase();
            String SQL="SELECT*FROM PROYECTOS WHERE IDPROYECTO=?";
            String claves[]={id};
            Cursor c=transaccionConsultar.rawQuery(SQL,claves);
            if(c.moveToFirst()){
                proyect=new Proyecto(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getFloat(4));
            }
            tabla.close();
        }
        catch (SQLiteException e){
            return null;
        }
        return proyect;*/
            try{
                SQLiteDatabase db=base.getReadableDatabase();
                Cursor c=null;
                if (dato instanceof Integer){
                    c=db.rawQuery("SELECT * FROM PROYECTOS WHERE IDPROYECTO=?",new String[]{Integer.parseInt(dato.toString())+""});
                }
                else{
                    c=db.rawQuery("SELECT * FROM PROYECTOS WHERE DESCRIPCION=?",new String[]{dato.toString()});
                }

                if (c.moveToFirst()) {
                    Proyecto proyecto = new Proyecto(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getFloat(4));
                    return proyecto;
                }
            }catch(SQLiteException e){
                e.printStackTrace();
                return null;
            }
            return null;
        }

    }//

    public Proyecto[] consultar(){
        Proyecto[] resultado=null;
        try {
            SQLiteDatabase transaccionConsulta=base.getReadableDatabase();
            String SQL="SELECT*FROM PROYECTOS";
            Cursor c=transaccionConsulta.rawQuery(SQL,null);
            if (c.moveToFirst()){
                resultado=new Proyecto[c.getCount()];
                int posicion=0;
                do {
                    resultado[posicion]=new Proyecto(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getFloat(4));
                    posicion++;
                }
                while (c.moveToNext());
                return resultado;
            }//if
        }
        catch (SQLiteException e){
            e.printStackTrace();
            return null;
        }
        return resultado;
    }//consultar

    public boolean eliminar(Proyecto proyecto){
        try {
            SQLiteDatabase transaccionEliminar=base.getWritableDatabase();
            String[] datos={""+proyecto.id};
            long resultado=transaccionEliminar.delete("PROYECTOS","IDPROYECTO=?",datos);
            transaccionEliminar.close();
            if (resultado==0){
                return false;
            }
        }
        catch (SQLiteException e){
            error=e.getMessage();
            return false;
        }
        return true;
    }//eliminar

    public boolean actualizar(Proyecto proyecto){
        try {
            SQLiteDatabase transaccionActualizar=base.getWritableDatabase();
            ContentValues datos=new ContentValues();
            datos.put("DESCRIPCION", proyecto.getDescripcion());
            datos.put("UBICACION", proyecto.getUbicacion());
            datos.put("FECHA", proyecto.getFecha());
            String[] clave={""+proyecto.getId()};
            long respuesta = transaccionActualizar.update("PROYECTOS", datos,"IDPROYECTO=?", clave);
            transaccionActualizar.close();
            if (respuesta<0){
                return false;
            }
        }
        catch (SQLiteException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }//actualizar

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getError(){
        return error;
    }

}//class
