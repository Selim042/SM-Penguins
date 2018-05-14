package selim.penguins.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.IApparelPattern;
import selim.penguins.Penguins;
import selim.penguins.items.ItemEarmuffs.EnumEarmuffsPattern;

public class ItemEarmuffs extends ItemApparelPatterned<EnumEarmuffsPattern> {

	public ItemEarmuffs() {
		super(EnumPenguinSlot.HEAD);
		this.setRegistryName(Penguins.MODID, "earmuffs");
		this.setUnlocalizedName(Penguins.MODID + ":earmuffs");
		this.setMaxStackSize(1);
	}

	@Override
	public EnumEarmuffsPattern[] getPossiblePatterns() {
		return EnumEarmuffsPattern.values();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldShinkIfBaby() {
		return false;
	}

	public static class ItemColorEarmuffs implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if (!(stack.getItem() instanceof ItemApparelPatterned))
				return -1;
			int toReturn = -1;
			for (ColoredPattern pattern : ((ItemApparelPatterned<?>) stack.getItem())
					.getPatterns(stack)) {
				switch ((EnumEarmuffsPattern) pattern.getPattern()) {
				case BASE:
					toReturn = pattern.getColor().getColorValue();
					break;
				case BRACE:
					if (tintIndex == 0)
						toReturn = pattern.getColor().getColorValue();
					break;
				case MUFFS:
					if (tintIndex == 1)
						toReturn = pattern.getColor().getColorValue();
					break;
				default:
					break;
				}
			}
			return toReturn == -1 ? 0xFFFFFF : toReturn;
		}

	}

	public static enum EnumEarmuffsPattern implements IApparelPattern {
		BASE(new ResourceLocation(Penguins.MODID, "textures/apparel/earmuffs/base.png"),
				new ResourceLocation(Penguins.MODID, "base"), Penguins.MODID + ":base", "   ", "   ",
				" # "),
		BRACE(new ResourceLocation(Penguins.MODID, "textures/apparel/earmuffs/brace.png"),
				new ResourceLocation(Penguins.MODID, "brace"), Penguins.MODID + ":brace", "###", "   ",
				"   "),
		MUFFS(new ResourceLocation(Penguins.MODID, "textures/apparel/earmuffs/muffs.png"),
				new ResourceLocation(Penguins.MODID, "muffs"), Penguins.MODID + ":muffs", "   ", "# #",
				"   ");

		private final ResourceLocation fileName;
		private final ResourceLocation id;
		private final String unlocalizedName;
		private final String[] patterns;
		private ItemStack patternItem = ItemStack.EMPTY;

		private EnumEarmuffsPattern(String fileName, String id, String unlocalizedName) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName);
		}

		private EnumEarmuffsPattern(ResourceLocation fileName, ResourceLocation id,
				String unlocalizedName) {
			this.fileName = fileName;
			this.id = id;
			this.unlocalizedName = unlocalizedName;
			this.patterns = new String[3];
		}

		private EnumEarmuffsPattern(String fileName, String id, String unlocalizedName,
				ItemStack patternItem) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName, patternItem);
		}

		private EnumEarmuffsPattern(ResourceLocation fileName, ResourceLocation id,
				String unlocalizedName, ItemStack patternItem) {
			this(fileName, id, unlocalizedName);
			this.patternItem = patternItem;
		}

		private EnumEarmuffsPattern(String fileName, String id, String unlocalizedName, String line1,
				String line2, String line3) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), unlocalizedName, line1, line2,
					line3);
		}

		private EnumEarmuffsPattern(ResourceLocation fileName, ResourceLocation id,
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

		public static EnumEarmuffsPattern fromId(String id) {
			return fromId(new ResourceLocation(id));
		}

		public static EnumEarmuffsPattern fromId(ResourceLocation id) {
			for (EnumEarmuffsPattern p : EnumEarmuffsPattern.values())
				if (p.id.equals(id))
					return p;
			return null;
		}

		@Override
		public ItemApparelPatterned<?> getApplicableApparel() {
			return Penguins.Items.EARMUFFS;
		}
	}

}
