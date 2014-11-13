package com.danwink.space_cruiser.components;

import com.badlogic.ashley.core.Component;

public class MoveComponent extends Component
{
	public float x, y;
	public Direction facing;
	
	public enum Direction
	{
		NORTH( 0, -1 ),
		SOUTH( 0, 1 ),
		EAST( 1, 0 ),
		WEST( -1, 0 );
		
		int x, y;
		Direction( int x, int y )
		{
			this.x = x;
			this.y = y;
		}
	}
}
