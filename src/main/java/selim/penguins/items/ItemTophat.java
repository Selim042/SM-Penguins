package selim.penguins.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.Penguins;

public class ItemTophat extends ItemApparelColored {

	public ItemTophat() {
		super(EnumPenguinSlot.HEAD);
		this.setRegistryName(Penguins.MODID, "tophat");
		this.setUnlocalizedName(Penguins.MODID + ":tophat");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public static class ItemColorTophat implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			return tintIndex == 1 ? getColor(stack) : -1;
		}

	}

}
