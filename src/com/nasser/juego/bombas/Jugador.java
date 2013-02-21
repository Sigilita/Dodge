package com.nasser.juego.bombas;

import java.util.ArrayList;
import java.util.List;

public class Jugador{
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;
    public int x,y;
    public int direccion;
    public int minx,miny,maxx,maxy;

    public Jugador(int x, int y,int minx,int miny, int maxx, int maxy) {        
    	direccion = ARRIBA;
    	this.x=x;
    	this.y=y;
    	this.minx =minx ;
    	this.miny =miny ;
    	this.maxx =maxx ;
    	this.maxy =maxy ;
    }
    
    public void Izquierda() {
    	direccion = IZQUIERDA;
    }
    public void Derecha() {
    	direccion = DERECHA;
    }
    public void Arriba(){
    	direccion =ARRIBA;
    }
    public void Aabajo(){
    	direccion =ABAJO;
    }
    
  /*  public void movimiento() {
      
       xanterior =this.x;
       yanterior=this.y;
   
        if(direccion == ARRIBA)
            this.y=yanterior-1;
        if(direccion == IZQUIERDA)
        	this.x=xanterior -1;
        if(direccion == ABAJO)
        	this.y=yanterior +1;
        if(direccion == DERECHA)
        	this.x=xanterior+1;
        
        if(this.x < minx )
        	this.x=x;
        if(this.x > maxx )
        	this.x=x;
        if(this.y < miny)
        	this.y=y;
        if(this.y > maxy )
        	this.y=y;
    }*/
    public int getX(){
    	return this.x;
    }
    public int getY(){
    	return this.y;
    }
}
