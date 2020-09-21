package com.sxtanna.schema;

import com.sxtanna.schema.document.SchemaDocument;
import com.sxtanna.schema.model.MapData;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public final class SchemaTest implements WithAssertions
{

	@Test
	void test_schema_get()
	{
		final SchemaDocument schema = Schema.loadFromResource("/data.json",
															  SchemaDocument.Type.JSON);

		assertThat(schema.get("", MapData.class))
				.isPresent()
				.get()
				.hasNoNullFieldsOrProperties();

		assertThat(schema.getList("data.builders", String.class))
				.isPresent()
				.get()
				.asList()
				.contains("Sxtanna",
						  "Axtanna");
	}

	@Test
	void test_schema_set()
	{
		final SchemaDocument schema = Schema.loadFromResource("/data.json",
															  SchemaDocument.Type.JSON);

		assertThat(schema.get("min.t", Integer.class)).isEmpty();

		assertThatNoException().isThrownBy(() -> schema.set("min.t", 21));

		assertThat(schema.get("min.t", Integer.class)).contains(21);
	}

}
