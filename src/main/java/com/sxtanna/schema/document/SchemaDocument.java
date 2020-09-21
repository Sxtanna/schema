package com.sxtanna.schema.document;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * A document that holds data in various formats, accessible through paths delimited by periods.
 */
public interface SchemaDocument
{

	/**
	 * Attempt to retrieve an instance of {@link T} from the provided path
	 *
	 * @param path  period delimited path to the object
	 * @param clazz type of the object
	 *
	 * @return An optional containing the object at this path
	 */
	<T> Optional<T> get(@NotNull final String path,
						@NotNull final Class<T> clazz);


	/**
	 * Attempt to retrieve a list of {@link T} objects from the provided path
	 *
	 * @param path  period delimited path to the list
	 * @param clazz type of the objects in the list
	 *
	 * @return An optional containing the list of objects at this path
	 */
	<T> Optional<List<T>> getList(@NotNull final String path,
								  @NotNull final Class<T> clazz);


	/**
	 * Attempt to retrieve a map of {@link K} to {@link V} objects from the provided path
	 *
	 * @param path   period delimited path to the map
	 * @param clazzK type of the keys in the map
	 * @param clazzV type of the values in the map
	 *
	 * @return An optional containing the map at this path
	 */
	<K, V> Optional<Map<K, V>> getMap(@NotNull final String path,
									  @NotNull final Class<K> clazzK,
									  @NotNull final Class<V> clazzV);


	/**
	 * Updates the value at the provided path with the provided value
	 *
	 * @param path period delimited path
	 * @param data value to insert into the document
	 *
	 * @apiNote This method does not create parent nodes on a path
	 */
	<T> void set(@NotNull final String path,
				 @Nullable final T data);


	/**
	 * Save this document to the provided writer
	 *
	 * @param writer the writer to consume this document in text form
	 */
	void save(@NotNull final Writer writer);


	/**
	 * The "type" or "format" of a specific schema document.
	 */
	enum Type
	{
		/**
		 * A document using JSON.
		 */
		JSON(SchemaDocumentJson::new);


		private final Function<Reader, SchemaDocument> function;

		Type(final Function<Reader, SchemaDocument> function)
		{
			this.function = function;
		}


		/**
		 * Parse a {@link SchemaDocument} from a the provided reader using this type
		 *
		 * @param reader where to read the contents of the document from
		 *
		 * @return a document representing the contents of the reader
		 */
		public SchemaDocument get(@NotNull final Reader reader)
		{
			return function.apply(reader);
		}

	}

}
