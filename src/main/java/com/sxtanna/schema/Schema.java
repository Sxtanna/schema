package com.sxtanna.schema;

import com.sxtanna.schema.format.SchemaFormat;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Schema implements SchemaFormat
{

	private final SchemaFormat format;


	private Schema(final SchemaFormat format)
	{
		this.format = format;
	}


	@Override
	public <T> Optional<T> get(@NotNull final String path, @NotNull final Class<T> clazz)
	{
		return format.get(path, clazz);
	}

	@Override
	public <T> Optional<List<T>> getList(@NotNull final String path, @NotNull final Class<T> clazz)
	{
		return format.getList(path, clazz);
	}

	@Override
	public <K, V> Optional<Map<K, V>> getMap(final @NotNull String path, final @NotNull Class<K> clazzK, final @NotNull Class<V> clazzV)
	{
		return format.getMap(path, clazzK, clazzV);
	}


	public static Schema load(@NotNull final Reader reader, @NotNull final SchemaFormat.Type type)
	{
		return new Schema(type.get(reader));
	}

}
