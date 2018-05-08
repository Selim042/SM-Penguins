package selim.penguins.items;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import selim.penguins.IApparelPattern;

public abstract class ItemApparelPatterned<E extends IApparelPattern> extends ItemApparel {

	public ItemApparelPatterned(EnumPenguinSlot slot) {
		super(slot);
	}

	public abstract E[] getPossiblePatterns();

	public abstract ColoredPattern<E>[] getPatterns(ItemStack stack);

	public abstract void addPattern(ItemStack stack, ColoredPattern<E> pattern);

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
			patternNbt.setInteger("color", this.color.getDyeDamage());
			return patternNbt;
		}

	}

}
