package net.db64.homelawnsecurity.entity.custom.other;

import com.mojang.serialization.Codec;
import net.db64.homelawnsecurity.component.BagOfCurrencyComponent;
import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.item.custom.CurrencyItem;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;

public class CurrencyEntity extends ProjectileEntity implements FlyingItemEntity {
	private static final int MAX_IDLE_TIME = 200;

	private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(CurrencyEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	private static final TrackedData<Boolean> CURRENCY_LEFT_OWNER = DataTracker.registerData(CurrencyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public int idleTime = 0;
	public ItemStack stack = getDefaultItemStack();

	public CurrencyEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public CurrencyEntity(ServerWorld world, LivingEntity livingEntity, ItemStack stack) {
		this(livingEntity.getX(), livingEntity.getEyeY() - (double) 0.1f, livingEntity.getZ(), world, stack);
		setOwner(livingEntity);
	}

	public CurrencyEntity(double x, double y, double z, World world, ItemStack stack) {
		super(ModEntities.Other.CURRENCY, world);
		this.setPosition(x, y, z);
		dataTracker.set(ITEM, stack);
		this.stack = stack.copy();
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		//super.initDataTracker(builder);
		builder.add(ITEM, this.getDefaultItemStack());
		builder.add(CURRENCY_LEFT_OWNER, false);
	}

	@Override
	public void tick() {
		this.tickInitialBubbleColumnCollision();
		this.applyGravity();
		this.applyDrag();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		Vec3d vec3d;
		if (hitResult.getType() != HitResult.Type.MISS) {
			vec3d = hitResult.getPos();
		} else {
			vec3d = this.getPos().add(this.getVelocity());
		}

		//double d = this.getX() + vec3d.x;
		//double e = this.getY() + vec3d.y;
		//double f = this.getZ() + vec3d.z;

		this.setPosition(vec3d);
		this.updateRotation();
		this.tickBlockCollision();
		super.tick();
		if (hitResult.getType() != HitResult.Type.MISS && this.isAlive()) {
			this.hitOrDeflect(hitResult);
		}

		Vec3d velocity = this.getVelocity();
		this.distanceTraveled += (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
		//this.setPosition(d, e, f);

		checkForCollection();

		/*if (isOnGround()) {
			idleTime += 1;
			// TO DO: Currency doesn't know that it touched the ground. Remember when it would sink in when it had the smaller hitbox?
		}*/
		if (this.idleTime > MAX_IDLE_TIME) {
			discard();
		}
	}

	protected double getGravity() {
		return 0.01d;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);

		this.lastDeflectedEntity = null;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);

		idleTime += 1;
	}

	private void tickInitialBubbleColumnCollision() {
		if (this.firstUpdate) {
			Iterator var1 = BlockPos.iterate(this.getBoundingBox()).iterator();

			while(var1.hasNext()) {
				BlockPos blockPos = (BlockPos)var1.next();
				BlockState blockState = this.getWorld().getBlockState(blockPos);
				if (blockState.isOf(Blocks.BUBBLE_COLUMN)) {
					blockState.onEntityCollision(this.getWorld(), blockPos, this, EntityCollisionHandler.DUMMY);
				}
			}
		}
	}

	private void applyDrag() {
		Vec3d velocity = this.getVelocity();
		float drag = 0.8F;

		this.setVelocity(velocity.multiply(drag));
	}

	private void checkForCollection() {
		if (this.getWorld() instanceof ServerWorld world) {
			Entity owner = getOwner();
			if (owner != null) {
				Box box = this.getBoundingBox().expand(1.0);
				if (!dataTracker.get(CURRENCY_LEFT_OWNER) && !box.intersects(owner.getBoundingBox())) {
					dataTracker.set(CURRENCY_LEFT_OWNER, true);
					//HomeLawnSecurity.LOGGER.info("currency left owner");
				}
			}

			for (LivingEntity entity : this.getWorld()
				.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(1.0), EntityPredicates.EXCEPT_SPECTATOR)) {
				if (isOwner(entity) && !dataTracker.get(CURRENCY_LEFT_OWNER))
					continue;

				//HomeLawnSecurity.LOGGER.info("currency collided with {}", entity.getName());

				ItemStack stack = getStack();
				ItemStack bag = ((CurrencyItem) getStack().getItem()).getCurrentBag((LivingEntity) entity);
				if (bag == null)
					continue;

				//HomeLawnSecurity.LOGGER.info("currency collided with {} who has a bag", entity.getName());

				CurrencyComponent pickupCurrency = stack.get(ModDataComponentTypes.CURRENCY);
				BagOfCurrencyComponent bagCurrency = bag.get(ModDataComponentTypes.BAG_OF_CURRENCY);

				//HomeLawnSecurity.LOGGER.info("pickupCurrency: {}, bagCurrency: {}", pickupCurrency, bagCurrency);
				if (pickupCurrency != null && bagCurrency != null) {
					bag.set(ModDataComponentTypes.BAG_OF_CURRENCY, new BagOfCurrencyComponent(Math.min(bagCurrency.amount() + pickupCurrency.amount(), 10000), bagCurrency.name()));

					if (pickupCurrency.name().equals("brainpower"))
						world.playSound(null, entity.getBlockPos(), ModSounds.ENTITY_BRAINPOWER_COLLECT, SoundCategory.NEUTRAL, 1f, world.getRandom().nextFloat() * 0.4f + 1f);
					else
						world.playSound(null, entity.getBlockPos(), ModSounds.ENTITY_SUN_COLLECT, SoundCategory.NEUTRAL, 1f, world.getRandom().nextFloat() * 0.4f + 1f);

					this.discard();
					break;
				}
			}
		}
	}

	protected void setStack(ItemStack stack) {
		ItemStack item;
		if (!stack.isEmpty()) {
			item = stack;
		} else {
			item = this.getDefaultItemStack();
		}
		this.stack = item;
		getDataTracker().set(ITEM, item.copyWithCount(1));
	}

	@Override
	public ItemStack getStack() {
		return this.getDataTracker().get(ITEM);
	}

	//@Override
	public ItemStack getDefaultItemStack() {
		return ModItems.SUN.getDefaultStack();
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.put("item", this.stack.toNbt(this.getRegistryManager()));

		nbt.putInt("idle_time", idleTime);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);

		this.setStack(nbt.get("item", ItemStack.CODEC, registryOps).orElseGet(() -> this.getDefaultItemStack().copy()));

		idleTime = nbt.getInt("idle_time").orElse(0);
	}

	@Override
	protected Text getDefaultName() {
		Text placeholder = super.getDefaultName();

		if (stack != null)
			return stack.getName();
		
		return placeholder;
	}
}
