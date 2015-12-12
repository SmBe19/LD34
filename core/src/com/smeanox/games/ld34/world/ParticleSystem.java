package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.smeanox.games.ld34.Consts;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Comment
 */
public class ParticleSystem implements Updatable, Renderable {

	private World world;
	private Texture texture;
	private List<Particle> particles;

	private float lifeTime, lifeTimeRand, rate, rateRand, startX, startY, startXRand, startYRand, startVeloX, startVeloY, startVeloXRand, startVeloYRand;
	private Deque<Float> nextParticles;
	private Color color;
	float passedTime;
	boolean generating;

	public ParticleSystem(World world, Texture texture, Color color, float lifeTime, float lifeTimeRand, float rate,
	                      float rateRand, float startX, float startY, float startXRand, float startYRand,
	                      float startVeloX, float startVeloY, float startVeloXRand, float startVeloYRand) {
		this.world = world;
		this.texture = texture;
		this.color = color;
		this.lifeTime = lifeTime;
		this.lifeTimeRand = lifeTimeRand;
		this.rate = rate;
		this.rateRand = rateRand;
		this.startX = startX;
		this.startY = startY;
		this.startXRand = startXRand;
		this.startYRand = startYRand;
		this.startVeloX = startVeloX;
		this.startVeloY = startVeloY;
		this.startVeloXRand = startVeloXRand;
		this.startVeloYRand = startVeloYRand;

		particles = new ArrayList<Particle>();
		nextParticles = new ArrayDeque<Float>();

		world.getUpdatables().add(this);
		world. getRenderables().add(this);

		passedTime = 0;
		generating = false;
		fillNextParticles();
	}

	private void fillNextParticles(){
		float last = 0;
		if(!nextParticles.isEmpty()){
			last = nextParticles.getLast();
		}
		while(nextParticles.isEmpty() || nextParticles.getLast() < passedTime + 5){
			last += getRand(rate, rateRand);
			nextParticles.addLast(last);
		}
	}

	public boolean isGenerating() {
		return generating;
	}

	public void setGenerating(boolean generating) {
		this.generating = generating;
	}

	public void destroy(){
		for(Particle particle : particles){
			particle.destroy();
		}
		world.getUpdatables().remove(this);
		world.getRenderables().remove(this);
	}

	private float getRand(float mid, float rand){
		return mid + MathUtils.random(-rand, rand);
	}

	@Override
	public void update(float delta) {
		for(int i = particles.size()-1; i >= 0; i--){
			particles.get(i).update(delta);
		}
		passedTime += delta;
		if(generating) {
			fillNextParticles();

			while (nextParticles.getFirst() < passedTime) {
				nextParticles.removeFirst();
				addParticle(getRand(lifeTime, lifeTimeRand), getRand(startX, startXRand), getRand(startY, startYRand),
						getRand(startVeloX, startVeloXRand), getRand(startVeloY, startVeloYRand));
			}
		}
	}

	public void addParticle(float time, float x, float y, float vx, float vy){
		new Particle(time, x, y, vx, vy);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		spriteBatch.setColor(color);
		for(int i = particles.size()-1; i >= 0; i--) {
			particles.get(i).render(delta, spriteBatch);
		}
		spriteBatch.setColor(Color.WHITE);
	}

	private class Particle extends Rigidbody implements Updatable, Renderable{
		private float time;

		public Particle(float time, float x, float y, float vx, float vy) {
			world.getPhysics().addRigidbody(this);

			this.time = time;
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;

			particles.add(this);
		}

		@Override
		public Rectangle getCollisionBox() {
			return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
		}

		@Override
		public void update(float delta) {
			time -= delta;
			if(time < 0){
				destroy();
			}
		}

		@Override
		public void render(float delta, SpriteBatch spriteBatch) {
			spriteBatch.draw(texture, x, y, texture.getWidth(), texture.getHeight());
		}

		public void destroy(){
			world.getPhysics().removeRigidbody(this);
			particles.remove(this);
		}
	}
}
