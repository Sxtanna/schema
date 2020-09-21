package com.sxtanna.schema.format.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sxtanna.schema.format.SchemaFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class SchemaFormatJson implements SchemaFormat
{

	private static final Gson GSON = createGson();


	private final JsonElement json;

	public SchemaFormatJson(@NotNull final Reader reader)
	{
		this.json = JsonParser.parseReader(reader);
	}


	@Override
	public <T> Optional<T> get(@NotNull final String path, @NotNull final Class<T> clazz)
	{
		return Optional.ofNullable(findNode(path))
					   .map(node -> GSON.fromJson(node, clazz));
	}


	@Override
	public <T> Optional<List<T>> getList(@NotNull final String path, @NotNull final Class<T> clazz)
	{
		final JsonElement node = findNode(path);
		if (node == null || !node.isJsonArray())
		{
			return Optional.empty();
		}

		final List<T> list = new ArrayList<>(node.getAsJsonArray().size());

		node.getAsJsonArray()
			.forEach(element -> list.add(GSON.fromJson(element, clazz)));

		return Optional.of(list);
	}


	@Override
	public <K, V> Optional<Map<K, V>> getMap(@NotNull final String path, @NotNull final Class<K> clazzK, @NotNull final Class<V> clazzV)
	{
		final JsonElement node = findNode(path);
		if (node == null || !node.isJsonObject())
		{
			return Optional.empty();
		}

		final Map<K, V> hash = new HashMap<>();

		for (final String k : node.getAsJsonObject().keySet())
		{
			final JsonElement v = node.getAsJsonObject().get(k);

			hash.put(GSON.fromJson(k, clazzK),
					 GSON.fromJson(v, clazzV));
		}

		return Optional.of(hash);
	}


	@Nullable
	private JsonElement findNode(@NotNull final String path)
	{
		JsonElement node = json;

		for (final String next : path.split("\\."))
		{
			if (node == null || !node.isJsonObject())
			{
				return null;
			}

			node = node.getAsJsonObject().get(next);
		}

		return node;
	}


	private static Gson createGson()
	{
		final GsonBuilder builder = new GsonBuilder();

		builder.serializeNulls()
			   .setPrettyPrinting()
			   .enableComplexMapKeySerialization()
			   .serializeSpecialFloatingPointValues();

		return builder.create();
	}

}
