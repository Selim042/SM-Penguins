package selim.penguins.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import selim.penguins.IApparelPattern;
import selim.penguins.items.ItemApparelPatterned;
import selim.penguins.items.ItemApparelPatterned.ColoredPattern;

//@Mod.EventBusSubscriber
@JEIPlugin
public class JeiPlugin implements IModPlugin {

	// @Override
	// public void register(IModRegistry registry) {
	// registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE),
	// ApparelPatternCategory.UID);
	// }
	//
	// @Override
	// public void registerCategories(IRecipeCategoryRegistration registry) {
	// registry.addRecipeCategories(
	// new ApparelPatternCategory(registry.getJeiHelpers().getGuiHelper()));
	// }

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// @Optional.Method(modid = "jei")
	// @SubscribeEvent
	// public static void registerDummyRecipes(RegistryEvent.Register<IRecipe>
	// event) {
	// for (ItemApparelPatterned<?> apparel :
	// ItemApparelPatterned.getAllPatternedApparel())
	// for (IApparelPattern pattern : apparel.getPossiblePatterns())
	// event.getRegistry().register(new DummyPatternRecipe(apparel, pattern));
	// }

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(ColoredPattern.class, new IRecipeWrapperFactory<ColoredPattern>() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public IRecipeWrapper getRecipeWrapper(ColoredPattern pattern) {
				return new ApparelPatternWrapper(pattern.getPattern().getApplicableApparel(),
						pattern.getPattern());
			}
		}, VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipes(Arrays.asList(ItemApparelPatterned.getAllPatterns()),
				VanillaRecipeCategoryUid.CRAFTING);
	}

	// private static class DummyPatternRecipe<E extends IApparelPattern>
	// implements IRecipe {
	//
	// private final ItemApparelPatterned<E> apparel;
	// private final E pattern;
	//
	// public DummyPatternRecipe(ItemApparelPatterned<E> apparel, E pattern) {
	// this.apparel = apparel;
	// this.pattern = pattern;
	// }
	//
	// public ItemApparelPatterned<E> getApparel() {
	// return this.apparel;
	// }
	//
	// public E getPattern() {
	// return this.pattern;
	// }
	//
	// @Override
	// public IRecipe setRegistryName(ResourceLocation name) {
	// return this;
	// }
	//
	// @Override
	// public ResourceLocation getRegistryName() {
	// return pattern.getId();
	// }
	//
	// @Override
	// public Class<IRecipe> getRegistryType() {
	// return IRecipe.class;
	// }
	//
	// @Override
	// public boolean matches(InventoryCrafting inv, World worldIn) {
	// return false;
	// }
	//
	// @Override
	// public ItemStack getCraftingResult(InventoryCrafting inv) {
	// return getRecipeOutput();
	// }
	//
	// @Override
	// public boolean canFit(int width, int height) {
	// return true;
	// }
	//
	// @Override
	// public ItemStack getRecipeOutput() {
	// ItemStack apparelStack = new ItemStack(apparel);
	// this.apparel.addPattern(apparelStack, new ColoredPattern<E>(pattern,
	// EnumDyeColor.WHITE));
	// return apparelStack;
	// }
	//
	// }

}
