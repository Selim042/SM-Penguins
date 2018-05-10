package selim.penguins.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import selim.penguins.IApparelPattern;
import selim.penguins.items.ItemScarf.EnumScarfPattern;

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

	public abstract E[] getPossiblePatterns();

	@SuppressWarnings("unchecked")
	public ColoredPattern<E>[] getPatterns(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemScarf))
			return null;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
			return new ColoredPattern[0];
		List<ColoredPattern<EnumScarfPattern>> patterns = new ArrayList<ColoredPattern<EnumScarfPattern>>();
		NBTTagList list = nbt.getTagList("patterns", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound patternNbt = list.getCompoundTagAt(i);
			ColoredPattern<EnumScarfPattern> coloredPat = new ColoredPattern<EnumScarfPattern>(
					EnumScarfPattern.fromId(patternNbt.getString("pattern")),
					EnumDyeColor.byMetadata(patternNbt.getInteger("color")));
			if (coloredPat == null || coloredPat.getPattern() == null)
				continue;
			patterns.add(coloredPat);
		}
		return (ColoredPattern<E>[]) patterns.toArray(new ColoredPattern[patterns.size()]);
	}

	public void addPattern(ItemStack stack, ColoredPattern<E> pattern) {
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

	public static class ColoredPattern<E extends IApparelPattern> {

		private final E pattern;
		private final EnumDyeColor color;

		public ColoredPattern(E pattern, EnumDyeColor color) {
			this.pattern = pattern;
			this.color = color;
		}

		public E getPattern() {
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
