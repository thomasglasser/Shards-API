package dev.thomasglasser.sherdsapi.api.data;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Loader-agnostic base implementation of Sherd datagen
 */
public abstract class BaseSherdDatagenSuite<T extends BaseSherdDatagenSuite<T>>
{
	/**
	 * The id of the mod making sherds
	 */
	protected String modId;

	/**
	 * The list of sherds to be generated and their keys
	 */
	protected List<Pair<ResourceKey<Sherd>, Sherd>> sherds = new ArrayList<>();

	/**
	 * @param modId The id of the mod making sherds.
	 */
	public BaseSherdDatagenSuite(String modId) {
		this.modId = modId;
		this.generate();
	}

	/**
	 * A designated method for making sherd suites if the chain method is not preferred
	 */
	public void generate() {
	}

	/**
	 * Creates a resource key for a sherd with the given path.
	 * @param path The path of the sherd.
	 * @return The resource key of the sherd.
	 */
	protected ResourceKey<Sherd> sherdKey(String path)
	{
		return SherdsApiRegistries.sherdKey(new ResourceLocation(modId, path));
	}

	/**
	 * Creates a resource key for a pattern with the given path
	 * @param path The path of the pattern
	 * @return The resource key of the pattern
	 */
	public ResourceKey<String> patternKey(String path)
	{
		return ResourceKey.create(Registries.DECORATED_POT_PATTERNS, new ResourceLocation(modId, path));
	}

	/**
	 * Creates a resource location with the generator's mod ID and the given path
	 * @param path The path of the resource
	 * @return The location of the resource
	 */
	public ResourceLocation modLoc(String path)
	{
		return new ResourceLocation(modId, path);
	}

	/**
	 * Creates a sherd with the given key, ingredient, and pattern and adds it to the suite
	 * @param key The key of the sherd
	 * @param ingredient The ingredient used to make the sherd
	 * @param pattern The key of the pattern
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, Ingredient ingredient, ResourceKey<String> pattern)
	{
		Optional<ResourceKey<String>> patternOptional;
		if (key.location().equals(pattern.location()))
			patternOptional = Optional.empty();
		else
			patternOptional = Optional.of(pattern);
		sherds.add(Pair.of(key, new Sherd(ingredient, patternOptional)));
		return (T) this;
	}

	public T makeSherdSuite(ResourceLocation key, Ingredient ingredient, ResourceKey<String> pattern)
	{
		return makeSherdSuite(SherdsApiRegistries.sherdKey(key), ingredient, pattern);
	}

	public T makeSherdSuite(String key, Ingredient ingredient, ResourceKey<String> pattern)
	{
		return makeSherdSuite(sherdKey(key), ingredient, pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, Ingredient ingredient, ResourceLocation pattern)
	{
		return makeSherdSuite(key, ingredient, ResourceKey.create(Registries.DECORATED_POT_PATTERNS, pattern));
	}

	public T makeSherdSuite(ResourceLocation key, Ingredient ingredient, ResourceLocation pattern)
	{
		return makeSherdSuite(SherdsApiRegistries.sherdKey(key), ingredient, pattern);
	}

	public T makeSherdSuite(String key, Ingredient ingredient, ResourceLocation pattern)
	{
		return makeSherdSuite(sherdKey(key), ingredient, pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, Ingredient ingredient, String pattern)
	{
		return makeSherdSuite(key, ingredient, patternKey(pattern));
	}

	public T makeSherdSuite(ResourceLocation key, Ingredient ingredient, String pattern)
	{
		return makeSherdSuite(SherdsApiRegistries.sherdKey(key), ingredient, pattern);
	}

	public T makeSherdSuite(String key, Ingredient ingredient, String pattern)
	{
		return makeSherdSuite(sherdKey(key), ingredient, pattern);
	}

	/**
	 * Creates a sherd with the given key, items, and pattern and adds it to the suite
	 * @param key The key of the sherd
	 * @param items The items that can be used to make the sherd
	 * @param pattern The key of the pattern
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, List<ItemLike> items, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, List<ItemLike> items, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(String key, List<ItemLike> items, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, List<ItemLike> items, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, List<ItemLike> items, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(String key, List<ItemLike> items, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, List<ItemLike> items, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, List<ItemLike> items, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	public T makeSherdSuite(String key, List<ItemLike> items, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])), pattern);
	}

	/**
	 * Creates a sherd with the given key, item, and pattern and adds it to the suite
	 * @param key The key of the sherd
	 * @param item The item used to make the sherd
	 * @param pattern The key of the pattern
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, ItemLike item, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, ItemLike item, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(String key, ItemLike item, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, ItemLike item, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, ItemLike item, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(String key, ItemLike item, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, ItemLike item, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, ItemLike item, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	public T makeSherdSuite(String key, ItemLike item, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(item), pattern);
	}

	/**
	 * Creates a sherd with the given key, tag, and pattern and adds it to the suite
	 * @param key The key of the sherd
	 * @param tag The tag with items that can be used to make the sherd
	 * @param pattern The key of the pattern
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, TagKey<Item> tag, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, TagKey<Item> tag, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(String key, TagKey<Item> tag, ResourceKey<String> pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, TagKey<Item> tag, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, TagKey<Item> tag, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(String key, TagKey<Item> tag, ResourceLocation pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(ResourceKey<Sherd> key, TagKey<Item> tag, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(ResourceLocation key, TagKey<Item> tag, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	public T makeSherdSuite(String key, TagKey<Item> tag, String pattern)
	{
		return makeSherdSuite(key, Ingredient.of(tag), pattern);
	}

	/**
	 * Creates a sherd with the same sherd and pattern key and given ingredient and adds it to the suite
	 * @param key The key of the sherd and pattern
	 * @param ingredient The ingredient used to make the sherd
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, Ingredient ingredient)
	{
		return makeSherdSuite(key, ingredient, key.location());
	}

	public T makeSherdSuite(ResourceLocation key, Ingredient ingredient)
	{
		return makeSherdSuite(key, ingredient, key);
	}

	public T makeSherdSuite(String key, Ingredient ingredient)
	{
		return makeSherdSuite(key, ingredient, key);
	}

	/**
	 * Creates a sherd with the same sherd and pattern key and given items and adds it to the suite
	 * @param key The key of the sherd and pattern
	 * @param items The items that can be used to make the sherd
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, List<ItemLike> items)
	{
		return makeSherdSuite(key, items, key.location());
	}

	public T makeSherdSuite(ResourceLocation key, List<ItemLike> items)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])));
	}

	public T makeSherdSuite(String key, List<ItemLike> items)
	{
		return makeSherdSuite(key, Ingredient.of(items.toArray(new ItemLike[0])));
	}

	/**
	 * Creates a sherd with the same sherd and pattern key and given tag and adds it to the suite
	 * @param key The key of the sherd and pattern
	 * @param tag The tag with items that can be used to make the sherd
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, TagKey<Item> tag)
	{
		return makeSherdSuite(key, tag, key.location());
	}

	public T makeSherdSuite(ResourceLocation key, TagKey<Item> tag)
	{
		return makeSherdSuite(key, Ingredient.of(tag));
	}

	public T makeSherdSuite(String key, TagKey<Item> tag)
	{
		return makeSherdSuite(key, Ingredient.of(tag));
	}

	/**
	 * Creates a sherd with the same sherd and pattern key and given item and adds it to the suite
	 * @param key The key of the sherd and pattern
	 * @param item The item used to make the sherd
	 * @return The datagen suite with the sherd added
	 */

	public T makeSherdSuite(ResourceKey<Sherd> key, ItemLike item)
	{
		return makeSherdSuite(key, item, key.location());
	}

	public T makeSherdSuite(ResourceLocation key, ItemLike item)
	{
		return makeSherdSuite(key, Ingredient.of(item));
	}

	public T makeSherdSuite(String key, ItemLike item)
	{
		return makeSherdSuite(key, Ingredient.of(item));
	}
}
