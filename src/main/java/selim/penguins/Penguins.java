package selim.penguins;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.apparel.ModelPenguinBowtie;
import selim.penguins.apparel.ModelPenguinEarmuffs;
import selim.penguins.apparel.ModelPenguinScarf;
import selim.penguins.apparel.ModelPenguinTophat;
import selim.penguins.crafting.RecipeApparelColoredDying;
import selim.penguins.crafting.RecipeApparelPatterned;
import selim.penguins.items.ItemApparelColored;
import selim.penguins.items.ItemApparelPatterned;
import selim.penguins.items.ItemBowtie;
import selim.penguins.items.ItemBowtie.ItemColorBowtie;
import selim.penguins.items.ItemEarmuffs;
import selim.penguins.items.ItemEarmuffs.ItemColorEarmuffs;
import selim.penguins.items.ItemScarf;
import selim.penguins.items.ItemScarf.ItemColorScarf;
import selim.penguins.items.ItemTophat;
import selim.penguins.items.ItemTophat.ItemColorTophat;
import selim.penguins.layers.LayerPatreon;
import selim.penguins.penguin.EntityPenguin;
import selim.penguins.penguin.PenguinRenderer;

@Mod.EventBusSubscriber
@Mod(modid = Penguins.MODID, name = Penguins.NAME, version = Penguins.VERSION)
public class Penguins {

	public static final String MODID = "selimpenguin";
	public static final String NAME = "Penguin";
	public static final String VERSION = "1.1.1";
	@Mod.Instance(value = MODID)
	public static Penguins instance;
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@ObjectHolder(MODID)
	public static class Items {

		public static final ItemApparelColored BOWTIE = null;
		public static final ItemApparelColored TOPHAT = null;
		public static final ItemApparelPatterned<?> SCARF = null;
		// public static final ItemApparelPatterned<?> BOBBLE_HAT = null;
		public static final ItemApparelPatterned<?> EARMUFFS = null;
		// public static final ItemApparel FEZ = null;

	}

	@ObjectHolder(MODID)
	public static class Entities {

		public static final EntityEntry PENGUIN = null;

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBowtie());
		event.getRegistry().register(new ItemTophat());
		event.getRegistry().register(new ItemScarf());
		// event.getRegistry().register(new ItemBobbleHat());
		event.getRegistry().register(new ItemEarmuffs());
		// event.getRegistry().register(new ItemFez());
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		List<Biome> penguinBiomes = new LinkedList<Biome>();
		for (Entry<ResourceLocation, Biome> e : ForgeRegistries.BIOMES.getEntries())
			if (e.getValue().getTempCategory() == TempCategory.COLD)
				penguinBiomes.add(e.getValue());
		event.getRegistry()
				.register(EntityEntryBuilder.create().entity(EntityPenguin.class).egg(0x000000, 0xFFFFFF)
						.tracker(32, 4, false).name(MODID + ":penguin")
						.spawn(EnumCreatureType.CREATURE, 7, 7, 9, penguinBiomes)
						.id(new ResourceLocation(MODID, "penguin"), 0).build());
	}

	@SubscribeEvent
	public static void registerRecipe(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(new RecipeApparelColoredDying());
		event.getRegistry().register(new RecipeApparelPatterned.RecipeAddPattern());
	}

	@SideOnly(Side.CLIENT)
	@EventHandler
	public void clientPreinit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityPenguin.class,
				new IRenderFactory<EntityPenguin>() {

					@Override
					public Render<? super EntityPenguin> createRenderFor(RenderManager manager) {
						return new PenguinRenderer(manager);
					}
				});
	}

	@SideOnly(Side.CLIENT)
	@EventHandler
	public void clientInit(FMLInitializationEvent event) {
		ItemColors colors = Minecraft.getMinecraft().getItemColors();
		colors.registerItemColorHandler(new ItemColorBowtie(), Items.BOWTIE);
		colors.registerItemColorHandler(new ItemColorTophat(), Items.TOPHAT);
		colors.registerItemColorHandler(new ItemColorScarf(), Items.SCARF);
		colors.registerItemColorHandler(new ItemColorEarmuffs(), Items.EARMUFFS);

		for (RenderPlayer renderPlayer : Minecraft.getMinecraft().getRenderManager().getSkinMap()
				.values())
			renderPlayer.addLayer(new LayerPatreon(renderPlayer, new ModelPenguinTophat(),
					new ResourceLocation(Penguins.MODID, "textures/apparel/tophat.png"),
					new ResourceLocation(Penguins.MODID, "textures/apparel/tophat_stripe.png")));
		PatreonHelper.init();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Items.BOWTIE, 0,
				new ModelResourceLocation(Items.BOWTIE.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Items.TOPHAT, 0,
				new ModelResourceLocation(Items.TOPHAT.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Items.SCARF, 0,
				new ModelResourceLocation(Items.SCARF.getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(Items.EARMUFFS, 0,
				new ModelResourceLocation(Items.EARMUFFS.getRegistryName(), "inventory"));

		ApparelModelManager.registerModel(Items.BOWTIE, new ModelPenguinBowtie());
		ApparelModelManager.registerModel(Items.TOPHAT, new ModelPenguinTophat());
		ApparelModelManager.registerModel(Items.SCARF, new ModelPenguinScarf());
		ApparelModelManager.registerModel(Items.EARMUFFS, new ModelPenguinEarmuffs());

		ApparelModelManager.registerTextures(Items.BOWTIE,
				new ResourceLocation(Penguins.MODID, "textures/apparel/bowtie.png"));
		ApparelModelManager.registerTextures(Items.TOPHAT,
				new ResourceLocation(Penguins.MODID, "textures/apparel/tophat.png"),
				new ResourceLocation(Penguins.MODID, "textures/apparel/tophat_stripe.png"));
		ApparelModelManager.registerBaseTexture(Items.SCARF,
				new ResourceLocation(Penguins.MODID, "textures/apparel/scarf.png"));
		ApparelModelManager.registerBaseTexture(Items.EARMUFFS,
				new ResourceLocation(Penguins.MODID, "textures/apparel/earmuffs.png"));
	}

}