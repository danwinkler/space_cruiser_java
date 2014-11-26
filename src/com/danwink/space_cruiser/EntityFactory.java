package com.danwink.space_cruiser;

import game_framework.InfiniteChunkLayerGenerator;
import game_framework.InfiniteChunkManager;
import game_framework.InfiniteChunkManager.Layer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danwink.game_framework.network.SyncEngine;
import com.danwink.game_framework.network.SyncReference;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.components.StarSystemComponent;
import com.phyloa.dlib.math.Point2i;
import com.phyloa.dlib.util.DMath;

public class EntityFactory
{
	public static void newStarMap( SyncEngine engine )
	{
		Entity playerShipEntity = engine.getEntitiesFor( Family.all( PlayerShipComponent.class ).get() ).first();
		ShipComponent sc = Mappers.ship.get( playerShipEntity );
		
		Entity starMapEntity = new Entity();
		StarMapComponent starMap = new StarMapComponent();
		
		starMap.icm = new InfiniteChunkManager<StarMapChunkComponent>( StarMapChunkComponent.class, StarMapComponent.chunkSize, StarMapComponent.chunkSize );
		
		//TODO: SO MUCH ROOM FOR REFACTORING
		starMap.icm.newLayer( "main", new InfiniteChunkLayerGenerator<StarMapChunkComponent>() {
			public StarMapChunkComponent generate( int x, int y, String layerName )
			{
				Entity chunkEntity = engine.newSyncEntity();
				StarMapChunkComponent chunk = new StarMapChunkComponent();
				chunk.pos = new Point2i( x, y );
				
				chunk.stars = new ArrayList<SyncReference<StarSystemComponent>>();
				int starCount = DMath.randomi( 5, 9 );
				for( int i = 0; i < starCount; i++ )
				{
					StarSystemComponent star = new StarSystemComponent();
					star.pos = new Vector2( x*StarMapComponent.chunkSize + DMath.randomf( 0, StarMapComponent.chunkSize ), y*StarMapComponent.chunkSize + DMath.randomf( 0, StarMapComponent.chunkSize ) );
					star.name = StarNamer.genStarName();
					star.connectedStars = new CopyOnWriteArrayList<>();
					
					Entity starEntity = engine.newSyncEntity( e -> {
						e.add( star );	
					});
					
					if( sc.starSystemLocation == null ) sc.starSystemLocation = SyncReference.from( starEntity, star );
					
					Layer<StarMapChunkComponent> l = starMap.icm.getLayer( "main" );
					for( int xx = x-1; xx <= x+1; xx++ )
					{
						for( int yy = y-1; yy <= y+1; yy++ )
						{
							if( xx == x && yy == y ) continue;
							StarMapChunkComponent neighbor = l.getIfExists( xx, yy );
							if( neighbor == null ) continue;
							for( SyncReference<StarSystemComponent> otherStarMapSyncRef : neighbor.stars )
							{
								StarSystemComponent otherStar = otherStarMapSyncRef.get();
								if( star.pos.dst2( otherStar.pos ) < StarMapComponent.connectDistance*StarMapComponent.connectDistance )
								{
									star.connectedStars.add( SyncReference.from( engine.getBySyncId( otherStarMapSyncRef.getId() ), otherStar ) );
									otherStar.connectedStars.add( SyncReference.from( starEntity, star ) );
								}
							}
						}
					}
					
					chunk.stars.add( SyncReference.from( starEntity, star ) );
				}
				
				for( SyncReference<StarSystemComponent> star1Ref : chunk.stars )
				{
					StarSystemComponent star1 = star1Ref.get();
					for( SyncReference<StarSystemComponent> star2Ref : chunk.stars )
					{
						StarSystemComponent star2 = star2Ref.get();
						if( star1.pos.dst2( star2.pos ) < StarMapComponent.connectDistance*StarMapComponent.connectDistance )
						{
							star1.connectedStars.add( star2Ref );
							star2.connectedStars.add( star1Ref );
						}
					}
				}
				
				//If a star has no connections, connect to the closest other star in another sector
				for( SyncReference<StarSystemComponent> star1Ref : chunk.stars )
				{
					StarSystemComponent star1 = star1Ref.get();
					if( star1.connectedStars.size() == 0 )
					{
						SyncReference<StarSystemComponent> closest = null;
						float dist = 10000000000000f;
						Layer<StarMapChunkComponent> l = starMap.icm.getLayer( "main" );
						for( int xx = x-1; xx <= x+1; xx++ )
						{
							for( int yy = y-1; yy <= y+1; yy++ )
							{
								if( xx == x && yy == y ) continue;
								StarMapChunkComponent neighbor = l.getIfExists( xx, yy );
								if( neighbor == null ) continue;
								for( SyncReference<StarSystemComponent> otherStarMapSyncRef : neighbor.stars )
								{
									StarSystemComponent otherStar = otherStarMapSyncRef.get();
									float d = star1.pos.dst2( otherStar.pos );
									if( d < dist )
									{
										closest = otherStarMapSyncRef;
									}
								}
							}
						}
						if( closest != null ) 
						{
							star1.connectedStars.add( closest );
						}
					}
				}
				
				chunkEntity.add( chunk );
				
				return chunk;
			}
		});
		
		starMapEntity.add( starMap );
		
		engine.addEntity( starMapEntity );
		
		starMap.icm.getLayer( "main" ).get( 0, 0 );
	}
}
