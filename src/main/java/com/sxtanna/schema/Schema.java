package com.sxtanna.schema;

import com.sxtanna.schema.document.SchemaDocument;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.io.Reader;

public final class Schema
{

	public static SchemaDocument load(@NotNull final Reader reader, @NotNull final SchemaDocument.Type type)
	{
		return type.get(reader);
	}

	public static SchemaDocument loadFromResource(@NotNull final String path, @NotNull final SchemaDocument.Type type)
	{
		return load(new InputStreamReader(Schema.class.getResourceAsStream(path)), type);
	}

}
