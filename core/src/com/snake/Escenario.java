/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Franklin
 */
public class Escenario implements Screen{
    public Objeto[][] matriz;
    public Vector2 comida;
    public int tamanioMatriz = 25;
    public int escala = Gdx.graphics.getWidth()/tamanioMatriz; 
    private Snake snake;
    private Renderer renderer; //lapiz
    private Vector2 nextPosComida;
    private MySnakeGame game;

    private FreeTypeFontGenerator fontGenerator80;
    private FreeTypeFontGenerator fontGenerator30;
    private FreeTypeFontGenerator fontGenerator15;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font80;
    private BitmapFont font30;
    private BitmapFont font15;
    private float gameOverWidth;
    private float inputRTextWidth;
    private float snakeLengthTextWidth;
    public boolean gameOver = false;

    public Escenario(MySnakeGame game){
        matriz = new Objeto[tamanioMatriz][tamanioMatriz];
        snake = new Snake(this);
        renderer = new Renderer(this);
        nextPosComida = new Vector2();
        this.game = game;
        restart();

        GlyphLayout layout = new GlyphLayout();
        fontGenerator80 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/meek.ttf"));
        fontGenerator30 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/mm10.ttf"));
        fontGenerator15 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/mm10.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 80;
        font80 = fontGenerator80.generateFont(fontParameter);
        font80.setColor(Color.RED);
        layout.setText(font80, "GAME OVER");
        gameOverWidth = layout.width;
        fontParameter.size = 30;
        font30 = fontGenerator30.generateFont(fontParameter);
        font30.setColor(Color.GOLD);
        layout.setText(font30, "PRESS R TO RESTART");
        inputRTextWidth = layout.width;
        fontParameter.size = 15;
        font15 = fontGenerator15.generateFont(fontParameter);
        font15.setColor(Color.RED);
        layout.setText(font15, "SNAKE'S LENGTH: 10");
        snakeLengthTextWidth = layout.width;
    }

    @Override
    public void render(float delta) {
        renderer.render();
        snake.update();
        showScoreAndLife();
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            restart();
            snake.iniciarSnake();
        } 

        if(gameOver){
            showGameOver();
           
        }
    }

    public void createComida(){
        do {
            nextPosComida = new Vector2(MathUtils.random(1, tamanioMatriz-2), MathUtils.random(1, tamanioMatriz-2));
        } while (matriz[(int)nextPosComida.x][(int)nextPosComida.y] != Objeto.VACIO);

        comida = nextPosComida;
        matriz[(int)comida.x][(int)comida.y] = Objeto.APPLE;
    }

    public void restart(){
        // cargando la matriz
        for (int x = 0; x < tamanioMatriz; x++) {
            for (int y = 0; y < tamanioMatriz; y++) {
                if(x == 0 || x == tamanioMatriz-1 || y == 0 || y == tamanioMatriz-1){ // insertando los muros
                    matriz[x][y] = Objeto.MURO;
                } else {
                    matriz[x][y] = Objeto.VACIO; 
                }
            }
        }
        //cargando snake 2 por defaul
       // matriz[tamanioMatriz/2-1][tamanioMatriz/2] = Objeto.SNAKE;
        matriz[tamanioMatriz/2][tamanioMatriz/2] = Objeto.SNAKE;
        matriz[tamanioMatriz/2+1][tamanioMatriz/2] = Objeto.SNAKE;
      
        gameOver = false;

        createComida();
    }
    
    
    private void showGameOver() {
         game.batch.begin();
         font80.draw(game.batch, "GAME OVER", Gdx.graphics.getWidth()/2f - gameOverWidth/2f, Gdx.graphics.getHeight() * 0.7f);
         font30.draw(game.batch, "PRESS R TO RESTART", Gdx.graphics.getWidth()/2f - inputRTextWidth/2f, Gdx.graphics.getHeight() * 0.4f);
         font15.draw(game.batch, "SNAKE'S LENGTH: " + snake.tamanioSnake, Gdx.graphics.getWidth()/2f - snakeLengthTextWidth/2f, Gdx.graphics.getHeight() * .6f);
         game.batch.end();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        font15.dispose();
        font30.dispose();
        font80.dispose();
        fontGenerator30.dispose();
        fontGenerator80.dispose();
        fontGenerator15.dispose();
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    private void showScoreAndLife() {
         game.batch.begin();
          font15.draw(game.batch, "Score: " + (snake.tamanioSnake-2)*10, Gdx.graphics.getWidth()/2f -60, Gdx.graphics.getHeight() * .95f);
          font15.draw(game.batch, "Vidas: " + snake.vidas, Gdx.graphics.getWidth()/6f -60, Gdx.graphics.getHeight() * .95f);
        game.batch.end();
    }

}
