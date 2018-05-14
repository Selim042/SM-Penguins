package selim.penguins;

import java.util.HashMap;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.penguins.items.ItemApparel;
import selim.penguins.items.ItemApparelColored;
import selim.penguins.items.ItemApparelPatterned;

@SideOnly(Side.CLIENT)
public class ApparelModelManager {

	private static final HashMap<ItemApparel, ModelBase> MODELS = new HashMap<>();
	private static final HashMap<ItemApparelColored, ResourceLocation[]> ALL_TEXTURES = new HashMap<>();
	private static final HashMap<ItemApparelPatterned<?>, ResourceLocation> BASE_TEXTURES = new HashMap<>();

	public static <T extends ItemApparel> T registerModel(T apparel, ModelBase model) {
		MODELS.put(apparel, model);
		return apparel;
	}

	public static <T extends ItemApparelColored> T registerTextures(T apparel, ResourceLocation... model) {
		ALL_TEXTURES.put(apparel, model);
		return apparel;
	}

	public static <T extends ItemApparelPatterned<?>> T registerBaseTexture(T apparel,
			ResourceLocation model) {
		BASE_TEXTURES.put(apparel, model);
		return apparel;
	}

	public static <T extends ItemApparel> ModelBase getModel(T apparel) {
		return MODELS.get(apparel);
	}

	public static <T extends ItemApparelColored> ResourceLocation[] getTextures(T apparel) {
		return ALL_TEXTURES.get(apparel);
	}

	public static <T extends ItemApparelPatterned<?>> ResourceLocation getBaseTexture(T apparel) {
		return BASE_TEXTURES.get(apparel);
	}

}
