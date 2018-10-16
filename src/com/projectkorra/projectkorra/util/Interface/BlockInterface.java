package com.projectkorra.projectkorra.util.Interface;

import org.bukkit.block.Block;

import com.projectkorra.projectkorra.util.ReflectionHandler.PackageType;
import com.projectkorra.projectkorra.util.Interface.R112.BlockInterface112;

public abstract class BlockInterface {
	
	private static BlockInterface INSTANCE;
	
	public abstract boolean setBlock_(Block block, EnumChangingBlock changingBlock, byte data);
	
	public static boolean setBlock(Block block, EnumChangingBlock changingBlock, byte data) {
		return false;
		
	}
	
	public abstract boolean isBlock_(Block block, EnumChangingBlock changingBlock, byte data);

	public static boolean isBlock(Block block, EnumChangingBlock changingBlock, byte data) {
		return false;
	}
	
	public static boolean isBlock(Block block, EnumChangingBlock changingBlock) {
		return isBlock(block, changingBlock, (byte) 0);
	}
	
	
	public static void setup() {
		if (INSTANCE != null) return;
		
		int version = Integer.parseInt(PackageType.getServerVersion().split("_")[1]);
		
		if (version <= 12) {
			INSTANCE = new BlockInterface112();
		} else {
			INSTANCE = new BlockInterface113();
		}
	}

}
