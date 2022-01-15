/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/**
 *
 * @author Franklin
 */
public class Renderer {
     private Escenario myEscenario;
    private ShapeRenderer shapeRenderer;

    public Renderer(Escenario newEscenario){
        this.myEscenario = newEscenario;
        shapeRenderer = new ShapeRenderer();
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        dibujarPared();
        dibujarSnake();
        dibujarComida();

        shapeRenderer.end();
    }
    private void dibujarSnake() {
        for (int x = 1; x < myEscenario.tamanioMatriz - 1; x++) {
            for (int y = 1; y < myEscenario.tamanioMatriz - 1; y++) {
                if(myEscenario.matriz[x][y] == Objeto.SNAKE){
                     shapeRenderer.setColor(Color.GREEN);
                    shapeRenderer.rect(x * myEscenario.escala, y * myEscenario.escala, myEscenario.escala, myEscenario.escala);
                }    
            }
        }
    }

    public void dispose(){
        shapeRenderer.dispose();
    }

    private void dibujarPared() {
        shapeRenderer.rect(0,0, myEscenario.escala, Gdx.graphics.getHeight()); // Left
        shapeRenderer.rect(Gdx.graphics.getWidth() - myEscenario.escala, 0, myEscenario.escala, Gdx.graphics.getHeight()); // Right
        shapeRenderer.rect(myEscenario.escala, 0, Gdx.graphics.getWidth() - myEscenario.escala, myEscenario.escala);
        shapeRenderer.rect(myEscenario.escala, Gdx.graphics.getHeight() - myEscenario.escala, Gdx.graphics.getWidth() - myEscenario.escala, myEscenario.escala);

    }


    private void dibujarComida() {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(myEscenario.comida.x * myEscenario.escala, myEscenario.comida.y * myEscenario.escala, myEscenario.escala, myEscenario.escala);
        shapeRenderer.setColor(Color.GOLD);
    }
}
