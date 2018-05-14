package selim.penguins.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import selim.penguins.IApparelPattern;
import selim.penguins.items.ItemApparelPatterned;
import selim.penguins.items.ItemApparelPatterned.ColoredPattern;

public class ApparelPatternWrapper<E extends IApparelPattern> implements IShapedCraftingRecipeWrapper {

	private final ItemApparelPatterned<E> apparel;
	private final E pattern;

	public ApparelPatternWrapper(ItemApparelPatterned<E> apparel, E pattern) {
		this.apparel = apparel;
		this.pattern = pattern;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> dyes = new LinkedList<ItemStack>();
		List<ItemStack> outputs = new LinkedList<ItemStack>();
		for (ItemStack dye : OreDictionary.getOres("dye")) {
			ItemStack apparelStack = new ItemStack(apparel);
			dyes.add(dye);
			apparel.addPattern(apparelStack, new ColoredPattern(this.pattern, EnumDyeColor.WHITE));
			outputs.add(apparelStack);
		}
		List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
		if (pattern.hasPatternItem())
			inputs.add(Collections.singletonList(pattern.getPatternItem()));
		else {
			String[] lines = pattern.getPatterns();
			for (int x = 0; x < lines.length && x < 3; x++) {
				String line = lines[x];
				for (int y = 0; y < line.length() & y < 3; y++) {
					char c = line.charAt(y);
					if (!Character.isWhitespace(c))
						inputs.add((x * 3) + y, dyes);
					else
						inputs.add((x * 3) + y, Collections.singletonList(ItemStack.EMPTY));
				}
			}
		}
		for (int i = 0; i < inputs.size(); i++) {
			List<ItemStack> input = inputs.get(i);
			if (input.size() == 1 && input.get(0).equals(ItemStack.EMPTY)) {
				inputs.set(i, Collections.singletonList(new ItemStack(apparel)));
				break;
			}
		}
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputLists(ItemStack.class, Collections.singletonList(outputs));
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		return 3;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return pattern.getId();
	}

}
