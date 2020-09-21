package com.sxtanna.schema;

import com.sxtanna.schema.document.SchemaDocument;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

/**
 * The entry point for creating {@link SchemaDocument} instances.
 */
public final class Schema
{

	/**
	 * Load a document from the provided reader using the provider type
	 *
	 * @param reader where to read the document from
	 * @param type   which type to read the document as
	 *
	 * @return The new document representing the contents from the reader
	 *
	 * @apiNote Makes zero assumptions about the state of the provided reader, it is up to the caller to ensure readiness
	 *          and to close the reader.
	 */
	public static SchemaDocument load(@NotNull final Reader reader, @NotNull final SchemaDocument.Type type)
	{
		return type.get(reader);
	}

}
