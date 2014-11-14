package com.danwink.space_cruiser.systems;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import game_framework.BetterIteratingSystem;
import game_framework.SyncEngine;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.components.MoveComponent.Direction;
import com.phyloa.dlib.math.Point2i;
import com.phyloa.dlib.util.DMath;

public class RandomMoverSystem extends BetterIteratingSystem
{
	HashMap<MapComponent, PathFinder> finders = new HashMap<MapComponent, PathFinder>();
	
	public RandomMoverSystem()
	{
		super( Family.all( MoveComponent.class ).get() );
	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		MoveComponent move = Mappers.move.get( entity );
		if( move.path == null )
		{
			MapComponent map = Mappers.map.get( ((SyncEngine)getEngine()).getBySyncId( move.mapEntityId ) );
			PathFinder finder = getFinder( map );
			Path p = null;
			Point2i tileSpace = null;
			do
			{
				tileSpace = map.map.worldToTileSpace( move.x, move.y );
				if( map.map.blocked( null, tileSpace.x, tileSpace.y ) )
				{
					move.x = DMath.randomi( 0, map.map.width ) * map.map.scale;
					move.y = DMath.randomi( 0, map.map.height ) * map.map.scale;
					continue;
				}
				p = finder.findPath( null, tileSpace.x, tileSpace.y, DMath.randomi( 0, map.map.width ), DMath.randomi( 0, map.map.height ) );
			} while( p == null );
			
			move.path = new LinkedList<Point2i>();
			for( int i = 0; i < p.getLength(); i++ )
			{
				move.path.addLast( new Point2i( p.getX( i ), p.getY( i ) ) );
				move.facing = Direction.fromCoords( p.getX(1)-p.getX(0), p.getY(1)-p.getY(0) );
			}
		}
	}
	
	PathFinder getFinder( MapComponent map )
	{
		PathFinder finder = finders.get( map );
		if( finder == null )
		{
			finder = new AStarPathFinder( map.map, (map.map.width + map.map.height) * 5, false );
			finders.put( map, finder );
		}
		return finder;
	}
}
