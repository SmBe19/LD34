package com.smeanox.games.ld34.world;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.smeanox.games.ld34.Consts;

/**
 * Comment
 */
public class CoinParticle extends ParticleSystem.Particle {

    private World world;

    public CoinParticle(World world, ParticleSystem particleSystem, float time, float x, float y, float vx, float vy) {
        super(particleSystem, time, x, y, vx, vy);
        this.world = world;
    }

    @Override
    public void doPhysics(float delta){
        super.doPhysics(delta);
        vy -= Consts.GRAVITY * delta;
        Vector2 heroPos = new Vector2(world.getHero().getX() + world.getHero().getWidth() / 2,world.getHero().getY()+ + world.getHero().getHeight() / 2);
        Vector2 pos = new Vector2(getX(), getY());
        heroPos.sub(pos);
        Vector2 dir = new Vector2(heroPos).nor();
        dir.scl(-Consts.COIN_ATTRACTION / heroPos.len());
        vx += dir.x * delta;
        vy += dir.y * delta;
        vx *= Math.pow(0.1,delta);
        vy *= Math.pow(0.1,delta);
        //System.out.println("force is " + dir.len2());
    }

    @Override
    public boolean collidesWith(Collidable collidable) {
        if(collidable instanceof Hero){
            Vector2 heroPos = new Vector2(world.getHero().getX() + world.getHero().getWidth() / 2,world.getHero().getY()+ + world.getHero().getHeight() / 2);
            Vector2 pos = new Vector2(getX(), getY());
            heroPos.sub(pos);
            if(heroPos.len() < world.getHero().getWidth() / 2 && MathUtils.randomBoolean(0.2f)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy(){
        if(isDestroyed()) return;
        super.destroy();
    }

    @Override
    public boolean onCollision(Collidable collidable, float delta) {
        if (collidable instanceof Hero) {
            destroy();
            return true;
        }
        return false;
    }
}
