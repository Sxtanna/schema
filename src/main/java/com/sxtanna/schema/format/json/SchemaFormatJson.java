package com.sxtanna.schema.format.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sxtanna.schema.format.SchemaFormat;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
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
		JsonElement node = json;

		for (final String next : path.split("\\."))
		{
			if (node == null || !node.isJsonObject())
			{
				return Optional.empty();
			}

			node = node.getAsJsonObject().get(next);
		}

		if (node == null)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(GSON.fromJson(node, clazz));
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
