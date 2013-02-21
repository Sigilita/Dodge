package com.nasser.juego.bombas;

import java.util.List;

import android.graphics.Color;


import com.nasser.juego.marco.Graficos;
import com.nasser.juego.marco.Juego;
import com.nasser.juego.marco.Pantalla;
import com.nasser.juego.marco.Pixmap;
import com.nasser.juego.marco.Input.TouchEvent;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }
    
    EstadoJuego estado = EstadoJuego.Preparado;
    Mundo mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";
    
    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();
        
        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)        	
            updateGameOver(touchEvents);
            
    }
    
    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)        	
            estado = EstadoJuego.Ejecutandose;
    }
    
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            /*if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }*/
            if(estado==EstadoJuego.Ejecutandose)
            if(event.type==TouchEvent.TOUCH_UP){
            	if((event.x<320)&& (event.y<416)){
            		estado=EstadoJuego.Pausado;
            	}
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    mundo._player.Izquierda();
                    mundo.movimiento();
                }
                if((event.x < 128 && event.x >64) && event.y > 416) {
                    mundo._player.Derecha();
                    mundo.movimiento();

                }
                if((event.x < 192 && event.x >128) && event.y > 416) {
                    mundo.ponerBomba();
                }
                if((event.x < 256 && event.x >192) && event.y > 416) {
                    mundo._player.Aabajo();
                    mundo.movimiento();

                }
                if((event.x < 320 && event.x >256) && event.y > 416) {
                    mundo._player.Arriba();
                    mundo.movimiento();

                }
            }
        }
        
        mundo.update(deltaTime);
        if(mundo.finalJuego) {        	
            if(Configuraciones.sonidoHabilitado)
                Assets.derrota.play(1);               
            estado = EstadoJuego.FinJuego;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configuraciones.sonidoHabilitado)
                Assets.ataque.play(1);
        }
    }
    
    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) 
        {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) 
            {
                if(event.x > 80 && event.x <= 240) 
                {
                    if(event.y > 100 && event.y <= 148) 
                    {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }
                    if((event.y>148)&& (event.y<196)){
                    	if(Configuraciones.sonidoHabilitado=true)
                    		Configuraciones.sonidoHabilitado=false;
                    	else
                    		Configuraciones.sonidoHabilitado=true;
                    }
                    if(event.y > 196 && event.y < 244) 
                    {
                    	if(Configuraciones.sonidoHabilitado)
                    		Assets.pulsar.play(1);
                      juego.setScreen(new MainMenuScreen(juego));                        
                      return;
                    }
                }
            }
        }
    }
    
    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 0 && event.x <= 320 &&
                   event.y >= 0 && event.y <= 416) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);                        
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }
    

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.Fondo, 0, 0);
        drawWorld(mundo);
        if(estado == EstadoJuego.Preparado) 
            drawReadyUI();
        if(estado == EstadoJuego.Ejecutandose)
            drawRunningUI();
        if(estado == EstadoJuego.Pausado)
            drawPausedUI();
        if(estado == EstadoJuego.FinJuego)
        	drawGameOverUI();
            
        
        //drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length()*20 / 2, g.getHeight() - 42);
        drawText(g, puntuacion, 0 + puntuacion.length()*20, 0);  
    }
    
    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        Jugador _player = mundo._player;
        List <Enemigo> _ListaEnemigos= mundo._listaEnemigos;
        List <Muro> _ListaMuros= mundo._listaMuros;
        List <Premios> _ListaPremios= mundo._listaPremios;
        List <Explosion> _ListaExplosion=mundo._listaExplosiones;
        int x,y;
        
        Pixmap jugadorPixmap = null;
        if(_player.direccion == Jugador.ARRIBA) 
        	jugadorPixmap = Assets.JugadorArriba;
        if(_player.direccion == Jugador.IZQUIERDA) 
        	jugadorPixmap = Assets.JugadorIzquierda;
        if(_player.direccion == Jugador.ABAJO) 
        	jugadorPixmap = Assets.JugadorAbajo;
        if(_player.direccion == Jugador.DERECHA) 
        	jugadorPixmap = Assets.JugadorDerecha;        
        x = _player.x * 32;// + 16;
        y = _player.y * 32;// + 16;
        g.drawPixmap(jugadorPixmap,x ,y );
        
        Pixmap MuroPixmap = null;
        
        for(Muro murosEnLista : _ListaMuros){
        	
        	 if(murosEnLista.tipo== Muro.TIPO_1)
        		 MuroPixmap = Assets.Muro1;
             if(murosEnLista.tipo == Premios.TIPO_2)
            	 MuroPixmap = Assets.Muro2;
             if(murosEnLista.tipo == Premios.TIPO_3)
            	 MuroPixmap = Assets.Muro3;
              x = murosEnLista.x * 32;
              y = murosEnLista.y * 32;      
             g.drawPixmap(MuroPixmap, x, y);       
        }
        
        Pixmap EnemigoPixmap = null;
        
        for(Enemigo enemigoLista : _ListaEnemigos){
              EnemigoPixmap = Assets.Enemigo;
              x = enemigoLista.x * 32;
              y = enemigoLista.y * 32;      
             g.drawPixmap(EnemigoPixmap, x, y);       
        }
        Pixmap PremioPixmap = null;
        
        for(Premios premioLista : _ListaPremios){
       	 if(premioLista.tipo== Premios.TIPO_1)
       		PremioPixmap = Assets.Premio1;
            if(premioLista.tipo == Premios.TIPO_2)
            	PremioPixmap = Assets.Premio2;
            if(premioLista.tipo == Premios.TIPO_3)
            	PremioPixmap = Assets.Premio3;
             x = premioLista.x * 32;
             y = premioLista.y * 32;      
            g.drawPixmap(PremioPixmap, x, y);        
        }
        Pixmap Bomba=null;
        if(mundo.bombapuesta==true){
        	Bomba=Assets.bomba;
        	x=mundo._explosion.x * 32;
        	y=mundo._explosion.y * 32;
        	g.drawPixmap(Bomba, x, y);
        }
        Pixmap Explosion=null;
        if(mundo.dibujarExplosion==true){
        	for(Explosion _explosionesLista :_ListaExplosion){
        		Explosion=Assets.Explosion;
        		x=_explosionesLista.x*32;
        		y=_explosionesLista.y*32;
        		g.drawPixmap(Explosion, x, y);        
        	}
        }
    }
    
    private void drawReadyUI() {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.FlechaIzquierda, 0, 416);
        g.drawPixmap(Assets.FlechaDerecha, 64, 416);
        g.drawPixmap(Assets.bomba, 128, 416);
        g.drawPixmap(Assets.FlechaAbajo, 192, 416);
        g.drawPixmap(Assets.FlechaArriba, 256, 416);
    }
    
    private void drawRunningUI() {
        Graficos g = juego.getGraphics();
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.FlechaIzquierda, 0, 416);
        g.drawPixmap(Assets.FlechaDerecha, 64, 416);
        g.drawPixmap(Assets.bomba, 128, 416);
        g.drawPixmap(Assets.FlechaAbajo, 192, 416);
        g.drawPixmap(Assets.FlechaArriba, 256, 416);

    }
    
    private void drawPausedUI() {
        Graficos g = juego.getGraphics();
        if(Configuraciones.sonidoHabilitado=true)
        	g.drawPixmap(Assets.menupausa, 80, 100);
        else
        	g.drawPixmap(Assets.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();
        
        g.drawPixmap(Assets.finjuego, 62, 100);
      //  g.drawPixmap(Assets.botones, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }
    
    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
    
    @Override
    public void pause() {
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;
        
        if(mundo.finalJuego) {        	
            Configuraciones.addScore(mundo.puntuacion);
            Configuraciones.save(juego.getFileIO());           
        }
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void dispose() {
    	
    }
}