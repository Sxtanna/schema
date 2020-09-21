package com.sxtanna.schema.format;

import com.sxtanna.schema.format.json.SchemaFormatJson;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.Optional;
import java.util.function.Function;

public interface SchemaFormat
{

	<T> Optional<T> get(@NotNull final String path, @NotNull final Class<T> clazz);


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
