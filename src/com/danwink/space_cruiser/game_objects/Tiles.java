package com.danwink.space_cruiser.game_objects;

import com.danwink.space_cruiser.game_objects.Items.Item;

import game_framework.TileMap.Tile;
import game_framework.TileMap.Wall;

public class Tiles
{
	public static class SCTile
	{
		TileType t;
		float oxygen;
		Item item;
		float damage;
	}
	
	public enum TileType
	{
		FLOOR( true );
		
		boolean passable;
		
		TileType( boolean passable )
		{
			this.passable = passable;
		}
	}
	
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
