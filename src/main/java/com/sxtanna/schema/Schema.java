package com.sxtanna.schema;

import com.sxtanna.schema.format.SchemaFormat;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
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



	public static Schema load(@NotNull final Reader reader, @NotNull final SchemaFormat.Type type)
	{
		return new Schema(type.get(reader));
	}

}
