package com.nasser.juego.bombas;


import com.nasser.juego.marco.Graficos;
import com.nasser.juego.marco.Juego;
import com.nasser.juego.marco.Pantalla;
import com.nasser.juego.marco.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.Fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assets.Logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.menupausa2 = g.newPixmap("menupausa2.png", PixmapFormat.ARGB4444);
        
        Assets.JugadorAbajo=g.newPixmap("JugadorAbajo.png", PixmapFormat.ARGB4444);
        Assets.JugadorArriba=g.newPixmap("JugadorArriba.png", PixmapFormat.ARGB4444);
        Assets.JugadorDerecha=g.newPixmap("jugadorDerecha.png", PixmapFormat.ARGB4444);
        Assets.JugadorIzquierda=g.newPixmap("jugadorIzquierda.png", PixmapFormat.ARGB4444);
        
        Assets.FlechaArriba=g.newPixmap("arriba.png", PixmapFormat.ARGB4444);
        Assets.FlechaAbajo=g.newPixmap("abajo.png", PixmapFormat.ARGB4444);
        Assets.FlechaDerecha=g.newPixmap("derecha.png", PixmapFormat.ARGB4444);
        Assets.FlechaIzquierda=g.newPixmap("izquierda.png", PixmapFormat.ARGB4444);
        
        Assets.Premio1=g.newPixmap("moneda1.png", PixmapFormat.ARGB4444);
        Assets.Premio2=g.newPixmap("moneda2.png", PixmapFormat.ARGB4444);
        Assets.Premio3=g.newPixmap("moneda3.png", PixmapFormat.ARGB4444);
        
        Assets.Muro1=g.newPixmap("Muro1.png", PixmapFormat.ARGB4444);
        Assets.Muro2=g.newPixmap("Muro2.png", PixmapFormat.ARGB4444);
        Assets.Muro3=g.newPixmap("Muro3.png", PixmapFormat.ARGB4444);
        
        Assets.Enemigo=g.newPixmap("enemigo.png", PixmapFormat.ARGB4444);
        
        Assets.Explosion=g.newPixmap("Explosion.png", PixmapFormat.ARGB4444);
        Assets.bomba=g.newPixmap("bomba.png", PixmapFormat.ARGB4444);
        
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);     
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        

        
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.ataque = juego.getAudio().nuevoSonido("ataque.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");
        
        
        Configuraciones.cargar(juego.getFileIO());       
        juego.setScreen(new MainMenuScreen(juego));
    }

    @Override
    public void present(float deltaTime) {

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