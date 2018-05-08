package selim.penguins;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IApparelPattern {

	public ResourceLocation getTexture();

	public ResourceLocation getId();

	public String[] getPatterns();

	public boolean hasPattern();

	public boolean hasPatternItem();

	public ItemStack getPatternItem();

	public static class ApparelPatterns {

		private static final List<IApparelPattern> PATTERNS = new LinkedList<IApparelPattern>();

		public static void addPatterns(IApparelPattern[] patterns) {
			for (IApparelPattern pattern : patterns)
				if (!PATTERNS.contains(pattern))
					PATTERNS.add(pattern);
		}

		public static IApparelPattern[] getAllPatterns() {
			return (IApparelPattern[]) PATTERNS.toArray(new IApparelPattern[PATTERNS.size()]);
		}

		@SuppressWarnings("unchecked")
		public static <E extends IApparelPattern> E[] getPatterns(Class<E> clazz) {
			List<IApparelPattern> matching = new ArrayList<IApparelPattern>();
			for (IApparelPattern pattern : PATTERNS)
				if (clazz.isInstance(pattern))
					matching.add(pattern);
			return (E[]) matching.toArray(new IApparelPattern[matching.size()]);
		}
	}

}
