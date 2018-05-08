package selim.penguins.items;

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

}
