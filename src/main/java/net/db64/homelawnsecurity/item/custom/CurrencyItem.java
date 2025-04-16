package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class CurrencyItem extends Item implements ProjectileItem {
	public Predicate<ItemStack> bagPredicate;

	public CurrencyItem(Item.Settings settings, Predicate<ItemStack> bagPredicate) {
		super(settings);
		this.bagPredicate = bagPredicate;
	}

	/*@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);

		var currencyComponent = stack.get(ModDataComponentTypes.CURRENCY);
		if (currencyComponent != null)
			tooltip.add(Text.translatable("tooltip.homelawnsecurity.currency." + currencyComponent.name(), currencyComponent.amount()));
	}*/

	public ItemStack getCurrentBag(LivingEntity entity) {
		if (bagPredicate.test(entity.getStackInHand(Hand.OFF_HAND))) {
			return entity.getStackInHand(Hand.OFF_HAND);
		}
		else if (bagPredicate.test(entity.getStackInHand(Hand.MAIN_HAND))) {
			return entity.getStackInHand(Hand.MAIN_HAND);
		}
		else if (entity instanceof ServerPlayerEntity player) {
			PlayerInventory inventory = player.getInventory();
			for (int i = 0; i < inventory.size(); i++) {
				ItemStack itemStack2 = inventory.getStack(i);
				if (bagPredicate.test(itemStack2)) {
					return itemStack2;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		SoundEvent sound;
		CurrencyComponent currency = itemStack.get(ModDataComponentTypes.CURRENCY);
		if (currency != null && currency.name().equals("brainpower"))
			sound = ModSounds.ENTITY_BRAINPOWER_THROW;
		else
			sound = ModSounds.ENTITY_SUN_THROW;

		world.playSound(
			null,
			user.getX(),
			user.getY(),
			user.getZ(),
			sound,
			SoundCategory.NEUTRAL,
			0.5f,
			world.getRandom().nextFloat() * 0.4f + 1f
		);

		if (world instanceof ServerWorld serverWorld) {
			ProjectileEntity.spawnWithVelocity(CurrencyEntity::new, serverWorld, itemStack, user, 0.0f, 1.5f, 1.0f);
		}

		user.incrementStat(Stats.USED.getOrCreateStat(this));
		itemStack.decrementUnlessCreative(1, user);
		return ActionResult.SUCCESS;
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		return new CurrencyEntity(pos.getX(), pos.getY(), pos.getZ(), world, stack);
	}
}
