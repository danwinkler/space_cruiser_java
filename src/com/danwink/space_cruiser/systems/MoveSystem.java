package com.danwink.space_cruiser.systems;

import game_framework.BetterIteratingSystem;
import game_framework.SyncEngine;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.components.MoveComponent.Direction;
import com.phyloa.dlib.math.Point2i;

public class MoveSystem extends BetterIteratingSystem
{	
	public MoveSystem()
	{
		super( Family.all( MoveComponent.class ).get() );
	}
	
	protected void processEntity( Entity entity, float delta )
	{
		MoveComponent move = Mappers.move.get( entity );
		
		float speed = 50;
		float finishDistance = 1f;
		
		if( move.path != null )
		{
			MapComponent map = move.map.get();
			
			Point2i current = move.path.getFirst();
			Point2i next = move.path.get( 1 );
			
			if( move.facing == null )
			{
				move.facing = Direction.fromCoords( next.x - current.x, next.y - current.y );
			}
			
			move.x += (next.x - current.x) * speed * delta;
			move.y += (next.y - current.y) * speed * delta;
			
			float dx = (move.x/map.map.scale) - (next.x+.5f);
			float dy = (move.y/map.map.scale) - (next.y+.5f);
			
			if( dx * move.facing.x >= 0 && dy * move.facing.y >= 0 )
			{
				move.path.removeFirst();
				if( move.path.size() == 1 )
				{
					move.path = null;
				}
				else
				{
					current = next;
					next = move.path.get( 1 );
					
					move.facing = MoveComponent.Direction.fromCoords( next.x - current.x, next.y - current.y );
					move.x = current.x * map.map.scale + map.map.scale*.5f;
					move.y = current.y * map.map.scale + map.map.scale*.5f;
				}
			}
		}
	}
}
