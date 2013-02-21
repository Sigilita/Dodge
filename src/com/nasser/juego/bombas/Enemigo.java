package com.nasser.juego.bombas;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Enemigo {
	    public int x,y;
	    public int direccion;
	    public int minx,miny,maxx,maxy;
	    public boolean ChangeAxis;
	    public Calendar calendar;
    	int t= 0;
	    public Enemigo(int x, int y,int minx,int miny, int maxx, int maxy,int direccion) {        
	    	this.x=x;
	    	this.y=y;
	    	this.minx =minx ;
	    	this.miny =miny ;
	    	this.maxx =maxx ;
	    	this.maxy =maxy ;
	    	this.direccion=direccion;
	    	calendar=Calendar.getInstance();
	    }
	    
	   public void movimiento(int xPlayer, int yPlayer){
		   if(direccion==0){
			   movimiento1(xPlayer, yPlayer);
		   }else if (direccion ==1)
			   movimiento2();
		   else
			   movimiento3();
	   }
	    
	    
	    private void movimiento1(int xPlayer, int yPlayer) {
	       int xanterior,yanterior;
	       xanterior =this.x;
	       yanterior=this.y;
	       
	       if((Math.abs(xanterior-xPlayer)<Math.abs(yanterior -yPlayer)&& (ChangeAxis ==false))||
	    	  (Math.abs(xanterior-xPlayer)>Math.abs(yanterior -yPlayer)&& ChangeAxis ==true)){
	    	   if(ChangeAxis ==false){
	    		   if((xanterior-xPlayer )<0)
	    			   this.x=xanterior +1;
	    		   else if((xanterior-xPlayer )>0)
	    			   this.x=xanterior-1;
	    		   else{
	    			   if((yanterior-yPlayer )<0)
		    			   this.y=yanterior +1;
		    		   else if ((yanterior-yPlayer )>0)
		    			   this.y=yanterior-1;
	    		   }
	    	   ChangeAxis =true;
	    	   }else{
	    		   if((xanterior-xPlayer)<0)
	    			   this.x=xanterior +1;
	    		   else if((xanterior-xPlayer )>0)
	    			   this.x=xanterior-1;
	    		   else{
	    			   if((yanterior-yPlayer )<0)
		    			   this.y=yanterior +1;
		    		   else if ((yanterior-yPlayer )>0)
		    			   this.y=yanterior-1;
	    		   }
	    	   ChangeAxis =false;  
	    	   }
	       }else if((Math.abs(xanterior-xPlayer)<Math.abs(yanterior -yPlayer)&& ChangeAxis ==true)||
	    		    (Math.abs(xanterior-xPlayer)>Math.abs(yanterior -yPlayer)&& ChangeAxis ==false)){
	    	   if(ChangeAxis ==false){
	    		   if((yanterior-yPlayer )<0)
	    			   this.y=yanterior +1;
	    		   else if ((yanterior-yPlayer )>0)
	    			   this.y=yanterior-1;
	    		   else
	    		   {
	    			  if((xanterior-xPlayer )<0)
	    				  this.x=xanterior +1;
		    		  else
		    			  this.x=xanterior-1;
	    		   }
	    	   ChangeAxis =true;
	    	   }else{
	    		   if((yanterior-yPlayer)<0)
	    			   this.y=yanterior +1;
	    		   else if ((yanterior-yPlayer )>0)
	    			   this.y=yanterior-1;
	    		   else
	    		   {
	    			  if((xanterior-xPlayer )<0)
	    				  this.x=xanterior +1;
		    		  else
		    			  this.x=xanterior-1;
	    		   }
	    	   ChangeAxis =false;  
	    	   }
	       }else{
	    	   Random n = new Random();
	    	   if(n.nextInt(101)%2==0){
	    		   if((xanterior-xPlayer)<0)
	    			   this.x=xanterior+1;
	    		   else
	    			   this.x=xanterior-1;
	    	   }else{
	    		   if((yanterior-yPlayer)<0)
	    			   this.y=yanterior +1;
	    		   else
	    			   this.y=yanterior-1;
	    	   }
	       }
	       
	       
	        if(this.x < minx )
	        	this.x=maxx-1;
	        if(this.x > maxx )
	        	this.x=minx+1;
	        if(this.y < miny)
	        	this.y=maxy-1;
	        if(this.y > maxy )
	        	this.y=minx+1;
	    }
	    private  void movimiento3(){
	    	float a=1;
	    	float b=(float) 2.5;
	    	t=t+1;
	    	if(t>360)
	    		t=0;

	    	//int t=0;
	    	x=5+(int) ((a-b)*Math.cos(t*100)+b*Math.cos(100*t*((a/b)-1)));
	    	y=4+(int) ((a-b)*Math.sin(t*100)-b*Math.sin(100*t*((a/b)-1)));
	    
    }
	    
	    private void movimiento2(){
	    	int dir= (int) Math.floor(Math.random()*(100+100+1)-100);
	    	if((dir%2==0)&&(dir<0))
	    		x=x+1;
	    	else if((dir%2==0)&&(dir>0))
	    		x=x-1;
	    	else if((dir%2!=0)&&(dir<0))
	    		y=y+1;
	    	else
	    		y=y-1;
	    	 if(this.x < minx )
		        	this.x=maxx-1;
		        if(this.x > maxx )
		        	this.x=minx+1;
		        if(this.y < miny)
		        	this.y=maxy-1;
		        if(this.y > maxy )
		        	this.y=minx+1;
	    }
	    
	   /* private void movimiento3(int xPlayer, int yPlayer){
	    	x=(int) Math.floor(Math.random()*(maxx -minx-1)+minx);
	    	y=(int) Math.floor(Math.random()*(maxy -miny-1)+miny);
	    	movimiento1(xPlayer, yPlayer);
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
