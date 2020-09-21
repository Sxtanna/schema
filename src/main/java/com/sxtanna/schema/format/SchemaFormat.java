package com.sxtanna.schema.format;

import com.sxtanna.schema.format.json.SchemaFormatJson;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface SchemaFormat
{

	<T> Optional<T> get(@NotNull final String path, @NotNull final Class<T> clazz);

	<T> Optional<List<T>> getList(@NotNull final String path, @NotNull final Class<T> clazz);

	<K, V> Optional<Map<K, V>> getMap(@NotNull final String path, @NotNull final Class<K> clazzK, @NotNull final Class<V> clazzV);


	enum Type
	{
		JSON(SchemaFormatJson::new);


		private final Function<Reader, SchemaFormat> function;

		Type(final Function<Reader, SchemaFormat> function)
		{
			this.function = function;
		}


		public SchemaFormat get(@NotNull final Reader reader)
		{
			return function.apply(reader);
		}

	}

}
