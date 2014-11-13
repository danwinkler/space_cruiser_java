package com.danwink.space_cruiser.game_objects;

import game_framework.TileMap.Tile;
import game_framework.TileMap.Wall;

public class Tiles
{
	public static class Floor implements Tile
	{
		public boolean isPassable() 
		{
			return true;
		}
	}
	
	public static class NormalWall implements Wall
	{
		public boolean isPassable()
		{
			return false;
		}		
	}
}
