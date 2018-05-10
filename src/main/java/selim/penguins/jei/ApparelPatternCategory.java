package selim.penguins.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import selim.penguins.Penguins;

public class ApparelPatternCategory implements IRecipeCategory<ApparelPatternWrapper<?>> {

	public static final String UID = Penguins.MODID + ":apparel_pattern";
	public static final int width = 116;
	public static final int height = 54;

	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;

	private final IDrawable background;
	private final String localizedName;
	private final ICraftingGridHelper craftingGridHelper;

	public ApparelPatternCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation("minecraft",
				"textures/gui/container/crafting_table.png");
		background = guiHelper.createDrawable(location, 29, 16, width, height);
		localizedName = Translator.translateToLocal("gui.jei.category.craftingTable");
		craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei_title." + Penguins.MODID + ":apparel_pattern");
	}

	@Override
	public String getModName() {
		return Penguins.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ApparelPatternWrapper<?> recipeWrapper,
			IIngredients ingredients) {
		IGuiIngredientGroup<Ingredient> guiIngredients = recipeLayout
				.getIngredientsGroup(Ingredient.class);
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(craftOutputSlot, false, 94, 18);

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = craftInputSlot1 + x + (y * 3);
				guiItemStacks.init(index, true, x * 18, y * 18);
			}
		}

		List<List<Ingredient>> inputs = ingredients.getInputs(Ingredient.class);
		List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class).get(0);

		IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
		craftingGridHelper.setInputs(guiIngredients, inputs, wrapper.getWidth(), wrapper.getHeight());
		guiItemStacks.set(craftOutputSlot, outputs);
	}

}
