package com.danwink.space_cruiser.components;

import game_framework.SyncReference;

import java.util.LinkedList;

import com.badlogic.ashley.core.Component;
import com.phyloa.dlib.math.Point2i;

public class MoveComponent extends Component
{
	public float x, y;
	public Direction facing;
	public LinkedList<Point2i> path;
	public SyncReference<MapComponent> map;
	
	public enum Direction
	{
		NORTH( 0, -1 ),
		SOUTH( 0, 1 ),
		EAST( 1, 0 ),
		WEST( -1, 0 );
		
		public int x;
		public int y;
		
		Direction( int x, int y )
		{
			this.x = x;
			this.y = y;
		}
		
		public static Direction fromCoords( int x, int y )
		{
			for( Direction d : values() )
			{
				if( d.x == x && d.y == y ) return d;
			}
			return null;
		}
	}
}
