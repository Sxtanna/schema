package com.sxtanna.schema.model;

public final class Vector
{

	public final double x;
	public final double y;
	public final double z;


	public Vector(final double x, final double y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	@Override
	public String toString()
	{
		return String.format("Vector[%s, %s, %s]", x, y, z);
	}

}
