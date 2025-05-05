package net.db64.homelawnsecurity.block.custom.lawn;

import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;

public class UnsoddedLawnMarkerBlock extends LawnMarkerBlock {
	public UnsoddedLawnMarkerBlock(Settings settings) {
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
