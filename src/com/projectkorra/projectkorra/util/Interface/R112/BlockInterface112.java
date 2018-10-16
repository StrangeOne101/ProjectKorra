package com.projectkorra.projectkorra.util.Interface.R112;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.projectkorra.projectkorra.util.Interface.BlockInterface;
import com.projectkorra.projectkorra.util.Interface.EnumChangingBlock;

public class BlockInterface112 extends BlockInterface {

	@SuppressWarnings("deprecation")
	@Override
	public boolean setBlock_(Block block, EnumChangingBlock changingBlock, byte data) {
		switch (changingBlock) {
			case WATER: //Water in 1.13 has been merged into 1 block. Water level 0 is a source, and the rest is flowing
				if (data == 0) { //So we will now use 1.13 data values but bodge in the fact that they are separate in 1.12
					block.setType(Material.STATIONARY_WATER);
				} else {
					block.setType(Material.WATER);
					block.setData((byte) (data - 1));
				}
				return true;
			case LAVA: //Same story as water
				if (data == 0) {
					block.setType(Material.STATIONARY_LAVA);
				} else {
					block.setType(Material.LAVA);
					block.setData((byte) (data - 1));
				}
				return true;
			case STONE: //0 is stone, 1 is granite, etc. We don't need to change anything in 1.12
				block.setType(Material.STONE);
				block.setData(data);
				return true;
			case SAND:
				block.setType(Material.SAND);
				block.setData(data);
				return true;
			case :
				block.setType(Material.SAND);
				block.setData(data);
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
