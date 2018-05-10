package selim.penguins.crafting;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import selim.penguins.IApparelPattern;
import selim.penguins.Penguins;
import selim.penguins.items.ItemApparelPatterned;
import selim.penguins.items.ItemApparelPatterned.ColoredPattern;

public class RecipeApparelPatterned {

	public static class RecipeAddPattern extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

		public RecipeAddPattern() {
			this.setRegistryName(Penguins.MODID, "apparel_add_patern");
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			boolean flag = false;
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				if (itemstack.getItem() instanceof ItemApparelPatterned) {
					ItemApparelPatterned<?> apparel = (ItemApparelPatterned<?>) itemstack.getItem();
					if (flag)
						return false;
					if (apparel.getPatterns(itemstack).length >= 6)
						return false;
					flag = true;
				}
			}
			if (!flag)
				return false;
			else
				return this.matchPatterns(inv) != null;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			ItemStack itemstack = ItemStack.EMPTY;
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack itemstack1 = inv.getStackInSlot(i);
				if (!itemstack1.isEmpty() && itemstack1.getItem() instanceof ItemApparelPatterned) {
					itemstack = itemstack1.copy();
					itemstack.setCount(1);
					break;
				}
			}
			IApparelPattern pattern = this.matchPatterns(inv);
			if (pattern != null) {
				EnumDyeColor dye = null;
				for (int j = 0; j < inv.getSizeInventory(); ++j) {
					ItemStack itemstack2 = inv.getStackInSlot(j);
					int color = net.minecraftforge.oredict.DyeUtils.rawDyeDamageFromStack(itemstack2);
					if (color != -1) {
						dye = EnumDyeColor.byDyeDamage(color);
						break;
					}
				}
				ItemApparelPatterned<?> apparel = (ItemApparelPatterned<?>) itemstack.getItem();
				apparel.addPattern(itemstack, new ColoredPattern(pattern, dye));
				// NBTTagCompound nbttagcompound1 = itemstack.getTagCompound();
				// NBTTagList patternsNbt;
				// if (nbttagcompound1.hasKey("patterns", 9))
				// patternsNbt = nbttagcompound1.getTagList("patterns", 10);
				// else {
				// patternsNbt = new NBTTagList();
				// nbttagcompound1.setTag("patterns", patternsNbt);
				// }
				// NBTTagCompound nbttagcompound = new NBTTagCompound();
				// nbttagcompound.setString("pattern",
				// pattern.getId().toString());
				// nbttagcompound.setInteger("color", k);
				// patternsNbt.appendTag(nbttagcompound);
			}
			return itemstack;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return ItemStack.EMPTY;
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
			NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(),
					ItemStack.EMPTY);
			for (int i = 0; i < nonnulllist.size(); ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
			}
			return nonnulllist;
		}

		// Copied from the banner crafting & lightly modified
		@Nullable
		private IApparelPattern matchPatterns(InventoryCrafting inv) {
			// return EnumScarfPattern.BASE;
			ItemStack apparelStack = null;
			ItemApparelPatterned<?> apparelItem = null;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.getItem() instanceof ItemApparelPatterned) {
					if (apparelStack != null)
						return null;
					apparelStack = stack;
					apparelItem = (ItemApparelPatterned<?>) stack.getItem();
				}
			}
			if (apparelStack == null)
				return null;
			for (IApparelPattern pattern : apparelItem.getPossiblePatterns()) {
				if (pattern.hasPattern()) {
					boolean flag = true;
					if (pattern.hasPatternItem()) {
						boolean flag1 = false;
						boolean flag2 = false;
						for (int i = 0; i < inv.getSizeInventory() && flag; ++i) {
							ItemStack itemstack = inv.getStackInSlot(i);
							if (!itemstack.isEmpty()
									&& itemstack.getItem() instanceof ItemApparelPatterned) {
								if (net.minecraftforge.oredict.DyeUtils.isDye(itemstack)) {
									if (flag2) {
										flag = false;
										break;
									}
									flag2 = true;
								} else {
									if (flag1 || !itemstack.isItemEqual(pattern.getPatternItem())) {
										flag = false;
										break;
									}
									flag1 = true;
								}
							}
						}
						if (!flag1 || !flag2)
							flag = false;
					} else if (inv.getSizeInventory() == pattern.getPatterns().length
							* pattern.getPatterns()[0].length()) {
						int j = -1;
						for (int k = 0; k < inv.getSizeInventory() && flag; ++k) {
							int l = k / 3;
							int i1 = k % 3;
							ItemStack itemstack1 = inv.getStackInSlot(k);
							if (!itemstack1.isEmpty()
									&& !(itemstack1.getItem() instanceof ItemApparelPatterned)) {
								if (!net.minecraftforge.oredict.DyeUtils.isDye(itemstack1)) {
									flag = false;
									break;
								}
								if (j != -1 && j != itemstack1.getMetadata()) {
									flag = false;
									break;
								}
								if (pattern.getPatterns()[l].charAt(i1) == ' ') {
									flag = false;
									break;
								}
								j = itemstack1.getMetadata();
							} else if (pattern.getPatterns()[l].charAt(i1) != ' ') {
								flag = false;
								break;
							}
						}
					} else
						flag = false;
					if (flag)
						return pattern;
				}
			}
			return null;
		}

		@Override
		public boolean isDynamic() {
			return true;
		}

		@Override
		public boolean canFit(int width, int height) {
			return width >= 3 && height >= 3;
		}

	}

	// public static class RecipeDuplicatePattern
	// extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe>
	// implements IRecipe {
	//
	// /**
	// * Used to check if a recipe matches current crafting inventory
	// */
	// public boolean matches(InventoryCrafting inv, World worldIn) {
	// ItemStack itemstack = ItemStack.EMPTY;
	// ItemStack itemstack1 = ItemStack.EMPTY;
	//
	// for (int i = 0; i < inv.getSizeInventory(); ++i) {
	// ItemStack itemstack2 = inv.getStackInSlot(i);
	//
	// if (!itemstack2.isEmpty()) {
	// if (itemstack2.getItem() != Items.BANNER) {
	// return false;
	// }
	//
	// if (!itemstack.isEmpty() && !itemstack1.isEmpty()) {
	// return false;
	// }
	//
	// EnumDyeColor enumdyecolor = ItemBanner.getBaseColor(itemstack2);
	// boolean flag = TileEntityBanner.getPatterns(itemstack2) > 0;
	//
	// if (!itemstack.isEmpty()) {
	// if (flag) {
	// return false;
	// }
	//
	// if (enumdyecolor != ItemBanner.getBaseColor(itemstack)) {
	// return false;
	// }
	//
	// itemstack1 = itemstack2;
	// } else if (!itemstack1.isEmpty()) {
	// if (!flag) {
	// return false;
	// }
	//
	// if (enumdyecolor != ItemBanner.getBaseColor(itemstack1)) {
	// return false;
	// }
	//
	// itemstack = itemstack2;
	// } else if (flag) {
	// itemstack = itemstack2;
	// } else {
	// itemstack1 = itemstack2;
	// }
	// }
	// }
	//
	// return !itemstack.isEmpty() && !itemstack1.isEmpty();
	// }
	//
	// /**
	// * Returns an Item that is the result of this recipe
	// */
	// public ItemStack getCraftingResult(InventoryCrafting inv) {
	// for (int i = 0; i < inv.getSizeInventory(); ++i) {
	// ItemStack itemstack = inv.getStackInSlot(i);
	//
	// if (!itemstack.isEmpty() && TileEntityBanner.getPatterns(itemstack) > 0)
	// {
	// ItemStack itemstack1 = itemstack.copy();
	// itemstack1.setCount(1);
	// return itemstack1;
	// }
	// }
	//
	// return ItemStack.EMPTY;
	// }
	//
	// /**
	// * Get the result of this recipe, usually for display purposes (e.g.
	// * recipe book). If your recipe has more than one possible result (e.g.
	// * it's dynamic and depends on its inputs), then return an empty stack.
	// */
	// public ItemStack getRecipeOutput() {
	// return ItemStack.EMPTY;
	// }
	//
	// public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
	// NonNullList<ItemStack> nonnulllist =
	// NonNullList.<ItemStack>withSize(inv.getSizeInventory(),
	// ItemStack.EMPTY);
	//
	// for (int i = 0; i < nonnulllist.size(); ++i) {
	// ItemStack itemstack = inv.getStackInSlot(i);
	//
	// if (!itemstack.isEmpty()) {
	// if (itemstack.getItem().hasContainerItem(itemstack)) {
	// nonnulllist.set(i,
	// net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
	// } else if (itemstack.hasTagCompound()
	// && TileEntityBanner.getPatterns(itemstack) > 0) {
	// ItemStack itemstack1 = itemstack.copy();
	// itemstack1.setCount(1);
	// nonnulllist.set(i, itemstack1);
	// }
	// }
	// }
	//
	// return nonnulllist;
	// }
	//
	// /**
	// * If true, this recipe does not appear in the recipe book and does not
	// * respect recipe unlocking (and the doLimitedCrafting gamerule)
	// */
	// public boolean isDynamic() {
	// return true;
	// }
	//
	// /**
	// * Used to determine if this recipe can fit in a grid of the given
	// * width/height
	// */
	// public boolean canFit(int width, int height) {
	// return width * height >= 2;
	// }
	// }

}