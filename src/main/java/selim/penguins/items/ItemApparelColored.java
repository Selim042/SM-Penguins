package selim.penguins.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemApparelColored extends ItemApparel {

	public ItemApparelColored(EnumPenguinSlot slot) {
		super(slot);
	}

	public static int getColor(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemApparelColored))
			return 0xFFFFFFFF;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt != null) {
			NBTTagCompound displayNbt = nbt.getCompoundTag("display");
			if (displayNbt != null && displayNbt.hasKey("color", 3))
				return displayNbt.getInteger("color");
		}
		return 0x000000;
	}

	public static void setColor(ItemStack stack, int color) {
		if (stack == null || !(stack.getItem() instanceof ItemApparelColored))
			return;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		NBTTagCompound displayNbt = nbt.getCompoundTag("display");
		if (displayNbt == null) {
			displayNbt = new NBTTagCompound();
			nbt.setTag("display", displayNbt);
		}
		if (!nbt.hasKey("display", 10))
			nbt.setTag("display", displayNbt);
		displayNbt.setInteger("color", color);
	}

}
