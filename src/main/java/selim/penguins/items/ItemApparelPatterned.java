package selim.penguins.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.IApparelPattern;
import selim.penguins.Penguins;

public abstract class ItemApparelPatterned<E extends IApparelPattern> extends ItemApparel {

	private static final List<ItemApparelPatterned<?>> PATTERNED_APPAREL = new ArrayList<ItemApparelPatterned<?>>();
	private static final List<IApparelPattern> PATTERNS = new ArrayList<IApparelPattern>();

	public ItemApparelPatterned(EnumPenguinSlot slot) {
		super(slot);
		PATTERNED_APPAREL.add(this);
		for (IApparelPattern pattern : this.getPossiblePatterns())
			PATTERNS.add(pattern);
	}

	public static ItemApparelPatterned<?>[] getAllPatternedApparel() {
		return (ItemApparelPatterned[]) PATTERNED_APPAREL.toArray(new ItemApparelPatterned[0]);
	}

	public static IApparelPattern[] getAllPatterns() {
		return (IApparelPattern[]) PATTERNS.toArray(new IApparelPattern[0]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		for (ColoredPattern pattern : this.getPatterns(stack))
			tooltip.add(I18n.format(pattern.pattern.getUnlocalizedName(),
					I18n.format("colors." + Penguins.MODID + ":" + pattern.color.getUnlocalizedName())));
	}

	public abstract E[] getPossiblePatterns();

	@Override
	public ItemStack getRandomApparel(Random rand) {
		E[] possiblePatterns = this.getPossiblePatterns();
		int numPatterns = rand.nextInt(possiblePatterns.length);
		if (numPatterns > 6)
			numPatterns = 6;
		ItemStack stack = new ItemStack(this);
		for (int i = 0; i < numPatterns; i++) {
			E pat = possiblePatterns[rand.nextInt(possiblePatterns.length)];
			EnumDyeColor color = EnumDyeColor.byDyeDamage(rand.nextInt(EnumDyeColor.values().length));
			ColoredPattern pattern = new ColoredPattern(pat, color);
			this.addPattern(stack, pattern);
		}
		return stack;
	}

	public ColoredPattern[] getPatterns(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemApparelPatterned))
			return null;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
			return new ColoredPattern[0];
		ItemApparelPatterned<?> apparel = (ItemApparelPatterned<?>) stack.getItem();
		List<ColoredPattern> patterns = new ArrayList<>();
		NBTTagList list = nbt.getTagList("patterns", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound patternNbt = list.getCompoundTagAt(i);
			IApparelPattern pattern = null;
			for (IApparelPattern p : apparel.getPossiblePatterns()) {
				if (p.getId().equals(new ResourceLocation(patternNbt.getString("pattern")))) {
					pattern = p;
					break;
				}
			}
			if (pattern == null)
				continue;
			ColoredPattern coloredPat = new ColoredPattern(pattern,
					EnumDyeColor.byMetadata(patternNbt.getInteger("color")));
			if (coloredPat == null || coloredPat.getPattern() == null)
				continue;
			patterns.add(coloredPat);
		}
		return (ColoredPattern[]) patterns.toArray(new ColoredPattern[patterns.size()]);
	}

	public void addPattern(ItemStack stack, ColoredPattern pattern) {
		if (stack == null || !(stack.getItem() instanceof ItemApparelPatterned)
				|| !pattern.getPattern().getApplicableApparel().equals(stack.getItem()))
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

	public static class ColoredPattern {

		private final IApparelPattern pattern;
		private final EnumDyeColor color;

		public ColoredPattern(IApparelPattern pattern, EnumDyeColor color) {
			this.pattern = pattern;
			this.color = color;
		}

		public IApparelPattern getPattern() {
			return this.pattern;
		}

		public EnumDyeColor getColor() {
			return this.color;
		}

		public NBTTagCompound serializeToNbt() {
			NBTTagCompound patternNbt = new NBTTagCompound();
			patternNbt.setString("pattern", this.pattern.getId().toString());
			patternNbt.setInteger("color", this.color.getMetadata());
			return patternNbt;
		}

	}

}
