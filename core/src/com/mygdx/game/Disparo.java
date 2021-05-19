package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Disparo {
    static Texture texture = new Texture("bala.png");
    float x, y, w, h, v;

    Disparo(float xNave, float yNave) {
        w = 10;
        h = 50;
        x = xNave - w/2;
        y = yNave;
        v = 12;
    }

    void update() {
        y += v;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, w, h);
    }
}
