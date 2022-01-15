/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snake;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
/**
 *
 * @author Franklin
 */
public class Snake {
    private Escenario myEscenario;
    private Vector2 cabeza;
    private Array<Vector2> cola;
    private Vector2 direccionSnake;
    public byte vidas = 2;
    private long startTime;
    private long frecuenciaEnMils = 100; // in millis

    public int tamanioSnake = 2;

    public Snake(Escenario gameScreen){
        this.myEscenario = gameScreen;
        cola = new Array<Vector2>();
        iniciarSnake();
    }

    public void update(){
        queryInput();
        //mueve a la snake cada cierto tiempo mientras game over = false
        if(!myEscenario.gameOver && TimeUtils.timeSinceMillis(startTime) >= frecuenciaEnMils){ 
            startTime = System.currentTimeMillis();
            move();
        }
    }

    private void queryInput(){
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && (direccionSnake.y == 0) && cola.get(cola.size - 1).x != cabeza.x){
                direccionSnake.set(0, 1); 
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && direccionSnake.y == 0 && cola.get(cola.size - 1).x != cabeza.x){
                direccionSnake.set(0, -1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && direccionSnake.x == 0 && cola.get(cola.size - 1).y != cabeza.y){
                direccionSnake.set(-1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && direccionSnake.x == 0 && cola.get(cola.size - 1).y != cabeza.y){
                direccionSnake.set(1, 0);
        }
    }

    private void move(){
    
        cabeza.add(direccionSnake); // cambia la direccion de la snake

        // verifica si choca con el muro o con su cola
        if(myEscenario.matriz[(int)cabeza.x][(int)cabeza.y] == Objeto.MURO || myEscenario.matriz[(int)cabeza.x][(int)cabeza.y] == Objeto.SNAKE){    
            if(myEscenario.matriz[(int)cabeza.x][(int)cabeza.y] == Objeto.MURO){
                if(direccionSnake.y == 0){
                    direccionSnake.set(-1,0);
                    cabeza.add(direccionSnake);
                }
            }
            vidas--;
            if(vidas == 0){
                myEscenario.gameOver = true;
                vidas = 2;
            }else{
                myEscenario.restart();
                iniciarSnake();
            }
            return;
        }

        // verifica si come la manazana
        if(myEscenario.matriz[(int)cabeza.x][(int)cabeza.y] == Objeto.APPLE){ 
            myEscenario.createComida();
            tamanioSnake++;
            incrementarVelocidad(); // incrementa su nivel segun su tamanio
            cola.add(new Vector2(cabeza.x - direccionSnake.x, cabeza.y - direccionSnake.y));
            myEscenario.matriz[(int)cabeza.x][(int)cabeza.y] = Objeto.SNAKE; 
            return;
        }

        myEscenario.matriz[(int)cabeza.x][(int)cabeza.y] = Objeto.SNAKE; // actualiza la matriz con la cabeza

        // actualizar matriz cola->vacio
        myEscenario.matriz[(int)cola.get(0).x][(int)cola.get(0).y] = Objeto.VACIO; 
        cola.removeIndex(0);
        cola.add(new Vector2(cabeza.x - direccionSnake.x, cabeza.y - direccionSnake.y));
    }

    public void iniciarSnake(){
        cabeza = new Vector2(myEscenario.tamanioMatriz/2 + 1, myEscenario.tamanioMatriz/2);
       
        cola.clear();
       // cola.add(new Vector2(myEscenario.tamanioMatriz/2 - 1, myEscenario.tamanioMatriz/2));
        cola.add(new Vector2(myEscenario.tamanioMatriz/2, myEscenario.tamanioMatriz/2));
        direccionSnake = new Vector2(1, 0); // direccion por defecto ->
        tamanioSnake = 2;
        startTime = System.currentTimeMillis();
    }

    private void incrementarVelocidad() {
         if(tamanioSnake == 7){
             frecuenciaEnMils = 80;
             System.out.println("se incremento la velocidad 1");
         } 
         if(tamanioSnake == 10){
             frecuenciaEnMils = 70;
              System.out.println("se incremento la velocidad 2");
         } 
         if(tamanioSnake == 15){
             frecuenciaEnMils = 60;
              System.out.println("se incremento la velocidad 3");
         } 
    }
}
