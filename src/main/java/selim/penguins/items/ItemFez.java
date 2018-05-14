package selim.penguins.items;

import java.util.Random;

import net.minecraft.item.ItemStack;
import selim.penguins.Penguins;

public class ItemFez extends ItemApparel {

	public ItemFez() {
		super(EnumPenguinSlot.HEAD);
		this.setRegistryName(Penguins.MODID, "fez");
		this.setUnlocalizedName(Penguins.MODID + ":fez");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return false;
	}

	@Override
	public ItemStack getRandomApparel(Random rand) {
		return new ItemStack(this);
	}

}
