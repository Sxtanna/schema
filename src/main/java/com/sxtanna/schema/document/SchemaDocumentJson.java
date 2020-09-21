package com.sxtanna.schema.document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class SchemaDocumentJson implements SchemaDocument
{

	private static final Gson GSON = createGson();


	private JsonElement json;


	SchemaDocumentJson(@NotNull final Reader reader)
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


	@Override
	public <T> void set(@NotNull final String path, @Nullable final T data)
	{
		if (path.trim().isEmpty())
		{
			json = GSON.toJsonTree(data);
			return;
		}

		final int period = path.lastIndexOf('.');
		if (period == -1)
		{
			if (!json.isJsonObject())
			{
				return;
			}

			json.getAsJsonObject().add(path, GSON.toJsonTree(data));

			return;
		}

		final JsonElement node = findNode(path.substring(0, period));
		if (node == null || !node.isJsonObject())
		{
			return;
		}

		node.getAsJsonObject().add(path.substring(period + 1), GSON.toJsonTree(data));
	}


	@Override
	public void save(final @NotNull Writer writer)
	{
		GSON.toJson(json, writer);
	}


	@Nullable
	private JsonElement findNode(@NotNull final String path)
	{
		JsonElement node = json;
		if (path.trim().isEmpty())
		{
			return node;
		}

		for (final String next : path.trim().split("\\."))
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
