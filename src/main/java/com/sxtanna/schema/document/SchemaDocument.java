package com.sxtanna.schema.document;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface SchemaDocument
{

	<T> Optional<T> get(@NotNull final String path,
						@NotNull final Class<T> clazz);


	<T> Optional<List<T>> getList(@NotNull final String path,
								  @NotNull final Class<T> clazz);


	<K, V> Optional<Map<K, V>> getMap(@NotNull final String path,
									  @NotNull final Class<K> clazzK,
									  @NotNull final Class<V> clazzV);


	<T> void set(@NotNull final String path,
				 @Nullable final T data);


	void save(@NotNull final Writer writer);


	enum Type
	{
		JSON(SchemaDocumentJson::new);


		private final Function<Reader, SchemaDocument> function;

		Type(final Function<Reader, SchemaDocument> function)
		{
			this.function = function;
		}


		public SchemaDocument get(@NotNull final Reader reader)
		{
			return function.apply(reader);
		}

	}

}
