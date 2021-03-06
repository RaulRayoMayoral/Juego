package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    Animacion animacion = new Animacion(6f, true,  "cohete.png","cohete2.png","cohete1.png");
    float x, y, w, h, v;
    List<Disparo> disparos = new ArrayList<>();
    int vidas = 4;
    int puntos = 0;
    boolean muerto = false;
    Temporizador temporizadorFireRate = new Temporizador(20);
    Temporizador temporizadorRespawn = new Temporizador(120, false);
    public Sound disparo = Gdx.audio.newSound(Gdx.files.internal("disparolaser.mp3"));
    public Sound gameover = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
    Jugador() {
        x = 100;
        y = 100;
        w = 150 ;
        h = 100;
        v = 5;
    }

    void update() {
        for (Disparo disparo : disparos) disparo.update();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += v;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= v;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += v;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= v;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && temporizadorFireRate.suena() && !muerto) {
            disparos.add(new Disparo(x + w / 2, y + h));
            disparo.play();
        }

        if (x < 0) x = 0;

        if (temporizadorRespawn.suena()) {
            muerto = false;
        }
    }

    void render(SpriteBatch batch) {
        if (muerto) batch.setColor(1, 1, 1, 0.25f);
        batch.draw(animacion.getFrame(Temporizador.tiempoJuego), x, y, w, h);
        if (muerto) batch.setColor(1, 1, 1, 1);

        for (Disparo disparo : disparos) disparo.render(batch);
    }

    public void morir() {
        vidas--;
        muerto = true;
        temporizadorRespawn.activar();
        gameover.play();
    }
}
