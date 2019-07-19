package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Sniper extends Sprite {
                                                //frame size -->> 48 x 47
    private Texture sniper_run;
    private Texture sniper_shoot;
    private Texture sniper_die;

    private Animation<TextureRegion> running;
    private Animation<TextureRegion> aiming;
    private Animation<TextureRegion> shooting;
    private Animation<TextureRegion> dying;

    public World world;
    public Body sniper_body;
    private PlayScreen screen;

    float x, y;

    float engage_distance;
    float shooting_distance;
    float elspsedTime;

    boolean isDead;

    public Sniper(World world, PlayScreen screen, float x, float y) {

        this.world = world;
        this.screen = screen;

        this.x = x;
        this.y = y;

        engage_distance = 360;
        shooting_distance = 150f;

        elspsedTime = 0f;

        isDead = false;

        sniper_shoot = new Texture("Sprites/Enemies/Sniper/shoot.png");
        sniper_run = new Texture("Sprites/Enemies/Sniper/run.png");
        sniper_die = new Texture("Sprites/Enemies/Sniper/die.png");

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=0; i<12; i++)
            frames.add(new TextureRegion(sniper_run, i*48, 0, 48, 47));
        running = new Animation<TextureRegion>(1f/15f, frames);
        frames.clear();

        /*for(int i=0; i<5; i++)
            frames.add(new TextureRegion(sniper, i*48, 47*2, 48 ,47));
        aiming = new Animation<TextureRegion>(1f/8f, frames);
        frames.clear();*/

        for(int i=0; i<13; i++)
            frames.add(new TextureRegion(sniper_shoot, i*48, 0, 48, 47));
        shooting = new Animation<TextureRegion>(1f/10f, frames);
        frames.clear();

        for(int i=0; i<12; i++)
            frames.add(new TextureRegion(sniper_die, i*48, 0, 48, 47));
        dying = new Animation<TextureRegion>(1f/15f, frames);
        frames.clear();

        defineSniper();

        setBounds(0, 0, 48f, 47f);
        setRegion(new TextureRegion(sniper_run, 0, 0, 48, 47));
        setPosition(sniper_body.getPosition().x - getWidth()/4, sniper_body.getPosition().y - getHeight()/4);

    }

    public void update(float dt, float player_body_x) {

        TextureRegion region;

        if(!isDead) {
            //System.out.println(sniper_body.getPosition().x - player_body_x);
            if (sniper_body.getPosition().x - player_body_x < engage_distance) {
                //System.out.println("SSSSSS");
                if (sniper_body.getPosition().x - player_body_x < shooting_distance) {
                    System.out.println("shoot");
                    sniper_body.setLinearVelocity(0, 0);
                    region = (shooting.getKeyFrame(elspsedTime, true));
                    if (shooting.getKeyFrameIndex(elspsedTime) == 5) {
                        //shoot 1 bullet
                        shoot();
                    }
                } else {
                    System.out.println("run");
                    region = (running.getKeyFrame(elspsedTime, true));
                    sniper_body.applyLinearImpulse(new Vector2(-50f,0),sniper_body.getWorldCenter(),true);
                    //setPosition(((sniper_body.getPosition().x * MyGdxGame.ppm) - 48f/2f),((sniper_body.getPosition().y * MyGdxGame.ppm) - 47f/2f));
                    setPosition(sniper_body.getPosition().x - getWidth()/4, sniper_body.getPosition().y - getHeight()/4);
                }
            }
            else {
                System.out.println("die");
                region = (dying.getKeyFrame(elspsedTime, true));
            }
            elspsedTime += dt;
        }
        else {

            region = (dying.getKeyFrame(elspsedTime, false));
            if(dying.getKeyFrameIndex(elspsedTime) == 11) {
                //remove body
            }
            elspsedTime += dt;
        }
        setRegion(region);
    }

    public void shoot() {

    }

    public void defineSniper() {

        BodyDef bdef = new BodyDef();
        bdef.position.set((x + 48f/2f), (y + 47f/2f));
        bdef.type = BodyDef.BodyType.DynamicBody;
        sniper_body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 20);
        fdef.shape = shape;
        sniper_body.createFixture(fdef);
    }

}