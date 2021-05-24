package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	Fondo fondo;
	Jugador jugador;
	List<Enemigo> enemigos;
	List<Disparo> disparosAEliminar;
	List<Enemigo> enemigosAEliminar;
	Temporizador temporizadorNuevoEnemigo;
	ScoreBoard scoreboard;
	boolean gameover;



	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(2f);

		inicializarJuego();

		Sound sound = Gdx.audio.newSound(Gdx.files.internal("melodiajuego.mp3"));
		sound.setLooping(sound.play(0.20f), true);

	}

	void inicializarJuego(){
		fondo = new Fondo();
		jugador = new Jugador();
		enemigos = new ArrayList<>();
		temporizadorNuevoEnemigo = new Temporizador(140);
		disparosAEliminar = new ArrayList<>();
		enemigosAEliminar = new ArrayList<>();
		scoreboard = new ScoreBoard();

		gameover = false;
	}

	void update() {
		Temporizador.tiempoJuego += 1;

		if (temporizadorNuevoEnemigo.suena()){
			enemigos.add(new Enemigo("alien"));
			enemigos.add(new Enemigo("alien2"));
			enemigos.add(new Enemigo("alien3"));
		}

		if(!gameover) jugador.update();

		for (Enemigo enemigo : enemigos) enemigo.update();

		for (Enemigo enemigo : enemigos) {
			for (Disparo disparo : jugador.disparos) {
				if (Utils.solapan(disparo.x, disparo.y, disparo.w, disparo.h, enemigo.x, enemigo.y, enemigo.w, enemigo.h)) {
					disparosAEliminar.add(disparo);
					enemigosAEliminar.add(enemigo);
					jugador.puntos++;
					break;
				}
			}

			if (!gameover && !jugador.muerto && Utils.solapan(enemigo.x, enemigo.y, enemigo.w, enemigo.h, jugador.x, jugador.y, jugador.w, jugador.h)) {
				jugador.morir();
				if (jugador.vidas == 0){
					gameover = true;
				}
			}

			if (enemigo.x < -enemigo.w) enemigosAEliminar.add(enemigo);
		}

		for (Disparo disparo : jugador.disparos)
			if (disparo.y > 720)
				disparosAEliminar.add(disparo);

		for (Disparo disparo : disparosAEliminar) jugador.disparos.remove(disparo);
		for (Enemigo enemigo : enemigosAEliminar) enemigos.remove(enemigo);
		disparosAEliminar.clear();
		enemigosAEliminar.clear();

		if(gameover) {
			int result = scoreboard.update(jugador.puntos);
			if(result == 1) {
				inicializarJuego();
			} else if (result == 2) {
				Gdx.app.exit();
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update();

		batch.begin();
		fondo.render(batch);
		jugador.render(batch);
		for (Enemigo enemigo : enemigos) enemigo.render(batch);
		font.draw(batch, "Vidas: " + jugador.vidas, 30, 660);
		font.draw(batch, "Puntos: " + jugador.puntos, 30, 700);

		if (gameover){
			scoreboard.render(batch, font);
		}
		batch.end();
	}
}


