package com.danwink.space_cruiser.systems.client;

import game_framework.InfiniteChunkManager.Layer;
import game_framework.SyncReference;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.components.StarSystemComponent;

public class StarMapRenderSystem extends IteratingSystem
{
	private Engine engine;

	ShapeRenderer sr;
	
	public StarMapRenderSystem()
	{
		super( Family.all( StarMapComponent.class ).get() );
	}

	@Override
	public void addedToEngine( Engine engine )
	{
		super.addedToEngine( engine );
		this.engine = engine;
		
		sr = new ShapeRenderer();
	}
	
	protected void processEntity( Entity entity, float deltaTime )
	{
		Entity shipEntity = engine.getEntitiesFor( Family.all( PlayerShipComponent.class ).get() ).first();
		ShipComponent playerShip = Mappers.ship.get( shipEntity );
		StarSystemComponent ssc = playerShip.starSystemLocation.get();
		
		StarMapComponent starMap = Mappers.starmap.get( entity );
		Layer<StarMapChunkComponent> l = starMap.icm.getLayer( "main" );
		l.resetIterator( ssc.pos.x - StarMapComponent.mapViewRadius, ssc.pos.y - StarMapComponent.mapViewRadius, StarMapComponent.mapViewRadius*2, StarMapComponent.mapViewRadius*2 );
		sr.begin( ShapeType.Filled );
		while( l.hasNext() )
		{
			StarMapChunkComponent chunk = l.next();
			if( chunk == null ) 
			{
				System.out.println( "null chunk" );
				continue;	
			}
			for( SyncReference<StarSystemComponent> starRef : chunk.stars )
			{
				StarSystemComponent star = starRef.get();
				sr.circle( star.pos.x, star.pos.y, 10 );
			}
		}
		sr.end();
	}
}
