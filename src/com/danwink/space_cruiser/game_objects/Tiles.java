package com.danwink.space_cruiser.game_objects;

import game_framework.TileMap.Tile;

public class Tiles
{
	public static class Floor implements Tile
	{
		public boolean isPassable() 
		{
			return true;
		}
	}
}
