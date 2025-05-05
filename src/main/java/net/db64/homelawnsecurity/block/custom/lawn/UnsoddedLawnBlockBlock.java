package net.db64.homelawnsecurity.block.custom.lawn;

import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;

public class UnsoddedLawnBlockBlock extends LawnBlockBlock {
	public UnsoddedLawnBlockBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected BlockSoundGroup getSoundGroup(BlockState state) {
		return switch (state.get(PATH_ID_MAIN)) {
			case 0 -> BlockSoundGroup.GRAVEL;
			default -> BlockSoundGroup.ROOTED_DIRT;
		};
	}
}
