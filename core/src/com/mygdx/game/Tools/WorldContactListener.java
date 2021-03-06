package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Bullet;
import com.mygdx.game.Sprites.Enemies.Sniper;
import com.mygdx.game.Sprites.EnemyBullet;
import com.mygdx.game.Sprites.InteractiveTileObject;
import com.mygdx.game.Sprites.Player;

import java.io.IOException;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();

        //System.out.println("Tashfee");

        int  col = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (col){
// bullet and ground

            case MyGdxGame.BULLET_BIT | MyGdxGame.ENEMY_BIT :

                if(fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT){
                    ((Bullet)fixA.getUserData()).hitenemy();
                    //((Sniper)fixB.getUserData()).die();
                    ((Sniper)fixB.getUserData()).die();
                }
                else{
                    ((Bullet)fixB.getUserData()).hitenemy();
                    ((Sniper)fixA.getUserData()).die();
                }
                break;

            case MyGdxGame.BULLET_BIT | MyGdxGame.GROUND_BIT:

                if (fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT) {
                    ((Bullet)fixA.getUserData()).hitenemy();
                } else {
                    ((Bullet)fixB.getUserData()).hitenemy();
                }
                break;

            case MyGdxGame.ENEMYBULLET_BIT | MyGdxGame.GROUND_BIT:

                if(fixA.getFilterData().categoryBits == MyGdxGame.ENEMYBULLET_BIT){
                    ((EnemyBullet)fixA.getUserData()).hitenemy();
                }else {
                    ((EnemyBullet)fixB.getUserData()).hitenemy();
                }
                break;

            case MyGdxGame.ENEMYBULLET_BIT | MyGdxGame.PLAYER_BIT:

                if(fixA.getFilterData().categoryBits == MyGdxGame.ENEMYBULLET_BIT){
                    ((EnemyBullet)fixA.getUserData()).hitenemy();
                    try {
                        ((Player)fixB.getUserData()).life();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    ((EnemyBullet)fixB.getUserData()).hitenemy();
                    try {
                        ((Player)fixA.getUserData()).life();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
