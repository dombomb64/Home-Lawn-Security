package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.util.IEntityDataSaver;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPathBoundEntity {
	TagKey<Block> iGetPathTag();

	void iSetPathTag(TagKey<Block> value);

	TagKey<Block> iGetPathMarkerTag();

	void iSetPathMarkerTag(TagKey<Block> value);

	World iGetWorld();

	void iStopNavigation();

	Entity iGetSelf();



	boolean isGoal(BlockPos pos);

	boolean isStart(BlockPos pos);



	/**
	 * @return Whether this entity can use this block as a path.
	 */
	default boolean isPath(BlockPos pos) {
		World world = iGetWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(iGetPathMarkerTag()) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(iGetPathTag()) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}

	/**
	 * @return Whether this entity can't use this block as a path but the block is still a path.
	 */
	public default boolean isOtherPath(BlockPos pos) {
		World world = iGetWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getOtherPathMarkerTag(iGetPathMarkerTag())) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(getOtherPathTag(iGetPathTag())) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}

	/**
	 * @return Whether this entity can walk on this block if they have not been put off-course.
	 */
	public default boolean isPathOrGoal(BlockPos pos) {
		return isPath(pos) || isGoal(pos);
	}

	public default void switchPathTag() {
		TagKey<Block> tagKey = getPathTagNbt();
		//if (this.getWorld().getBlockState(this.getBlockPos().down()).isIn(tagKey))
		//return; // shut up you are standing on it

		iStopNavigation();
		setPathTagNbt(getOtherPathTag(tagKey));
	}

	public default TagKey<Block> getOtherPathTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1)
			return ModTags.Blocks.ZOMBIE_PATH_2; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1; // flop
	}

	public default TagKey<Block> getOtherPathMarkerTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			return ModTags.Blocks.ZOMBIE_PATH_2_MARKERS; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1_MARKERS; // flop
	}

	default TagKey<Block> getPathTagNbt() {
		/*NbtCompound nbt = ((IEntityDataSaver) iGetSelf()).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			iSetPathTag(ModTags.Blocks.ZOMBIE_PATH_2);
			iSetPathMarkerTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS);
		}
		else {
			iSetPathTag(ModTags.Blocks.ZOMBIE_PATH_1);
			iSetPathMarkerTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS);
		}*/
		return iGetPathTag();
	}

	/*default TagKey<Block> getPathMarkerTagNbt() {
		NbtCompound nbt = ((IEntityDataSaver) iGetSelf()).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
		return this.pathMarkerTag;
	}*/

	default void setPathTagNbt(TagKey<Block> tagKey) {
		iSetPathTag(tagKey);
		//NbtCompound nbt = ((IEntityDataSaver) iGetSelf()).getPersistentData();
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_2) {
			//nbt.putInt("pathTag", 2);
			iSetPathMarkerTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS);
		}
		else {
			//nbt.putInt("pathTag", 1);
			iSetPathMarkerTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS);
		}
	}
}
