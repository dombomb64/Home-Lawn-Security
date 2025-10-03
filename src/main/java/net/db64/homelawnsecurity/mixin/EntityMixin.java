package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityDataSaver {
	@Shadow
	public abstract BlockPos getBlockPos();

	@Shadow
	public abstract World getWorld();

	@Final
	@Shadow
	protected Random random;

	@Unique
	private NbtCompound persistentData;

	@Override
	public NbtCompound getPersistentData() {
		if (this.persistentData == null) {
			this.persistentData = new NbtCompound();
		}
		return persistentData;
	}

	@Inject(
		method = "writeData",
		at = @At("HEAD")
	)
	protected void injectWriteMethod(WriteView view, CallbackInfo ci) {
		if (persistentData != null) {
			view.put(IEntityDataSaver.dataId, NbtCompound.CODEC, persistentData);
		}
	}

	@Inject(
		method = "readData",
		at = @At("HEAD")
	)
	protected void injectReadMethod(ReadView view, CallbackInfo ci) {
		persistentData = view.read(IEntityDataSaver.dataId, NbtCompound.CODEC).orElse(new NbtCompound());
	}


	// -===-


	@WrapWithCondition(
		method = "pushAwayFrom",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V",
			ordinal = 0
		)
	)
	private boolean checkForPushThis(Entity instance, double deltaX, double deltaY, double deltaZ, @Local(index = 0, argsOnly = true) Entity that) {
		Entity toHiss = (Entity) (Object) this; // This should be pushed by that if...
		return (toHiss instanceof ZombieEntity && that instanceof PlantEntity) // This is a zombie being pushed by that plant
			|| (!(toHiss instanceof IPvzEntity) && !(that instanceof IPvzEntity)); // Neither of these are PvZ-related
	}

	@WrapWithCondition(
		method = "pushAwayFrom",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V",
			ordinal = 1
		)
	)
	private boolean checkForPushThat(Entity instance, double deltaX, double deltaY, double deltaZ, @Local(index = 0, argsOnly = true) Entity that) {
		Entity toHiss = (Entity) (Object) this; // That should be pushed by this if...
		return (that instanceof ZombieEntity && toHiss instanceof PlantEntity) // That is a zombie being pushed by this plant
			|| (!(that instanceof IPvzEntity) && !(toHiss instanceof IPvzEntity)); // Neither of these are PvZ-related
	}
}
