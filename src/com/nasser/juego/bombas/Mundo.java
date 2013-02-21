package com.nasser.juego.bombas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.net.NetworkInfo.DetailedState;

import com.nasser.juego.bombas.PantallaJuego.EstadoJuego;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public Jugador _player;
    public Premios _premio;
    public Enemigo _enemigo;
    public Muro _muro;
    public Vacio _vacio;
    
    public Explosion _explosion;
    public boolean bombapuesta=false;
    public boolean bombaexplota=false;
    public boolean dibujarExplosion=false;
    
    public List<Premios> _listaPremios;
    public List<Enemigo> _listaEnemigos;
    public List<Muro> _listaMuros;
    public List<Vacio> _listaVacio;
    public List<Explosion> _listaExplosiones;
    
    float movimientoenemigo=0;
    float tiempobomba=0;
    float dibujadoExplosion=0;
    float tiempoTick = 0;
    float spawnEnemigo=0;
    static float tick = TICK_INICIAL;
    
    public boolean finalJuego = false;
    public int puntuacion = 0;
    public int tesoro;
    
    int mundo[][] = new int[MUNDO_ANCHO][MUNDO_ALTO];
   
    Random random = new Random();
    

    public Mundo() {
    	_listaMuros= new ArrayList<Muro>();
    	_listaPremios= new ArrayList<Premios>();
    	_listaEnemigos= new ArrayList<Enemigo>();
    	_listaVacio= new ArrayList<Vacio>();
        colocacion();
        creacionObjetos();
        _player= new Jugador(MUNDO_ANCHO/2,MUNDO_ALTO/2,0,0, MUNDO_ANCHO, MUNDO_ALTO);
        tesoro=_listaPremios.size();
    }

    private void colocacion() {
    	/* 1=> Jugador  1 casilla tablero   
    	 * 2=> Vacio 	1 casilla alrededor del jugador en  tablero
    	 * 3=> Tesoro 	5%  tablero
    	 * 4=> Muro  	resto tablero
    	 * 5=> Enemigo	5%  tablero
    	 */
    	mundo[MUNDO_ANCHO/2][MUNDO_ALTO/2]=1;
    	mundo[MUNDO_ANCHO/2+1][MUNDO_ALTO/2]=2;
    	mundo[MUNDO_ANCHO/2-1][MUNDO_ALTO/2]=2;
    	mundo[MUNDO_ANCHO/2][MUNDO_ALTO/2+1]=2;
    	mundo[MUNDO_ANCHO/2][MUNDO_ALTO/2-1]=2;
    	mundo[MUNDO_ANCHO/2+1][MUNDO_ALTO/2+1]=2;
    	mundo[MUNDO_ANCHO/2-1][MUNDO_ALTO/2-1]=2;
    	mundo[MUNDO_ANCHO/2-1][MUNDO_ALTO/2+1]=2;
    	mundo[MUNDO_ANCHO/2+1][MUNDO_ALTO/2-1]=2;
    	
    	int casillasEnemigo=(int)(5*MUNDO_ALTO*MUNDO_ANCHO)/100-2;
    	int casillasTesoro=(int)(5*MUNDO_ALTO*MUNDO_ANCHO)/100;
    	do{
    		int aleatorioX=(int) Math.floor(Math.random()*(MUNDO_ANCHO));
    		int aleatorioY=(int) Math.floor(Math.random()*(MUNDO_ALTO));
    		if(mundo[aleatorioX][aleatorioY]==0){
    			mundo[aleatorioX][aleatorioY]=3;
    			casillasTesoro=casillasTesoro-1;
    		}
    	}while(casillasTesoro!=0);
    	do{
    		int aleatorioX=(int) Math.floor(Math.random()*(MUNDO_ANCHO));
    		int aleatorioY=(int) Math.floor(Math.random()*(MUNDO_ALTO));
    		if(mundo[aleatorioX][aleatorioY]==0){
    			mundo[aleatorioX][aleatorioY]=5;
    			casillasEnemigo=casillasEnemigo-1;
    		}
    	}while(casillasEnemigo!=0);
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
            	if(mundo[x][y]==0){
        			mundo[x][y]=4;
        		}
            }
        }
        
    }
   
    private void creacionObjetos(){
    	for (int x = 0; x < MUNDO_ANCHO; x++) 
    	{
            for (int y = 0; y < MUNDO_ALTO; y++) 
            {
            	if(mundo[x][y]==2){
            		_vacio= new Vacio(x, y);
            		_listaVacio.add(_vacio);
            	}else if(mundo[x][y]==3){
            		
            		_premio= new Premios(x, y, (int) Math.floor(Math.random()*(2+1)));
            		_listaPremios.add(_premio);
            	}else  if(mundo[x][y]==4){
            		_muro= new Muro(x, y, (int) Math.floor(Math.random()*(2+1)));
            		_listaMuros.add(_muro);
            	}else if(mundo[x][y]==5){
            		_enemigo= new Enemigo(x, y, 0, 0, MUNDO_ANCHO, MUNDO_ALTO,(int) Math.floor(Math.random()*(2)));
            		//_enemigo= new Enemigo(x, y, 0, 0, MUNDO_ANCHO, MUNDO_ALTO,3);
            		_listaEnemigos.add(_enemigo);
            	}
            }
        }
    }
    public void movimiento(){	
    	int xanterior,yanterior;
   
    	xanterior=_player.x;
    	yanterior=_player.y;
    	
         if(_player.direccion == Jugador.ARRIBA){
        	 if(yanterior-1==-1){
        		 _player.y=0;
        	 }else{
        		 if(mundo[xanterior][yanterior-1]!=4){
	        		 _player.y=yanterior-1;
	        	 }
	        	 if(mundo[xanterior][yanterior-1]==3){
	        		cogerPremio();
	        	 }
        	 }
         }
         if(_player.direccion == Jugador.IZQUIERDA){
        	 if(xanterior-1==-1){
        		 _player.x=0;
        	 }else{
	        	 if(mundo[xanterior-1][yanterior]!=4){
	        		 _player.x=xanterior-1;
	        	 }
	        	 if(mundo[xanterior-1][yanterior]==3){
	        		 cogerPremio();
	         	 }
        	 }
          }
         if(_player.direccion == Jugador.ABAJO){
        	 if(yanterior+1==MUNDO_ALTO){
        		 _player.y=MUNDO_ALTO-1;
        	 }else{
        		 if(mundo[xanterior][yanterior+1]!=4){
        			 _player.y=yanterior+1;
        		 }
        		 if(mundo[xanterior][yanterior+1]==3){
        			 cogerPremio();
        		 }
        	 }
          }
         if(_player.direccion == Jugador.DERECHA){
        	 if(xanterior+1==MUNDO_ANCHO){
        		 _player.x=MUNDO_ANCHO-1;
        	 }else{
	        	 if(mundo[xanterior+1][yanterior]!=4){
	        		 _player.x=xanterior+1;
	        		 
	        	 }
	        	 if(mundo[xanterior+1][yanterior]==3){
	        		 cogerPremio();
	         	 }
        	 }
          }

         if(_player.x < 0 )
         	_player.x=0;
         if(_player.x > MUNDO_ANCHO )
        	 _player.x=MUNDO_ANCHO;
         if(_player.y < 0 )
          	_player.y=0;
          if(_player.y > MUNDO_ALTO )
         	 _player.y=MUNDO_ALTO;
    }

    public boolean muerte(){
    	boolean dead=false;
    	for(Enemigo _enemigoEnLista : _listaEnemigos){
    		if((_enemigoEnLista.x==_player.x)&&(_enemigoEnLista.y==_player .y))
    			dead= true;
    	}

    	return dead;
    }
    
    public boolean muerteBomba()
    {
    	boolean dead=false;
    	if (((_explosion.x==_player.x)&&(_explosion.y==_player.y))||
        		((_explosion.x+1==_player.x)&&(_explosion.y==_player.y))||
        		((_explosion.x-1==_player.x)&&(_explosion.y==_player.y))||
        		((_explosion.x==_player.x)&&(_explosion.y==_player.y+1))||
        		((_explosion.x==_player.x)&&(_explosion.y==_player.y-1)))
        		dead=true;	
    	return dead;
    }
    public void muerteEnemigo(){
    	List<Enemigo> _EnemigoAuxiliar= new ArrayList<Enemigo>(_listaEnemigos);
    	int indexLista=0;
    	for(Enemigo _enemigosEnLista : _listaEnemigos){
    		if (((_explosion.x==_enemigosEnLista.x)&&(_explosion.y==_enemigosEnLista.y))||
            		((_explosion.x+1==_enemigosEnLista.x)&&(_explosion.y==_enemigosEnLista.y))||
            		((_explosion.x-1==_enemigosEnLista.x)&&(_explosion.y==_enemigosEnLista.y))||
            		((_explosion.x==_enemigosEnLista.x)&&(_explosion.y==_enemigosEnLista.y+1))||
            		((_explosion.x==_enemigosEnLista.x)&&(_explosion.y==_enemigosEnLista.y-1))){
    			_EnemigoAuxiliar.remove(indexLista);
					puntuacion += 100;
					}
    		
    	indexLista=indexLista+1;
    	}
    	_listaEnemigos=new ArrayList<Enemigo>(_EnemigoAuxiliar);
    }
    
    public void ponerBomba(){
    	_explosion= new Explosion(_player.x,_player.y);
    	bombapuesta=true;
    	tiempobomba=0;
    }
    public void cogerPremio(){
    	int indexLista=0;
    	List<Premios> _PremioAuxiliar= new ArrayList<Premios>(_listaPremios);
    	for(Premios _premioLista : _listaPremios ){
    		if((_premioLista.x==_player.x)&&(_premioLista.y==_player.y)){
    				mundo[_premioLista.x][_premioLista.y]=2;
    				if(_premioLista.tipo==Premios.TIPO_1)
    					puntuacion += 200;
    				if(_premioLista.tipo==Premios.TIPO_2)
    					puntuacion += 300;
    				if(_premioLista.tipo==Premios.TIPO_3)
    					puntuacion += 400;
    				_PremioAuxiliar.remove(indexLista);
    				tesoro=tesoro-1;
    				
    			}
    			indexLista++;
    		}
    	_listaPremios=new ArrayList<Premios>(_PremioAuxiliar);
    	}
    public void dibujadoExplosion(){
    	
    	_listaExplosiones= new ArrayList<Explosion>();
    	_listaExplosiones.add(_explosion);
    	_listaExplosiones.add(new Explosion(_explosion.x, _explosion.y-1));
    	_listaExplosiones.add(new Explosion(_explosion.x, _explosion.y+1));
    	_listaExplosiones.add(new Explosion(_explosion.x-1, _explosion.y));
    	_listaExplosiones.add(new Explosion(_explosion.x+1, _explosion.y));
    	dibujadoExplosion=0;
    	dibujarExplosion=true;
    }
    public void explosionBomba(){
    	int indexLista=0;
    	List<Muro> _listaAuxiliar= new ArrayList<Muro>(_listaMuros);
    	List<Muro> _listaAuxiliar2 = null;
    	
    	if(mundo[_explosion.x-1][_explosion.y]==4){
    		_listaAuxiliar2=new ArrayList<Muro>(_listaAuxiliar);
    		for(Muro _muroLista : _listaAuxiliar ){
    			if((_muroLista.x==_explosion.x-1)&&(_muroLista.y==_explosion.y)){
    				_muroLista.quitarVida();
    				if(_muroLista.vida==0){
    					mundo[_muroLista.x][_muroLista.y]=2;
    					_listaAuxiliar2.remove(indexLista);
    					puntuacion += 10;
    					}	
    			}
    			indexLista++;
    		}
    	}
    	indexLista=0;
    	if(_listaAuxiliar2!=null)
    	_listaAuxiliar=new ArrayList<Muro>(_listaAuxiliar2);
    	if(mundo[_explosion.x][_explosion.y+1]==4){
    		_listaAuxiliar2=new ArrayList<Muro>(_listaAuxiliar);
    		for(Muro _muroLista : _listaAuxiliar ){
    			if((_muroLista.x==_explosion.x)&&(_muroLista.y==_explosion.y+1)){
    				_muroLista.quitarVida();
    				if(_muroLista.vida==0){
    					mundo[_muroLista.x][_muroLista.y]=2;
    					_listaAuxiliar2.remove(indexLista);
    					puntuacion += 10;}
    			}
    			indexLista++;
    		}
    	}
    	indexLista=0;
    	if(_listaAuxiliar2!=null)
    	_listaAuxiliar=new ArrayList<Muro>(_listaAuxiliar2);
    	if(mundo[_explosion.x+1][_explosion.y]==4){
    		_listaAuxiliar2=new ArrayList<Muro>(_listaAuxiliar);
    		for(Muro _muroLista : _listaAuxiliar ){
    			if((_muroLista.x==_explosion.x+1)&&(_muroLista.y==_explosion.y)){
    				_muroLista.quitarVida();
    				if(_muroLista.vida==0){
    					mundo[_muroLista.x][_muroLista.y]=2;
    					_listaAuxiliar2.remove(indexLista);
    					puntuacion += 10;}
    			}
    			indexLista++;
    		}
    	}
    	indexLista=0;
    	if(_listaAuxiliar2!=null)
    	_listaAuxiliar=new ArrayList<Muro>(_listaAuxiliar2);
    	if(mundo[_explosion.x][_explosion.y-1]==4){
    		_listaAuxiliar2=new ArrayList<Muro>(_listaAuxiliar);
    		for(Muro _muroLista : _listaAuxiliar ){
    			if((_muroLista.x==_explosion.x)&&(_muroLista.y==_explosion.y-1)){
    				_muroLista.quitarVida();
    				if(_muroLista.vida==0){
    					mundo[_muroLista.x][_muroLista.y]=2;
    					_listaAuxiliar2.remove(indexLista);
    					puntuacion += 10;}
    			}
    			indexLista++;
    		}
    	}
    	if(_listaAuxiliar2!=null)
    	_listaMuros= new ArrayList<Muro>(_listaAuxiliar2);
    	
    	dibujadoExplosion();
    	muerteEnemigo();
    	finalJuego=muerteBomba();
    			
    }
    
    
    public void update(float deltaTime) {
        if (finalJuego)
            return;
        tiempoTick += deltaTime;
       
        movimientoenemigo +=deltaTime;
        if(movimientoenemigo>1){
        	for(Enemigo _enemigoEnLista : _listaEnemigos)
        		_enemigoEnLista.movimiento(_player.x, _player.y);
        	movimientoenemigo=0;
        }
        
        tiempobomba += deltaTime;
        if(tiempobomba>3){
        	if(bombapuesta){
            	//finalJuego=muerteBomba();
            	//IF TIEMPO HA PASADO DOS SEGUNDOS
            	explosionBomba();
            	bombapuesta=false;
            }
        }
        dibujadoExplosion +=deltaTime;
        if(dibujadoExplosion>0.75){
        	if(dibujarExplosion)
        		dibujarExplosion=false;
        }
        spawnEnemigo +=deltaTime;
        if(spawnEnemigo>20){
        	int aleatorioX;
        	int aleatorioY;
        	do{
        	 aleatorioX=(int) Math.floor(Math.random()*(MUNDO_ANCHO));
    		 aleatorioY=(int) Math.floor(Math.random()*(MUNDO_ALTO));
        	}while((_player.x!=aleatorioX)||(_player.y!=aleatorioY));
    		
        	_enemigo= new Enemigo(aleatorioX,aleatorioX, 0, 0, MUNDO_ANCHO, MUNDO_ALTO,(int) Math.floor(Math.random()*(2)));
        	_listaEnemigos.add(_enemigo);
        	spawnEnemigo=0;
        }
        while (tiempoTick > tick) {
        	tiempoTick -= tick;
        	//Que cada segundo se ejecute esta instruccion
           
            if (muerte()) { 
            	finalJuego = true;                
               return;
            }
            if(tesoro==0)
            	finalJuego=true;
            
            if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
            }

        }
    }
}
