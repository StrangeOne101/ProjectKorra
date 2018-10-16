package com.projectkorra.projectkorra.util.Interface.R113;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;

import com.projectkorra.projectkorra.util.Interface.BlockInterface;
import com.projectkorra.projectkorra.util.Interface.EnumChangingBlock;

public class BlockInterface113 extends BlockInterface {

	@Override
	public boolean setBlock_(Block block, EnumChangingBlock changingBlock, byte data) {
		switch (changingBlock) {
			case WATER: //Water in 1.13 has been merged into 1 block. Water level 0 is a source, and the rest is flowing
				block.setType(Material.WATER);
				Levelled blockState = (Levelled) block.getBlockData();
				blockState.setLevel(data);
				return true;
			case LAVA: //Same story as water
				block.setType(Material.LAVA);
				Levelled blockState = (Levelled) block.getBlockData();
				blockState.setLevel(data);
				return true;
			case STONE: //0 is stone, 1 is granite, etc. We don't need to change anything in 1.12
				if (data == 0 || data > 6) block.setType(Material.STONE);
				else if (data == 1) block.setType(Material.GRANITE);
				else if (data == 2) block.setType(Material.DIORITE);
				else if (data == 3) block.setType(Material.ANDESITE);
				else if (data == 4) block.setType(Material.POLISHED_GRANITE);
				else if (data == 5) block.setType(Material.POLISHED_DIORITE);
				else if (data == 6) block.setType(Material.POLISHED_ANDESITE);
				return true;
			case SAND:
				if (data == 0) block.setType(Material.SAND);
				else block.setType(Material.RED_SAND);
				return true;
			default:
				return false;
		}
				
	}

	@Override
	public boolean isBlock_(Block block, EnumChangingBlock changingBlock, byte data) {
		// TODO Auto-generated method stub
		return false;
	}

}
