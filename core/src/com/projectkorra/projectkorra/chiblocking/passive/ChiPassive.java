package com.projectkorra.projectkorra.chiblocking.passive;

import com.projectkorra.projectkorra.ability.StanceAbility;
import com.projectkorra.projectkorra.util.ChatUtil;
import com.projectkorra.projectkorra.util.ThreadUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.chiblocking.AcrobatStance;
import com.projectkorra.projectkorra.chiblocking.QuickStrike;
import com.projectkorra.projectkorra.chiblocking.SwiftKick;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import java.util.HashMap;
import java.util.Map;

public class ChiPassive {

	private static Map<Player, Object> messageTasks = new HashMap<>();

	public static boolean willChiBlock(final Player attacker, final Player player) {
		final BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (bPlayer == null) {
			return false;
		}

		final StanceAbility stance = bPlayer.getStance();
		final QuickStrike quickStrike = CoreAbility.getAbility(player, QuickStrike.class);
		final SwiftKick swiftKick = CoreAbility.getAbility(player, SwiftKick.class);
		double newChance = getChance();

		if (stance instanceof AcrobatStance) {
			newChance += ((AcrobatStance) stance).getChiBlockBoost();
		}

		if (quickStrike != null) {
			newChance += quickStrike.getBlockChance();
		} else if (swiftKick != null) {
			newChance += swiftKick.getBlockChance();
		}

		if (Math.random() > newChance / 100.0) {
			return false;
		} else if (bPlayer.isChiBlocked()) {
			return false;
		}

		return true;
	}

	public static void blockChi(final Player player) {
		if (Suffocate.isChannelingSphere(player)) {
			Suffocate.remove(player);
		}

		final BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		if (bPlayer == null) {
			return;
		}

		bPlayer.blockChi();
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 2, 0);

		final long start = System.currentTimeMillis();
		Runnable runnable = () -> {
			ChatUtil.sendActionBar(Element.CHI.getColor() + "* Chiblocked *", player);
			if (System.currentTimeMillis() >= start + getDuration()) {
				bPlayer.unblockChi();
				ThreadUtil.cancelTimerTask(ChiPassive.messageTasks.get(player));
			}
		};
		messageTasks.put(player, ThreadUtil.ensureEntityTimer(player, runnable, 0, 1));
	}

	public static double getChance() {
		return ConfigManager.getConfig().getDouble("Abilities.Chi.Passive.BlockChi.Chance");
	}

	public static int getDuration() {
		return ConfigManager.getConfig().getInt("Abilities.Chi.Passive.BlockChi.Duration");
	}

	public static long getTicks() {
		return (getDuration() / 1000) * 20;
	}
}
