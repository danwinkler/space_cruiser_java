package com.danwink.space_cruiser;

import game_framework.TileMap.Tile;

import com.danwink.space_cruiser.game_objects.Tiles.SCTile;
import com.phyloa.dlib.math.Point2i;

public class ClientMessages
{	
	public static enum Ship
	{
		JOIN,
	}
	
	public static enum Edit
	{
		CHANGE
	}
	
	public static class EditChangePacket
	{
		SCTile tile;
		Point2i pos;
		
		public EditChangePacket( SCTile tile, Point2i pos )
		{
			this.tile = tile;
			this.pos = pos;
		}
	}
	
	public static enum StarMap
	{
		VIEW
	}
}
