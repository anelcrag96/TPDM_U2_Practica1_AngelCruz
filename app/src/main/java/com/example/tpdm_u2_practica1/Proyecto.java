package com.example.tpdm_u2_practica1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class Proyecto {
    private int id;
    private String descripcion;
    private String ubicacion;
    private String fecha;
    private float presupuesto;
    private BaseDatos base;
    private String error;

    public Proyecto(Activity activity){
        base=new BaseDatos(activity,"civil",null,1);
    }

    public Proyecto (int id, String des, String ubi, String fec, float pre){
        this.id=id;
        this.descripcion=des;
        this.ubicacion=ubi;
        this.fecha=fec;
        this.presupuesto=pre;
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
            if (respuesta==-1){//respuesta==-1
                return false;//NO SE REALIZO LA INSERCION
            }
        }
        catch (SQLiteException e){
            error=e.getMessage();
            return false;
        }
        return true;
    }//insertar

    public Proyecto consultar(Object dato){
        try{
            SQLiteDatabase transaccionConsultar=base.getReadableDatabase();
            Cursor cursor=null;
            if (dato instanceof Integer){
                cursor=transaccionConsultar.rawQuery("SELECT * FROM PROYECTOS WHERE IDPROYECTO=?",new String[]{Integer.parseInt(dato.toString())+""});
            }
            else{
                cursor=transaccionConsultar.rawQuery("SELECT * FROM PROYECTOS WHERE DESCRIPCION=?",new String[]{dato.toString()});
            }

            if (cursor.moveToFirst()) {
                Proyecto proyecto = new Proyecto(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getFloat(4));
                return proyecto;
            }
        }catch(SQLiteException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }//consultar

    public Proyecto[] consultar(){
        try {
            SQLiteDatabase transaccionConsultar=base.getReadableDatabase();
            Cursor cursor=transaccionConsultar.rawQuery("SELECT*FROM PROYECTOS",null);
            if (cursor.moveToFirst()){
                Proyecto[] resultado=new Proyecto[cursor.getCount()];
                int posicion=0;
                do {
                    resultado[posicion]=new Proyecto(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getFloat(4));
                    posicion++;
                }
                while (cursor.moveToNext());
                return resultado;
            }//if
        }
        catch (SQLiteException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }//consultar

    public boolean eliminar(Proyecto proyecto){
        try {
            SQLiteDatabase transaccionEliminar=base.getWritableDatabase();

            long resultado=transaccionEliminar.delete("PROYECTOS","IDPROYECTO=?", new String[] {""+proyecto.getId()});
            transaccionEliminar.close();
            if (resultado==0){
                return false;//No se pudo eliminar
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
            datos.put("PRESUPUESTO",proyecto.getPresupuesto());
            String clave[]={""+proyecto.getId()};
            long respuesta = transaccionActualizar.update("PROYECTOS", datos,"IDPROYECTO=?", clave);
            transaccionActualizar.close();
            if (respuesta<0){
                return false;//No se actualizo nada
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
