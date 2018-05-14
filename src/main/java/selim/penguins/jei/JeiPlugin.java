package selim.penguins.jei;

import java.util.Arrays;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import selim.penguins.IApparelPattern;
import selim.penguins.items.ItemApparelPatterned;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(IApparelPattern.class, new IRecipeWrapperFactory<IApparelPattern>() {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public IRecipeWrapper getRecipeWrapper(IApparelPattern pattern) {
				return new ApparelPatternWrapper(pattern.getApplicableApparel(), pattern);
			}
		}, VanillaRecipeCategoryUid.CRAFTING);
		registry.addRecipes(Arrays.asList(ItemApparelPatterned.getAllPatterns()),
				VanillaRecipeCategoryUid.CRAFTING);
	}

}
