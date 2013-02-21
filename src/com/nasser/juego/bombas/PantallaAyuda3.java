package com.nasser.juego.bombas;

import java.util.List;


import com.nasser.juego.marco.Graficos;
import com.nasser.juego.marco.Juego;
import com.nasser.juego.marco.Pantalla;
import com.nasser.juego.marco.Input.TouchEvent;

public class PantallaAyuda3 extends Pantalla {      
    public PantallaAyuda3(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new MainMenuScreen(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
                if(event.x < 64 && event.y > 416 ) {
                    juego.setScreen(new PantallaAyuda2(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();      
        g.drawPixmap(Assets.Fondo, 0, 0);
        g.drawPixmap(Assets.ayuda3, 64, 100);
        g.drawPixmap(Assets.FlechaDerecha, 256, 416);
        g.drawPixmap(Assets.FlechaIzquierda, 0, 416);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}