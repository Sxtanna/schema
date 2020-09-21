package com.sxtanna.schema.model;

import java.util.List;
import java.util.Map;

public final class MapData
{

	public Data data;

	public Vector min;
	public Vector max;

	public Map<Integer, Vector> spawns;


	public static final class Data
	{

		private List<String> builders;

	}


	@Override
	public String toString()
	{
		return String.format("MapData{data=%s, min=%s, max=%s, spawns=%s}", data, min, max, spawns);
	}

}
