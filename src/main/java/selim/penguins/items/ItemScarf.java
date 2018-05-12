package selim.penguins.items;

import java.util.List;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import selim.penguins.IApparelPattern;
import selim.penguins.Penguins;
import selim.penguins.items.ItemScarf.EnumScarfPattern;

public class ItemScarf extends ItemApparelPatterned<EnumScarfPattern> {

	public ItemScarf() {
		super(EnumPenguinSlot.NECK);
		this.setRegistryName(Penguins.MODID, "scarf");
		this.setUnlocalizedName(Penguins.MODID + ":scarf");
		this.setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		for (ColoredPattern<EnumScarfPattern> pattern : getPatterns(stack))
			tooltip.add(pattern.getPattern() + ": " + pattern.getColor());
	}

	@Override
	public EnumScarfPattern[] getPossiblePatterns() {
		return EnumScarfPattern.values();
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return false;
	}

	public static class ItemColorScarf implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if (!(stack.getItem() instanceof ItemApparelPatterned) || tintIndex != 0)
				return -1;
			ColoredPattern<?>[] patterns = ((ItemApparelPatterned<?>) stack.getItem())
					.getPatterns(stack);
			for (ColoredPattern<?> pattern : patterns)
				if (pattern.getPattern().equals(EnumScarfPattern.BASE))
					return pattern.getColor().getColorValue();
			return 0xFFFFFF;
		}

	}

	public static enum EnumScarfPattern implements IApparelPattern {
		BASE(new ResourceLocation(Penguins.MODID, "textures/apparel/scarf/base.png"),
				new ResourceLocation(Penguins.MODID, "base"), Penguins.MODID + "base", "   ", "   ",
				" # "),
		VERTICAL_STRIPE(
				new ResourceLocation(Penguins.MODID, "textures/apparel/scarf/vertical_stripe.png"),
				new ResourceLocation(Penguins.MODID, "vertical_stripe"),
				Penguins.MODID + "vertical_stripe", "# #", "# #", "# #"),
		HORIZONTAL_STRIPE(
				new ResourceLocation(Penguins.MODID, "textures/apparel/scarf/horizontal_stripe.png"),
				new ResourceLocation(Penguins.MODID, "horizontal_stripe"),
				Penguins.MODID + "horizontal_stripe", "###", "   ", "###"),
		CHECKERED(new ResourceLocation(Penguins.MODID, "textures/apparel/scarf/checkered.png"),
				new ResourceLocation(Penguins.MODID, "checkered"), Penguins.MODID + ":checkered", " # ",
				"# #", " # "),
		BOTTOM_STRIPE(new ResourceLocation(Penguins.MODID, "textures/apparel/scarf/bottom_stripe.png"),
				new ResourceLocation(Penguins.MODID, "bottom_stripe"), Penguins.MODID + ":bottom_stripe",
				"   ", "   ", "###");

		private final ResourceLocation fileName;
		private final ResourceLocation id;
		private final String unlocalizedName;
		private final String[] patterns;
		private ItemStack patternItem = ItemStack.EMPTY;

		private EnumScarfPattern(String fileName, String id, String unlocalizedName) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName);
		}

		private EnumScarfPattern(ResourceLocation fileName, ResourceLocation id,
				String unlocalizedName) {
			this.fileName = fileName;
			this.id = id;
			this.unlocalizedName = unlocalizedName;
			this.patterns = new String[3];
		}

		private EnumScarfPattern(String fileName, String id, String unlocalizedName,
				ItemStack patternItem) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName, patternItem);
		}

		private EnumScarfPattern(ResourceLocation fileName, ResourceLocation id, String unlocalizedName,
				ItemStack patternItem) {
			this(fileName, id, unlocalizedName);
			this.patternItem = patternItem;
		}

		private EnumScarfPattern(String fileName, String id, String unlocalizedName, String line1,
				String line2, String line3) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName, line1, line2,
					line3);
		}

		private EnumScarfPattern(ResourceLocation fileName, ResourceLocation id, String unlocalizedName,
				String line1, String line2, String line3) {
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

		public static EnumScarfPattern fromId(String id) {
			return fromId(new ResourceLocation(id));
		}

		public static EnumScarfPattern fromId(ResourceLocation id) {
			for (EnumScarfPattern p : EnumScarfPattern.values())
				if (p.id.equals(id))
					return p;
			return null;
		}

		@Override
		public ItemApparelPatterned<?> getApplicableApparel() {
			return Penguins.Items.SCARF;
		}

	}

}
