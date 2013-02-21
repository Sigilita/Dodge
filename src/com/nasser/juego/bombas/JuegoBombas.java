package com.nasser.juego.bombas;

import com.nasser.juego.marco.Pantalla;
import com.nasser.juego.marco.androidimpl.AndroidJuego;

public class JuegoBombas extends AndroidJuego {
    @Override
    public Pantalla getStartScreen() {
    	return new LoadingScreen(this); 
    }
}
