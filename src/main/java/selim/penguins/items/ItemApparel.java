package selim.penguins.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemApparel extends Item {

	private final EnumPenguinSlot slot;

	public ItemApparel(EnumPenguinSlot slot) {
		this.slot = slot;
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
