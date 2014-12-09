package com.danwink.space_cruiser.game_objects;

import java.util.List;

import com.danwink.space_cruiser.game_objects.Items.Item;
import com.phyloa.dlib.util.DUtil;

import game_framework.TileMap.Tile;
import game_framework.TileMap.Wall;

public class Tiles
{
	public static class SCTile implements Tile
	{
		public TileType t = TileType.FLOOR;
		public float oxygen;
		public Item item;
		public float damage;
		
		@Override
		public boolean isPassable()
		{
			return t != null ? t.passable : false;
		}
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
	
	public static class NormalWall implements Wall
	{
		public boolean isPassable()
		{
			return false;
		}		
	}
	
	public static List<Class<? extends Tile>> getTiles()
	{
		return DUtil.getSubclasses( Tiles.class, Tile.class );
	}
	
	public static List<Class<? extends Wall>> getWalls()
	{
		return DUtil.getSubclasses( Tiles.class, Wall.class );
	}
}
