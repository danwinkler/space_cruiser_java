package com.danwink.space_cruiser.systems;

import java.util.HashMap;
import java.util.LinkedList;

import game_framework.BetterIteratingSystem;
import game_framework.ServerEntitySyncSystem;
import game_framework.SyncComponent;
import game_framework.SyncEngine;
import game_framework.TileMapPathFinder;

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
	HashMap<MapComponent, TileMapPathFinder> finders = new HashMap<MapComponent, TileMapPathFinder>();
	
	public RandomMoverSystem()
	{
		super( Family.all( MoveComponent.class ).get() );
	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		MoveComponent move = Mappers.move.get( entity );
		if( move.path == null )
		{
			MapComponent map = move.map.get();
			TileMapPathFinder finder = getFinder( map );
			LinkedList<Point2i> p = null;
			Point2i tileSpace = null;
			
			tileSpace = map.map.worldToTileSpace( move.pos.x, move.pos.y );
			if( map.map.blocked( tileSpace.x, tileSpace.y ) )
			{
				move.pos.x = DMath.randomi( 0, map.map.width ) * map.map.scale + map.map.scale*.5f;
				move.pos.y = DMath.randomi( 0, map.map.height ) * map.map.scale + map.map.scale*.5f;
				tileSpace = map.map.worldToTileSpace( move.pos.x, move.pos.y );
			}
			
			p = finder.findPath( tileSpace.x, tileSpace.y, DMath.randomi( 0, map.map.width ), DMath.randomi( 0, map.map.height ) );
			if( p == null || p.size() <= 1 ) return;
			
			Point2i a = p.getFirst();
			Point2i b = p.get( 1 );
			move.facing = Direction.fromCoords( b.x-a.x, b.y-a.y );
			
			move.path = p;
			SyncComponent sc = ServerEntitySyncSystem.syncMapper.get( entity );
			sc.sync = move;
		}
	}
	
	TileMapPathFinder getFinder( MapComponent map )
	{
		TileMapPathFinder finder = finders.get( map );
		if( finder == null )
		{
			finder = new TileMapPathFinder( map.map );
			finders.put( map, finder );
		}
		return finder;
	}
}
