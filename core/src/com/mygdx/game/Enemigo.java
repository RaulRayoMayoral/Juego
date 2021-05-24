package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Enemigo {
    Texture texture;
    float x, y, w, h, vx, vy;
    Temporizador cambioVelocidad = new Temporizador(60);

    Enemigo(String tipo) {
        if (tipo.equals("alien")) {
            y = 640;
            x = Utils.random.nextInt(720);
            w = 80;
            h = 80;
            vx = -2;
            vy = 0;
            this.texture = new Texture("alien.png");
        } else if (tipo.equals("alien2")) {
            y = 640;
            x = Utils.random.nextInt(720);
            w = 80;
            h = 80;
            vx = -3;
            vy = 1;
            this.texture = new Texture("alien2.png");
        } else if (tipo.equals("alien3")) {
            y = 640;
            x = Utils.random.nextInt(720);
            w = 80;
            h = 80;
            vx = -2;
            vy = 1;
            this.texture = new Texture("alien3.png");
        }
    }

        public void update () {
            if (texture != null) {
                y -= vy;
                x -= vx;
            }

            if (cambioVelocidad.suena()) {
                vy = Utils.random.nextInt(3) + 1;
                vx = (Utils.random.nextInt(3) - 1);
            }
        }


        void render (SpriteBatch batch){
            if (texture != null) {
                batch.draw(texture, x, y, w, h);
            }
        }

}
