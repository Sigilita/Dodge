package com.nasser.juego.bombas;

public class Muro {
	
    public static final int TIPO_1 = 0;
    public static final int TIPO_2 = 1;
    public static final int TIPO_3 = 2;
    public int x, y;
    public int tipo;
    public int vida;
    
    public Muro(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        if(this.tipo==TIPO_1){
        	vida=1;
        }else if (this.tipo ==TIPO_2 )
        	vida=2;
        else
        	vida=1000000;
    }
    public void quitarVida(){
    	vida=vida-1;
    }
}