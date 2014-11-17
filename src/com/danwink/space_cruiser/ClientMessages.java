package com.danwink.space_cruiser;

import game_framework.TileMap.Tile;

import com.phyloa.dlib.math.Point2i;

public class ClientMessages
{
	public static enum ShipBuild
	{
		JOIN,
		CHANGETILE
	}
	
	public static class ShipBuildChangeTilePacket
	{
		public Point2i p;
		public Tile t;
		
		public ShipBuildChangeTilePacket()
		{
			
		}
		
		public ShipBuildChangeTilePacket( Tile t, Point2i p )
		{
			this.t = t;
			this.p = p;
		}
	}
	
	public static enum Ship
	{
		JOIN,
	}
	
	public static enum StarMap
	{
		VIEW
	}
}
