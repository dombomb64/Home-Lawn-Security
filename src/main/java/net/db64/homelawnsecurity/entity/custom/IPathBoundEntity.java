package net.db64.homelawnsecurity.entity.custom;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface IPathBoundEntity {
	/*@Deprecated
	TagKey<Block> iGetPathTag();

	@Deprecated
	void iSetPathTag(TagKey<Block> value);

	@Deprecated
	TagKey<Block> iGetPathMarkerTag();

	@Deprecated
	void iSetPathMarkerTag(TagKey<Block> value);

	World iGetWorld();

	void iStopNavigation();

	Entity iGetSelf();*/



	int getPathId();

	void setPathId(int pathId);



	boolean isGoal(BlockPos pos);

	boolean isStart(BlockPos pos);



	boolean isWalkable(BlockPos pos);

	boolean isThisPath(BlockPos pos);

	boolean isCertainPath(BlockPos pos, int pathId);

	/**
	 * @return Whether this entity can use this block as a path.
	 */
	/*@Deprecated
	default boolean isThisPath(BlockPos pos) {
		World world = iGetWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.REVEALS_MARKERS)) {
			return markerState.isIn(iGetPathMarkerTag()) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(iGetPathTag()) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}*/

	/**
	 * @return Whether this entity can't use this block as a path but the block is still a path.
	 */
	/*@Deprecated
	default boolean isOtherPath(BlockPos pos) {
		World world = iGetWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.REVEALS_MARKERS)) {
			return markerState.isIn(getOtherPathMarkerTag(iGetPathMarkerTag())) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(getOtherPathTag(iGetPathTag())) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}*/

	/**
	 * @return Whether this entity can walk on this block if they have not been put off-course.
	 */
	/*@Deprecated
	default boolean isPathOrGoal(BlockPos pos) {
		return isThisPath(pos) || isGoal(pos);
	}*/

	/*@Deprecated
	default void switchPathTag() {
		TagKey<Block> tagKey = getPathTagNbt();
		//if (this.getEntityWorld().getBlockState(this.getBlockPos().down()).isIn(tagKey))
		//return; // shut up you are standing on it

		iStopNavigation();
		setPathTagNbt(getOtherPathTag(tagKey));
	}*/

	/*@Deprecated
	default TagKey<Block> getOtherPathTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1)
			return ModTags.Blocks.ZOMBIE_PATH_2; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1; // flop
	}*/

	/*@Deprecated
	default TagKey<Block> getOtherPathMarkerTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			return ModTags.Blocks.ZOMBIE_PATH_2_MARKERS; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1_MARKERS; // flop
	}*/

	/*@Deprecated
	default TagKey<Block> getPathTagNbt() {
		/NbtCompound nbt = ((IEntityDataSaver) iGetSelf()).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			iSetPathTag(ModTags.Blocks.ZOMBIE_PATH_2);
			iSetPathMarkerTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS);
		}
		else {
			iSetPathTag(ModTags.Blocks.ZOMBIE_PATH_1);
			iSetPathMarkerTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS);
		}/
		return iGetPathTag();
	}*/

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

	/*@Deprecated
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
	}*/
}
