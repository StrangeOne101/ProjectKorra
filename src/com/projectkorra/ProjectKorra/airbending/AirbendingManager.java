package com.projectkorra.ProjectKorra.airbending;

import org.bukkit.Bukkit;

import com.projectkorra.ProjectKorra.ProjectKorra;

public class AirbendingManager implements Runnable {

	public ProjectKorra plugin;
	
	public AirbendingManager(ProjectKorra plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		AirBlast.progressAll(AirBlast.class);
		AirPassive.handlePassive(Bukkit.getServer());
		AirBurst.progressAll(AirBurst.class);
		AirScooter.progressAll(AirScooter.class);
		Suffocate.progressAll(Suffocate.class);
		AirSpout.progressAll(AirSpout.class);
		AirBubble.handleBubbles(Bukkit.getServer());
		AirSuction.progressAll(AirSuction.class);
		AirSwipe.progressAll(AirSwipe.class);
		Tornado.progressAll(Tornado.class);
		AirShield.progressAll(AirShield.class);
		AirCombo.progressAll();
		FlightAbility.progressAll(FlightAbility.class);
	}

}
