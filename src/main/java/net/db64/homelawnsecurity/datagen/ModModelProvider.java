package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		BlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.GARDEN_BLOCK);
		BlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.GRAVEYARD_BLOCK);

		BlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.LAWN_BLOCK);

		BlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.FERTILE_PATH_BLOCK_1);
		BlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.FERTILE_PATH_BLOCK_2);
		BlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		BlockModels.registerUnsoddedPathBlock(blockStateModelGenerator, ModBlocks.ZOMBIE_PATH_BLOCK_1);
		BlockModels.registerUnsoddedPathBlock(blockStateModelGenerator, ModBlocks.ZOMBIE_PATH_BLOCK_2);
		BlockModels.registerUnsoddedPathBlock(blockStateModelGenerator, ModBlocks.ZOMBIE_PATH_BLOCK_CROSS);

		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.UNSODDED_LAWN_BLOCK);
		//blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.UNSODDED_LAWN_BLOCK);

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.GRAVEYARD_MARKER, ModBlocks.GRAVEYARD_MARKER.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.GARDEN_MARKER, ModBlocks.GARDEN_MARKER.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.LAWN_MARKER, ModBlocks.LAWN_MARKER.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_1, ModBlocks.FERTILE_PATH_MARKER_1.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_2, ModBlocks.FERTILE_PATH_MARKER_2.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_CROSS, ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_1, ModBlocks.ZOMBIE_PATH_MARKER_1.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_2, ModBlocks.ZOMBIE_PATH_MARKER_2.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_CROSS, ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.UNSODDED_LAWN_MARKER.asItem());

		/*blockStateModelGenerator.registerLog(ModBlocks.RUBBER_LOG).log(ModBlocks.RUBBER_LOG).wood(ModBlocks.RUBBER_WOOD);
		blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_RUBBER_LOG).log(ModBlocks.STRIPPED_RUBBER_LOG).wood(ModBlocks.STRIPPED_RUBBER_WOOD);
		blockStateModelGenerator.registerLog(ModBlocks.DRIPPING_RUBBER_LOG).log(ModBlocks.DRIPPING_RUBBER_LOG).wood(ModBlocks.DRIPPING_RUBBER_WOOD);

		BlockStateModelGenerator.BlockTexturePool rubberwoodPlanksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBERWOOD_PLANKS);
		rubberwoodPlanksPool.stairs(ModBlocks.RUBBERWOOD_STAIRS);
		rubberwoodPlanksPool.slab(ModBlocks.RUBBERWOOD_SLAB);
		rubberwoodPlanksPool.fence(ModBlocks.RUBBERWOOD_FENCE);
		rubberwoodPlanksPool.fenceGate(ModBlocks.RUBBERWOOD_FENCE_GATE);
		blockStateModelGenerator.registerDoor(ModBlocks.RUBBERWOOD_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.RUBBERWOOD_TRAPDOOR);
		rubberwoodPlanksPool.pressurePlate(ModBlocks.RUBBERWOOD_PRESSURE_PLATE);
		rubberwoodPlanksPool.button(ModBlocks.RUBBERWOOD_BUTTON);

		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBER_LEAVES);
		blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.RUBBER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
		blockStateModelGenerator.registerItemModel(ModBlocks.RUBBER_SAPLING); // So that the sapling is 2D in the inventory*/
	}

	private static class BlockModels {
		public static void registerLawnBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
			TextureMap textureMap = new TextureMap()
				.put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK))
				.put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
				.put(TextureKey.SIDE, TextureMap.getSubId(ModBlocks.LAWN_BLOCK, "_side"));
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE_BOTTOM_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector)));
		}

		public static void registerUnsoddedPathBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
			TextureMap textureMap = new TextureMap()
				.put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK))
				.put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
				.put(TextureKey.SIDE, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE_BOTTOM_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector)));
		}

		/*public static final TexturedModel.Factory LAWN_BLOCK = makeFactory(TextureMap::all, Models.CUBE_BOTTOM_TOP);
		public static final TexturedModel.Factory UNSODDED_PATH = makeFactory(TextureMap::all, Models.CUBE_BOTTOM_TOP);


		public static TextureMap lawnBlock(Block block) {
			return new TextureMap().put(TextureKey.SIDE, TextureMap.getSubId(ModBlocks.LAWN_BLOCK, "_side")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
		}

		public static TextureMap unsoddedPath(Block block) {
			return new TextureMap().put(TextureKey.SIDE, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK)).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
		}

		public static TexturedModel.Factory makeFactory(Function<Block, TextureMap> texturesGetter, Model model) {
			return (block) -> {
				return new TexturedModel((TextureMap)texturesGetter.apply(block), model);
			};
		}*/
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		//itemModelGenerator.register(ModItems.PEASHOOTER_SPAWN_EGG,
			//new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
		itemModelGenerator.registerSpawnEgg(ModItems.PEASHOOTER_SPAWN_EGG, 0xD7FF37, 0x3BC10E);

		//itemModelGenerator.register(ModItems.BASIC_ZOMBIE_SPAWN_EGG,
			//new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
		itemModelGenerator.registerSpawnEgg(ModItems.BASIC_ZOMBIE_SPAWN_EGG, 0x6D7858, 0x4A341D);

		//ItemModels.registerHeadgear(itemModelGenerator, ModItems.DAVES_PAN, , );

		itemModelGenerator.register(ModItems.BAG_OF_SUN, Models.GENERATED);
		itemModelGenerator.register(ModItems.BAG_OF_BRAINPOWER, Models.GENERATED);

		itemModelGenerator.register(ModItems.SUN, Models.GENERATED);
		itemModelGenerator.register(ModItems.BRAINPOWER, Models.GENERATED);

		itemModelGenerator.register(ModItems.LAWN_MOWER, Models.GENERATED);

		itemModelGenerator.register(ModItems.TARGET, Models.GENERATED);

		itemModelGenerator.register(ModItems.SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(ModItems.LAWN_GADGET, Models.HANDHELD);

		itemModelGenerator.register(ModBlocks.GARDEN_MARKER.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.GRAVEYARD_MARKER.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.LAWN_MARKER.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_1.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_2.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_1.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_2.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.UNSODDED_LAWN_MARKER.asItem(), Models.GENERATED);



		var plantBase = ItemModels.registerSeedPacketBase(itemModelGenerator, "plant");
		var zombieBase = ItemModels.registerSeedPacketBase(itemModelGenerator, "zombie");
		var imitaterBase = ItemModels.registerSeedPacketBase(itemModelGenerator, "imitater");
		var upgradeBase = ItemModels.registerSeedPacketBase(itemModelGenerator, "upgrade");

		var peashooter = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "peashooter");
		var sunflower = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "sunflower");
		var cherryBomb = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "cherry_bomb");
		var wallNut = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "wall_nut");
		var potatoMine = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "potato_mine");
		var snowPea = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "snow_pea");
		var chomper = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "chomper");
		var repeater = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "repeater");
		var puffShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "puff_shroom");
		var sunShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "sun_shroom");
		var fumeShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "fume_shroom");
		var graveBuster = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "grave_buster");
		var hypnoShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "hypno_shroom");
		var scaredyShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "scaredy_shroom");
		var iceShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "ice_shroom");
		var doomShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "doom_shroom");
		var lilyPad = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "lily_pad");
		var squash = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "squash");
		var threepeater = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "threepeater");
		var tangleKelp = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "tangle_kelp");
		var jalapeno = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "jalapeno");
		var spikeweed = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "spikeweed");
		var torchwood = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "torchwood");
		var tallNut = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "tall_nut");
		var seaShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "sea_shroom");
		var plantern = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "plantern");
		var cactus = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "cactus");
		var blover = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "blover");
		var splitPea = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "split_pea");
		var starfruit = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "starfruit");
		var pumpkin = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "pumpkin");
		var magnetShroom = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "magnet_shroom");
		var cabbagePult = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "cabbage_pult");
		var flowerPot = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "flower_pot");
		var kernelPult = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "kernel_pult");
		var coffeeBean = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "coffee_bean");
		var garlic = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "garlic");
		var umbrellaLeaf = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "umbrella_leaf");
		var marigold = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "marigold");
		var melonPult = ItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "melon_pult");

		var zombieGravestone = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "zombie_gravestone");
		var basicZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "basic_zombie");
		var trashCanZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "trash_can_zombie");
		var coneheadZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "conehead_zombie");
		var poleVaultingZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "pole_vaulting_zombie");
		var bucketheadZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "buckethead_zombie");
		var flagZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "flag_zombie");
		var newspaperZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "newspaper_zombie");
		var screenDoorZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "screen_door_zombie");
		var footballZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "football_zombie");
		var dancingZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "dancing_zombie");
		var zomboni = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "zomboni");
		var jackInTheBoxZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "jack_in_the_box_zombie");
		var diggerZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "digger_zombie");
		var pogoZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "pogo_zombie");
		var bungeeZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "bungee_zombie");
		var ladderZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "ladder_zombie");
		var catapultZombie = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "catapult_zombie");
		var garantuar = ItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "gargantuar");

		var plant0 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "0");
		var plant25 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "25");
		var plant50 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "50");
		var plant75 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "75");
		var plant100 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "100");
		var plant125 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "125");;
		var plant150 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "150");;
		var plant175 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "175");;
		var plant300 = ItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "300");

		var zombie25 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "25");
		var zombie50 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "50");
		var zombie75 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "75");
		var zombie100 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "100");
		var zombie125 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "125");
		var zombie150 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "150");
		var zombie175 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "175");
		var zombie200 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "200");
		var zombie225 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "225");
		var zombie250 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "250");
		var zombie300 = ItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "300");



		ItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_PEASHOOTER, plantBase, peashooter, plant100);

		ItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_SUNFLOWER, plantBase, sunflower, plant50);

		ItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_WALL_NUT, plantBase, wallNut, plant50);



		ItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_ZOMBIE_GRAVESTONE, zombieBase, zombieGravestone, zombie50);

		ItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_BASIC_ZOMBIE, zombieBase, basicZombie, zombie25);

		ItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_CONEHEAD_ZOMBIE, zombieBase, coneheadZombie, zombie75);
	}

	private static class ItemModels {
		public static final Model TEMPLATE_SEED_PACKET_BASE = item("template_seed_packet_base", TextureKey.LAYER0);
		public static final Model TEMPLATE_SEED_PACKET_IMAGE = item("template_seed_packet_image", TextureKey.LAYER0);
		public static final Model TEMPLATE_SEED_PACKET_COST = item("template_seed_packet_cost", TextureKey.LAYER0);

		private static Model item(String parent, TextureKey... requiredTextureKeys) {
			return new Model(Optional.of(Identifier.of(HomeLawnSecurity.MOD_ID, "item/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		/*public static void registerHeadgear(ItemModelGenerator itemModelGenerator, Item item, Model model, ItemModel.Unbaked onHeadModel) {
			ItemModel.Unbaked unbaked = net.minecraft.client.data.ItemModels.select(
				new DisplayContextProperty(),
				net.minecraft.client.data.ItemModels.basic(itemModelGenerator.upload(item, model)),
				net.minecraft.client.data.ItemModels.switchCase(List.of(ModelTransformationMode.HEAD), onHeadModel)
			);
			itemModelGenerator.output.accept(item, unbaked);
		}*/

		public static void registerSeedPacket(ItemModelGenerator itemModelGenerator, Item item, Identifier baseModel, Identifier imageModel, Identifier costModel) {
			ItemModel.Unbaked unbaked = net.minecraft.client.data.ItemModels.composite(
				net.minecraft.client.data.ItemModels.basic(baseModel),
				net.minecraft.client.data.ItemModels.basic(imageModel),
				net.minecraft.client.data.ItemModels.basic(costModel)
			);

			itemModelGenerator.output.accept(item, unbaked);
		}

		public static Identifier registerSeedPacketBase(ItemModelGenerator itemModelGenerator, String base) {
			Identifier id = Identifier.of(HomeLawnSecurity.MOD_ID, "item/seed_packet/base/" + base);
			return Models.GENERATED.upload(id, TextureMap.layer0(id), itemModelGenerator.modelCollector);
		}

		public static Identifier registerSeedPacketImage(ItemModelGenerator itemModelGenerator, String team, String image) {
			Identifier id = Identifier.of(HomeLawnSecurity.MOD_ID, "item/seed_packet/image/" + team + "/" + image);
			return Models.GENERATED.upload(id, TextureMap.layer0(id), itemModelGenerator.modelCollector);
		}

		public static Identifier registerSeedPacketCost(ItemModelGenerator itemModelGenerator, String team, String cost) {
			Identifier id = Identifier.of(HomeLawnSecurity.MOD_ID, "item/seed_packet/cost/" + team + "/" + cost);
			return Models.GENERATED.upload(id, TextureMap.layer0(id), itemModelGenerator.modelCollector);
		}
	}
}
