package net.db64.homelawnsecurity.block.custom.lawn;

import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;

public class SoddedLawnMarkerBlock extends LawnMarkerBlock {
	public SoddedLawnMarkerBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected BlockSoundGroup getSoundGroup(BlockState state) {
		return switch (state.get(PATH_ID_MAIN)) {
			case 0 -> BlockSoundGroup.WART_BLOCK;
			default -> BlockSoundGroup.MUDDY_MANGROVE_ROOTS;
		};
	}
}
