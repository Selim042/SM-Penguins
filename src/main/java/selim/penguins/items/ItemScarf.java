package selim.penguins.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
		// for (ColoredPattern pattern : getPatterns(stack)) {
		//// System.out.println(pattern.getPattern() + ": " +
		// pattern.getColor());
		// tooltip.add(pattern.getPattern() + ": " + pattern.getColor());
		// }
	}

	@Override
	public EnumScarfPattern[] getPossiblePatterns() {
		return EnumScarfPattern.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ColoredPattern<EnumScarfPattern>[] getPatterns(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemScarf))
			return null;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
			return new ColoredPattern[] {};
		List<ColoredPattern<EnumScarfPattern>> patterns = new ArrayList<ColoredPattern<EnumScarfPattern>>();
		NBTTagList list = nbt.getTagList("patterns", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound patternNbt = list.getCompoundTagAt(i);
			// System.out.println(patternNbt.getString("pattern") + ":"
			// + EnumScarfPattern.fromId(patternNbt.getString("pattern")));
			ColoredPattern<EnumScarfPattern> coloredPat = new ColoredPattern<EnumScarfPattern>(
					EnumScarfPattern.fromId(patternNbt.getString("pattern")),
					EnumDyeColor.byMetadata(patternNbt.getInteger("color")));
			if (coloredPat == null || coloredPat.getPattern() == null)
				continue;
			patterns.add(coloredPat);
		}
		return (ColoredPattern<EnumScarfPattern>[]) patterns
				.toArray(new ColoredPattern[patterns.size()]);
	}

	@Override
	public void addPattern(ItemStack stack, ColoredPattern<EnumScarfPattern> pattern) {
		if (stack == null || !(stack.getItem() instanceof ItemScarf))
			return;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		NBTTagList list = nbt.getTagList("patterns", 10);
		if (list == null) {
			list = new NBTTagList();
			nbt.setTag("patterns", list);
		}
		list.appendTag(pattern.serializeToNbt());
		nbt.setTag("patterns", list);
		stack.setTagCompound(nbt);
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return false;
	}

	public static enum EnumScarfPattern implements IApparelPattern {
		BASE("textures/apparel/scarf/base.png", "base", "   ", "   ", " # "),
		VERTICAL_STRIPE("textures/apparel/scarf/vertical_stripe.png", "vertical_stripe", "#  ", "#  ",
				"#  "),
		HORIZONTAL_STRIPE("textures/apparel/scarf/hotizontal_stripe.png", "horizonal_stripe", "###",
				"   ", "   "),
		CHECKERED("textures/apparel/scarf/checkered.png", "checkered", " # ", "# #", " # "),
		BOTTOM_STRIPE("textures/apparel/scarf/bottom_stripe.png", "bottom_stripe", "   ", "   ", "###");

		private final ResourceLocation fileName;
		private final ResourceLocation id;
		private final String[] patterns;
		private ItemStack patternItem;

		private EnumScarfPattern(String fileName, String id) {
			this(new ResourceLocation(fileName), new ResourceLocation(id));
		}

		private EnumScarfPattern(ResourceLocation fileName, ResourceLocation id) {
			this.patterns = new String[3];
			this.patternItem = ItemStack.EMPTY;
			this.fileName = fileName;
			this.id = id;
		}

		private EnumScarfPattern(String fileName, String id, ItemStack patternItem) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), patternItem);
		}

		private EnumScarfPattern(ResourceLocation fileName, ResourceLocation id, ItemStack patternItem) {
			this(fileName, id);
			this.patternItem = patternItem;
		}

		private EnumScarfPattern(String fileName, String id, String line1, String line2, String line3) {
			this(new ResourceLocation(fileName), new ResourceLocation(id), line1, line2, line3);
		}

		private EnumScarfPattern(ResourceLocation fileName, ResourceLocation id, String line1,
				String line2, String line3) {
			this(fileName, id);
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
		public String[] getPatterns() {
			return this.patterns;
		}

		@Override
		public boolean hasPattern() {
			return !this.patternItem.isEmpty() || this.patterns[0] != null;
		}

		@Override
		public boolean hasPatternItem() {
			return !this.patternItem.isEmpty();
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

	}

}
