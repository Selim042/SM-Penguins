package selim.penguins.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemApparel extends Item {

	private static final List<ItemApparel> APPAREL = new ArrayList<>();

	private final EnumPenguinSlot slot;

	public ItemApparel(EnumPenguinSlot slot) {
		this.slot = slot;
		APPAREL.add(this);
	}

	public static ItemApparel[] getAllApparel() {
		return (ItemApparel[]) APPAREL.toArray(new ItemApparel[0]);
	}

	public static ItemStack getRandomFromAll(Random rand) {
		ItemApparel apparel = APPAREL.get(rand.nextInt(APPAREL.size()));
		ItemStack stack = apparel.getRandomApparel(rand);
		return stack == null ? ItemStack.EMPTY : stack;
	}

	public EnumPenguinSlot getSlot() {
		return this.slot;
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.MISC;
	}

	@SideOnly(Side.CLIENT)
	public abstract boolean shouldShinkIfBaby();

	public abstract ItemStack getRandomApparel(Random rand);

	public static enum EnumPenguinSlot {
		HEAD("head"),
		NECK("neck");

		private final String id;

		EnumPenguinSlot(String id) {
			this.id = id;
		}

		public String getId() {
			return this.id;
		}

		public static EnumPenguinSlot getSlot(String id) {
			for (EnumPenguinSlot s : EnumPenguinSlot.values())
				if (s.id.equals(id))
					return s;
			return null;
		}
	}

}
