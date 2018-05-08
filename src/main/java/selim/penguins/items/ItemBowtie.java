package selim.penguins.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.Penguins;

public class ItemBowtie extends ItemApparelColored {

	public ItemBowtie() {
		super(EnumPenguinSlot.NECK);
		this.setRegistryName(Penguins.MODID, "bowtie");
		this.setUnlocalizedName(Penguins.MODID + ":bowtie");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public static class ItemColorBowtie implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			return getColor(stack);
		}

	}

}
