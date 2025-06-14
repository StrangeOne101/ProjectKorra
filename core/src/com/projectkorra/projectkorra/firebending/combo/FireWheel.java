package com.projectkorra.projectkorra.firebending.combo;

import com.projectkorra.projectkorra.GeneralMethods;

import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager;
import com.projectkorra.projectkorra.ability.util.ComboUtil;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.attribute.markers.DayNightFactor;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.TempBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FireWheel extends FireAbility implements ComboAbility {

	private Particle particle;
	private Location location;
	private Vector direction;
	private double distanceTraveled = 0;
	private boolean goingUp = false;
	private boolean goingDown = false;
	private Location explodeLocation = null;
	private List<LivingEntity> affectedEntities = new ArrayList<>();
	private List<Vector> vectors = new ArrayList<>();

	private Vector rotationalVector;

	@Attribute(Attribute.COOLDOWN) @DayNightFactor(invert = true)
	private long cooldown = 5000;

	@Attribute(Attribute.RANGE) @DayNightFactor
	private double range = 50;

	@Attribute(Attribute.SPEED)
	private double speed = 1.0;

	@Attribute(Attribute.HEIGHT)
	private double height = 0.8;

	@Attribute(Attribute.DAMAGE) @DayNightFactor
	private double damage = 3;

	@Attribute("Trail")
	private boolean trail = false;

	@Attribute("Explode")
	private boolean explode;

	@Attribute("ExplodeRevert")
	private long explodeRevert = 10 * 1000;

	@Attribute("TrailRevert")
	private long trailRevert = 1 * 1000;

	public FireWheel(Player player) {
		super(player);

		this.direction = player.getEyeLocation().getDirection();
		this.direction.setY(0F);
		this.direction.normalize(); //Since we set the Y to 0, we have to re-normalize
		this.rotationalVector = new Vector(direction.getZ(), 0, -direction.getX()); //Rotates the vector 90degrees

		particle = getFireType() == Material.SOUL_FIRE ? Particle.SOUL_FIRE_FLAME : Particle.FLAME;

		this.cooldown = ConfigManager.defaultConfig.get().getLong("Abilities.Fire.FireWheel.Cooldown");
		this.height = ConfigManager.defaultConfig.get().getDouble("Abilities.Fire.FireWheel.Height");
		this.damage = ConfigManager.defaultConfig.get().getDouble("Abilities.Fire.FireWheel.Damage");
		this.speed = ConfigManager.defaultConfig.get().getDouble("Abilities.Fire.FireWheel.Speed");
		this.range = ConfigManager.defaultConfig.get().getDouble("Abilities.Fire.FireWheel.Range");
		this.explodeRevert = ConfigManager.defaultConfig.get().getLong("Abilities.Fire.FireWheel.ExplodeRevertTime");
		this.trailRevert = ConfigManager.defaultConfig.get().getLong("Abilities.Fire.FireWheel.Trail.RevertTime");
		this.trail = ConfigManager.defaultConfig.get().getBoolean("Abilities.Fire.FireWheel.Trail.Enabled");

		for (double d = -1; d < 5; d += 0.5) {
			Block b = player.getLocation().add(0, -d, 0).getBlock();
			if (b.getType().isSolid()) {
				this.location = b.getLocation().add(0, height + 1, 0);
				start();
				//ProjectKorra.plugin.getLogger().info("Started " + this.location.toString());
				return;
			}
		}
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreBinds(this)) {
			remove();
			return;
		}

		if (location == null || distanceTraveled > range) {
			remove();
			return;
		}

		if (player.isSneaking() && !explode) {
			explode = true;
		}

		advanceLocation();
		displayWheel();

		for (final Entity entity : GeneralMethods.getEntitiesAroundPoint(this.location, height + 0.3)) {
			if (entity instanceof LivingEntity && !entity.equals(this.player)) {
				if (!this.affectedEntities.contains(entity)) {
					this.affectedEntities.add((LivingEntity) entity);
					DamageHandler.damageEntity(entity, this.damage, this);
				}
			}
		}

		this.location.getWorld().playSound(this.location, Sound.BLOCK_FIRE_AMBIENT, 1, 1);

	}

	private void displayWheel() {
		double amount = 48 * height;
		double particleSpeed = 0.4;
		if (vectors.isEmpty()) {

			for (int i = 0; i < amount; i++) {
				float angle = (float) (((double)360) / amount * (double)i);
				//ProjectKorra.plugin.getLogger().info(angle + "");
				vectors.add(rotateVectorCC(direction, rotationalVector, Math.toRadians(angle)).normalize().multiply(height));
			}

			rotationalVector = rotationalVector.multiply(0.15); //Keep the vector but shorten it a lot for particle spread
		}


		for (int i = 0; i < vectors.size(); i++) {
			Vector display1 = vectors.get(i);
			Vector display2 = vectors.get((i - 2 + vectors.size()) % vectors.size());
			Location displayLoc = location.clone().add(display1);
			//Display particle using vector 1 for location and vector 2 for direction
			player.getWorld().spawnParticle(particle, displayLoc, 0, display2.getX() * particleSpeed, display2.getY() * particleSpeed, display2.getZ() * particleSpeed);
			displayLoc = displayLoc.clone().add(rotationalVector);
			player.getWorld().spawnParticle(particle, displayLoc, 0, display2.getX() * particleSpeed, display2.getY() * particleSpeed, display2.getZ() * particleSpeed);
			displayLoc = displayLoc.clone().add(rotationalVector.clone().multiply(-1));
			player.getWorld().spawnParticle(particle, displayLoc, 0, display2.getX() * particleSpeed, display2.getY() * particleSpeed, display2.getZ() * particleSpeed);
		}
	}

	public static Vector rotateVectorCC(Vector vec, Vector axis, double theta) {
		double x, y, z;
		double u, v, w;
		x = vec.getX();
		y = vec.getY();
		z = vec.getZ();
		u = axis.getX();
		v = axis.getY();
		w = axis.getZ();
		double v1 = u * x + v * y + w * z;
		double xPrime = u * v1 * (1d - Math.cos(theta))
				+ x * Math.cos(theta)
				+ (-w * y + v * z) * Math.sin(theta);
		double yPrime = v * v1 * (1d - Math.cos(theta))
				+ y * Math.cos(theta)
				+ (w * x - u * z) * Math.sin(theta);
		double zPrime = w * v1 * (1d - Math.cos(theta))
				+ z * Math.cos(theta)
				+ (-v * x + u * y) * Math.sin(theta);
		return new Vector(xPrime, yPrime, zPrime);
	}

	private void advanceLocation() {

		if (explodeLocation != null) { //If it's all set to explode and can't be stopped
			location.add(direction.clone().multiply(speed));
			if (location.distance(explodeLocation) <= height) {
				explode();
				return;
			}
		}

		distanceTraveled += speed;

		Location oldLocation = location.clone();

		//Check blocks in the general area of the wheel to see if they are water. If they are, Sizzle!
		Block[] blocks = new Block[] {location.getBlock(), location.clone().add(0, -height + 0.1, 0).getBlock()};
		for (Block b : blocks) {
			if (!b.getType().isSolid() && (b.getType() == Material.WATER ||
					(b.getState().getBlockData() instanceof Waterlogged && ((Waterlogged) b.getState().getBlockData()).isWaterlogged()))) {
				remove();

				b.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE, location.clone().add(0, 1, 0), 5);
				b.getLocation().getWorld().playSound(b.getLocation().clone().add(0, 1, 0), Sound.BLOCK_FIRE_EXTINGUISH, 5, 1);
				return;
			}
		}


		if (goingUp) {
			//ProjectKorra.plugin.getLogger().info("Up");
			location.add(0, 1, 0);

			goingUp = false; //Reset it

			outerLoop:
			for (double d = 0.5; d <= speed + 1; d += 0.5) {
				if (d > speed + 1) d = speed + 1;

				Location fowardLoc = location.clone().add(direction.clone().multiply(d));
				Location upLoc = location.clone().add(new Vector(0, height, 0));

				for (double d2 = 0; d2 <= height; d2 += 0.5) { //Starting at 0 is a slight patch so it doesnt catch blocks on the existing wall it scales
					if (d2 > height) d2 = height;

					//Check if it should continue to go up or not.
					if (fowardLoc.clone().add(0, d2, 0).getBlock().getType().isSolid() && !goingUp) {
						goingUp = true; //Will be set back to true if it should remain going up
					}

					//Check if the blocks above are blocked or not
					if (upLoc.clone().add(direction.clone().multiply(d2)).getBlock().getType().isSolid()) {
						if (explode) { //If it should explode
							explodeLocation = fowardLoc;
							break outerLoop;
						}
						remove(); //Remove if it shouldn't explode because it can't get over the ledge
						//debug(upLoc.clone().add(direction.clone().multiply(d2)));
						return;
					}
				}
			}

		} else if (goingDown) {
			//ProjectKorra.plugin.getLogger().info("Down");
			location.add(0, -1, 0);

			goingDown = true; //Reset it to default to going down if no solid blocks bellow are found

			Location downLoc = location.clone().add(new Vector(0, -1, 0));

			//Check if it should go up
			for (double d2 = -height; d2 <= height; d2 += 0.5) {
				if (d2 > height) d2 = height;

				//Check if the blocks above are blocked or not
				if (downLoc.clone().add(direction.clone().multiply(d2)).getBlock().getType().isSolid() && goingDown) {
					goingDown = false;
					break;
				}
			}
		} else {
			location.add(direction.clone().multiply(speed));
			//ProjectKorra.plugin.getLogger().info("Across");

			goingDown = true; //Reset it to default to going down if no solid blocks bellow are found

			//Progressively check all blocks between the end location and the current, so speeds of over 1 block dont make it go through blocks
			outerLoop:
			for (double d = 0.5; d <= speed + 1; d += 0.5) { //Using speed + 1 to space the wheel from the wall more
				if (d > speed + 1) d = speed + 1;

				Location fowardLoc = location.clone().add(direction.clone().multiply(d));


				//Check all blocks on the y axis starting at -height all the way to height
				for (double d2 = -height; d2 <= height; d2 += 0.5) {
					if (d2 > height) d2 = height;
					//Check if any of the blocks in front are solid
					if (fowardLoc.clone().add(0, d2, 0).getBlock().getType().isSolid() && !goingUp) {
						if (explode) {
							explodeLocation = fowardLoc;
							break outerLoop;
						}
						goingUp = true;
						//debug(fowardLoc.clone().add(0, d2, 0));
						//remove(); //For debugging
					}


				}
			}

			Location downLoc = location.clone().add(new Vector(0, -1, 0));

			//A seperate loop for going down because the range of blocks checked is different
			for (double d2 = 0; d2 <= height + 1; d2 += 0.5) {
				//Check if the blocks bellow are blocked or not
				if (downLoc.clone().add(direction.clone().multiply(d2)).getBlock().getType().isSolid() && goingDown) {
					goingDown = false;
				}
			}
		}

		if (trail) {

			Vector dir = GeneralMethods.getDirection(oldLocation, location);
			double length = dir.length();
			dir = dir.normalize();

			for (double d = 0; d < length; d += 0.5) {
				dir.multiply(d);
				Location loc = oldLocation.clone().add(dir);

				Block b = loc.clone().add(0, -height + 0.05, 0).getBlock();
				if (!b.getType().isSolid()) {
					new TempBlock(b, getFireType().createBlockData(), trailRevert);
				}
			}
		}
	}

	@Override
	public void remove() {
		bPlayer.addCooldown(this, cooldown);

		super.remove();
	}

	private void explode() {

		for (int angle = 0; angle < 180; angle += 18) {

			Vector vector = GeneralMethods.rotateVectorAroundVector(rotationalVector, direction, angle).normalize();

			for (double d = -height; d <= height; d += 0.5) {
				Block b = explodeLocation.clone().add(vector.clone().multiply(d)).getBlock();
				if (b.getType().isSolid()) {
					new TempBlock(b, Material.AIR.createBlockData(), explodeRevert);
				}
			}
		}

		for (final Entity entity : GeneralMethods.getEntitiesAroundPoint(this.explodeLocation, height + 1)) {
			if (entity instanceof LivingEntity && !entity.equals(this.player)) {
				if (!this.affectedEntities.contains(entity)) {
					this.affectedEntities.add((LivingEntity) entity);
					DamageHandler.damageEntity(entity, this.damage, this);
				}
			}
		}

		explodeLocation.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 5);
		explodeLocation.getWorld().playSound(explodeLocation, Sound.ENTITY_GENERIC_EXPLODE, 5, 1);

		remove();
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public String getName() {
		return "FireWheel";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public double getCollisionRadius() {
		return height;
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new FireWheel(player);
	}

	@Override
	public ArrayList<ComboManager.AbilityInformation> getCombination() {
		return ComboUtil.generateCombinationFromList(this, ConfigManager.defaultConfig.get().getStringList("Abilities.Fire.FireWheel.Combination"));
	}

}
