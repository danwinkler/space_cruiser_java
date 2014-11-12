package com.danwink.space_cruiser.game_objects;

public enum ShipSize
{
	A( "A-Class", 12, 8 ),
	B( "B-Class", 15, 10 ),
	C( "C-Class", 20, 15 );
	
	String name;
	int width;
	int height;
	
	ShipSize( String name, int width, int height )
	{
		this.name = name;
		this.width = width;
		this.height = height;
	}
}