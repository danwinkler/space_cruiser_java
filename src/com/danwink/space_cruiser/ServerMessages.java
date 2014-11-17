package com.danwink.space_cruiser;

import game_framework.TileMap.Tile;

import com.phyloa.dlib.math.Point2i;

public class ServerMessages
{
	public enum ShipBuild
	{
		SHIP,
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
	
	public enum SpaceNavigate
	{
		
	}
	
	public enum Ship
	{
		SHIP,
		ENTITIES
	}
	
	public enum StarMap
	{
		VIEW
	}
}
