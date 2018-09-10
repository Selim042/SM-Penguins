package selim.penguins.items;

import selim.penguins.Penguins;

public class ItemFlower extends ItemApparelColored {

	public ItemFlower() {
		super(EnumPenguinSlot.NECK);
		this.setRegistryName(Penguins.MODID, "flower");
		this.setUnlocalizedName(Penguins.MODID + ":flower");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean shouldShinkIfBaby() {
		return true;
	}

}
