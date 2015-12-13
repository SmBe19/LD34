package com.smeanox.games.ld34.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Comment
 */
public class ParticleSystem implements Updatable, Renderable, Destroyable {

	private World world;
	private int layer;
	private Texture texture;
	private List<Particle> particles;
	private ParticleFactory particleFactory;

	private float lifeTime, lifeTimeRand, rate, rateRand, startX, startY, startXRand, startYRand, startVeloX, startVeloY, startVeloXRand, startVeloYRand;
	private float zoom;
	private Deque<Float> nextParticles;
	private Color color;
	private float passedTime;
	private boolean generating;
	private float timeout, autoDisable, oneParticleTimeout;
	private String tag;

	public ParticleSystem(World world, String tag, ParticleFactory particleFactory, int layer,
	                      Texture texture, Color color, float zoom, float lifeTime, float lifeTimeRand, float rate,
	                      float rateRand, float startX, float startY, float startXRand, float startYRand,
	                      float startVeloX, float startVeloY, float startVeloXRand, float startVeloYRand) {
		this.world = world;
		this.tag = tag;
		this.particleFactory = particleFactory;
		this.layer = layer;
		this.texture = texture;
		this.color = color;
		this.zoom = zoom;
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

		if(this.particleFactory == null){
			this.particleFactory = new ParticleFactory() {
				@Override
				public Particle createParticle(ParticleSystem system, float time, float x, float y, float vx, float vy) {
					return new Particle(system, time, x, y, vx, vy);
				}
			};
		}

		particles = new ArrayList<Particle>();
		nextParticles = new ArrayDeque<Float>();

		world.getUpdatables().add(this);
		world.addRenderable(layer, this);

		passedTime = 0;
		generating = false;
		autoDisable = Float.POSITIVE_INFINITY;
		timeout = Float.POSITIVE_INFINITY;
		oneParticleTimeout = Float.POSITIVE_INFINITY;
		fillNextParticles();
	}

	public boolean isGenerating() {
		return generating;
	}

	public void setGenerating(boolean generating) {
		this.generating = generating;
	}

	public float getAutoDisable() {
		return autoDisable;
	}

	public void setAutoDisable(float autoDisable) {
		this.autoDisable = autoDisable;
	}

	public float getTimeout() {
		return timeout;
	}

	public void setTimeout(float timeout) {
		this.timeout = timeout;
	}

	public float getOneParticleTimeout() {
		return oneParticleTimeout;
	}

	public void setOneParticleTimeout(float oneParticleTimeout) {
		this.oneParticleTimeout = oneParticleTimeout;
	}

	@Override
	public void destroy(){
		for(Particle particle : getParticles()){
			particle.destroy();
		}
		world.getUpdatables().remove(this);
		world.getRenderables().get(layer).remove(this);
	}

	private float getRand(float mid, float rand){
		return mid + MathUtils.randomTriangular(-rand, rand);
	}

	private void fillNextParticles(){
		float last = passedTime;
		if(!nextParticles.isEmpty()){
			last = nextParticles.getLast();
		}
		while(nextParticles.isEmpty() || last < passedTime + 5){
			last += getRand(rate, rateRand);
			nextParticles.addLast(last);
		}
	}

	@Override
	public void update(float delta) {
		for(int i = getParticles().size()-1; i >= 0; i--){
			getParticles().get(i).update(delta);
		}
		if(generating) {
			passedTime += delta;
			autoDisable -= delta;
			if(autoDisable < 0){
				generating = false;
				autoDisable = Float.POSITIVE_INFINITY;
			}
			fillNextParticles();

			while (nextParticles.getFirst() < passedTime) {
				nextParticles.removeFirst();
				addOneParticle();
			}
		} else {
			timeout -= delta;
			oneParticleTimeout -= delta;
			if(oneParticleTimeout < 0){
				oneParticleTimeout = Float.POSITIVE_INFINITY;
				addOneParticle();
			}
			if(timeout < 0){
				generating = true;
				timeout = Float.POSITIVE_INFINITY;
			}
		}
	}

	public void addOneParticle(){
		particleFactory.createParticle(this, getRand(lifeTime, lifeTimeRand), getRand(startX, startXRand),
				getRand(startY, startYRand), getRand(startVeloX, startVeloXRand), getRand(startVeloY, startVeloYRand));
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		spriteBatch.setColor(color);
		for(int i = getParticles().size()-1; i >= 0; i--) {
			getParticles().get(i).render(delta, spriteBatch);
		}
		spriteBatch.setColor(Color.WHITE);
	}

	public float getStartVeloYRand() {
		return startVeloYRand;
	}

	public void setStartVeloYRand(float startVeloYRand) {
		this.startVeloYRand = startVeloYRand;
	}

	public float getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(float lifeTime) {
		this.lifeTime = lifeTime;
	}

	public float getLifeTimeRand() {
		return lifeTimeRand;
	}

	public void setLifeTimeRand(float lifeTimeRand) {
		this.lifeTimeRand = lifeTimeRand;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getRateRand() {
		return rateRand;
	}

	public void setRateRand(float rateRand) {
		this.rateRand = rateRand;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public float getStartXRand() {
		return startXRand;
	}

	public void setStartXRand(float startXRand) {
		this.startXRand = startXRand;
	}

	public float getStartYRand() {
		return startYRand;
	}

	public void setStartYRand(float startYRand) {
		this.startYRand = startYRand;
	}

	public float getStartVeloX() {
		return startVeloX;
	}

	public void setStartVeloX(float startVeloX) {
		this.startVeloX = startVeloX;
	}

	public float getStartVeloY() {
		return startVeloY;
	}

	public void setStartVeloY(float startVeloY) {
		this.startVeloY = startVeloY;
	}

	public float getStartVeloXRand() {
		return startVeloXRand;
	}

	public void setStartVeloXRand(float startVeloXRand) {
		this.startVeloXRand = startVeloXRand;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ParticleFactory getParticleFactory() {
		return particleFactory;
	}

	public void setParticleFactory(ParticleFactory particleFactory) {
		this.particleFactory = particleFactory;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public interface ParticleFactory {
		Particle createParticle(ParticleSystem system, float time, float x, float y, float vx, float vy);
	}

	public static class Particle extends Rigidbody implements Updatable, Renderable, Destroyable{
		private float time;
		private int collisions;
		private ParticleSystem particleSystem;

		public Particle(ParticleSystem particleSystem, float time, float x, float y, float vx, float vy) {
			particleSystem.world.getPhysics().addRigidbody(this);

			this.time = time;
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;

			this.collisions = 10;

			particleSystem.getParticles().add(this);
			this.particleSystem = particleSystem;
		}

		@Override
		public Rectangle getCollisionBox() {
			return new Rectangle(x, y, particleSystem.texture.getWidth() * particleSystem.zoom, particleSystem.texture.getHeight() * particleSystem.zoom);
		}

		@Override
		public boolean onCollision(Collidable collidable, float delta) {
			if(collidable instanceof GroundPart) {
				return true;
			}
			collisions--;
			if(collisions < 0){
				destroy();
			}
			return true;
		}

		@Override
		public boolean collidesWith(Collidable collidable) {
			if(collidable instanceof Building && "snow".equals(particleSystem.tag)){
				return true;
			}
			if(collidable instanceof GroundPart){
				return true;
			}
			return false;
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
			spriteBatch.draw(particleSystem.texture, x, y, particleSystem.texture.getWidth() * particleSystem.zoom, particleSystem.texture.getHeight() * particleSystem.zoom);
		}

		@Override
		public void destroy(){
			particleSystem.world.getPhysics().removeRigidbody(this);
			particleSystem.getParticles().remove(this);
		}
	}
}
