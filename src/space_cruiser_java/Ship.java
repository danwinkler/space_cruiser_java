package space_cruiser_java;

import game_framework.TileMap;

public class Ship 
{
	TileMap map;
	ShipSize size;
	
	public Ship( ShipSize size )
	{
		this.size = size;
		map = new TileMap( size.width+2, size.height+2 ); //Extra size for exterior cosmetic tiles
	}
	
	public enum ShipSize
	{
		A( "A-Class", 8, 12 ),
		B( "B-Class", 10, 15 ),
		C( "C-Class", 15, 20 );
		
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
}
