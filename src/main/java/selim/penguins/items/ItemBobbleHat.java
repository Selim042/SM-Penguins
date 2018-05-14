package selim.penguins.items;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import selim.penguins.IApparelPattern;
import selim.penguins.Penguins;
import selim.penguins.items.ItemBobbleHat.EnumBobbleHatPattern;

public class ItemBobbleHat extends ItemApparelPatterned<EnumBobbleHatPattern> {

	public ItemBobbleHat() {
		super(EnumPenguinSlot.HEAD);
		this.setRegistryName(Penguins.MODID, "bobble_hat");
		this.setUnlocalizedName(Penguins.MODID + ":bobble_hat");
		this.setMaxStackSize(1);
	}

	@Override
	public EnumBobbleHatPattern[] getPossiblePatterns() {
		return EnumBobbleHatPattern.values();
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return false;
	}

	public static enum EnumBobbleHatPattern implements IApparelPattern {
		BASE(new ResourceLocation(Penguins.MODID, "textures/apparel/bobble_hat/base.png"),
				new ResourceLocation(Penguins.MODID, "base"), Penguins.MODID + ":base", "   ", "   ",
				" # "),
		BRIM(new ResourceLocation(Penguins.MODID, "textures/apparel/bobble_hat/base.png"),
				new ResourceLocation(Penguins.MODID, "brim"), Penguins.MODID + ":brim", "   ", "   ",
				"###"),
		BALL(new ResourceLocation(Penguins.MODID, "textures/apparel/bobble_hat/base.png"),
				new ResourceLocation(Penguins.MODID, "ball"), Penguins.MODID + ":ball", " # ", "   ",
				"   "),
		HORIZONTAL_STRIPE(new ResourceLocation(Penguins.MODID, "textures/apparel/bobble_hat/base.png"),
				new ResourceLocation(Penguins.MODID, "horizontal_stripe"),
				Penguins.MODID + ":horizontal_stripe", "###", "   ", "###");

		private final ResourceLocation fileName;
		private final ResourceLocation id;
		private final String unlocalizedName;
		private final String[] patterns;
		private ItemStack patternItem = ItemStack.EMPTY;

		private EnumBobbleHatPattern(String fileName, String id, String unlocalizedName) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName);
		}

		private EnumBobbleHatPattern(ResourceLocation fileName, ResourceLocation id,
				String unlocalizedName) {
			this.fileName = fileName;
			this.id = id;
			this.unlocalizedName = unlocalizedName;
			this.patterns = new String[3];
		}

		private EnumBobbleHatPattern(String fileName, String id, String unlocalizedName,
				ItemStack patternItem) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName, patternItem);
		}

		private EnumBobbleHatPattern(ResourceLocation fileName, ResourceLocation id,
				String unlocalizedName, ItemStack patternItem) {
			this(fileName, id, unlocalizedName);
			this.patternItem = patternItem;
		}

		private EnumBobbleHatPattern(String fileName, String id, String unlocalizedName, String line1,
				String line2, String line3) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName, line1, line2,
					line3);
		}

		private EnumBobbleHatPattern(ResourceLocation fileName, ResourceLocation id,
				String unlocalizedName, String line1, String line2, String line3) {
			this(fileName, id, unlocalizedName);
			this.patterns[0] = line1;
			this.patterns[1] = line2;
			this.patterns[2] = line3;
		}

		@Override
		public ResourceLocation getTexture() {
			return this.fileName;
		}

		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		public String getUnlocalizedName() {
			return "apparel_pattern." + this.unlocalizedName + ".name";
		}

		@Override
		public String[] getPatterns() {
			return this.patterns;
		}

		@Override
		public boolean hasPattern() {
			return !this.patternItem.isEmpty() || this.patterns[0] != null;
		}

		@Override
		public ItemStack getPatternItem() {
			return this.patternItem;
		}

		public static EnumBobbleHatPattern fromId(String id) {
			return fromId(new ResourceLocation(id));
		}

		public static EnumBobbleHatPattern fromId(ResourceLocation id) {
			for (EnumBobbleHatPattern p : EnumBobbleHatPattern.values())
				if (p.id.equals(id))
					return p;
			return null;
		}

		@Override
		public ItemApparelPatterned<?> getApplicableApparel() {
			// return Penguins.Items.BOBBLE_HAT;
			return Penguins.Items.SCARF;
		}

	}

}
