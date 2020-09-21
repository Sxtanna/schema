package com.sxtanna.schema;

import com.sxtanna.schema.format.SchemaFormat;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.io.Reader;

public final class Schema
{

	public static SchemaFormat load(@NotNull final Reader reader, @NotNull final SchemaFormat.Type type)
	{
		return type.get(reader);
	}

	public static SchemaFormat loadFromResource(@NotNull final String path, @NotNull final SchemaFormat.Type type)
	{
		return load(new InputStreamReader(Schema.class.getResourceAsStream(path)), type);
	}

}
