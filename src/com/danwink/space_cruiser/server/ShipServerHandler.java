package com.danwink.space_cruiser.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import game_framework.InfiniteChunkLayerGenerator;
import game_framework.InfiniteChunkManager;
import game_framework.InfiniteChunkManager.Layer;
import game_framework.ServerEntitySyncSystem;
import game_framework.SyncComponent;
import game_framework.SyncEngine;
import game_framework.SyncReference;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.FileHelper;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.StarNamer;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.components.StarSystemComponent;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerGameHandler;
import com.danwink.space_cruiser.systems.MoveSystem;
import com.danwink.space_cruiser.systems.server.RandomMoverSystem;
import com.danwink.space_cruiser.systems.server.ServerShipSystem;
import com.danwink.space_cruiser.systems.server.ServerStarMapSystem;
import com.phyloa.dlib.math.Point2i;
import com.phyloa.dlib.util.DFile;
import com.phyloa.dlib.util.DMath;

public class ShipServerHandler implements ServerGameHandler
{
	public SyncEngine engine;
	
	public void activate( SpaceCruiserServer s, NetworkServer server )
	{
		engine = new SyncEngine();
		
		ServerEntitySyncSystem sync = new ServerEntitySyncSystem( .5f, server, ShipServerHandler.class );
		sync.setEntityPresenceSync( true );
		
		sync.setSync( MoveComponent.class, Family.all( MoveComponent.class ).get() );
		
		engine.addSystem( sync );
		
		engine.addSystem( new ServerShipSystem( server ) );
		
		engine.addSystem( new MoveSystem() );
		engine.addSystem( new RandomMoverSystem() );
		
		engine.addSystem( new ServerStarMapSystem( server ) );
		
		initializeGameState();
	}

	public void update( SpaceCruiserServer s, NetworkServer n, float delta )
	{
		engine.update( delta );
	}
	
	public void initializeGameState()
	{
		Entity ship = new Entity();
        MapComponent mc = new MapComponent();
        try
		{
			mc.map = FileHelper.JSONtoTileMap( DFile.loadText( "testMap.json" ) );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		
        mc.map.setScale( 64 );
		
		ship.add( mc );
		ShipComponent sc;
		ship.add( sc = new ShipComponent() );
		ship.add( new PlayerShipComponent() );
		ship.add( new SyncComponent( true ) );
		
		engine.addEntity( ship );
		
		for( int i = 0; i < 1; i++ )
		{
			Entity mover = new Entity();
			mover.add( new SyncComponent( true ) );
			MoveComponent move = new MoveComponent();
			move.map = SyncReference.from( ship, mc );
			mover.add( move );
			engine.addEntity( mover );
		}
		
		Entity starMapEntity = new Entity();
		StarMapComponent starMap = new StarMapComponent();
		
		starMap.icm = new InfiniteChunkManager<StarMapChunkComponent>( StarMapChunkComponent.class, StarMapComponent.chunkSize, StarMapComponent.chunkSize );
		
		//TODO: refactor out into it's own class
		//TODO: SO MUCH ROOM FOR REFACTORING
		//TODO: seems like all of the entity creation craziness in here could be abstracted
		//		Possibly like a public Entity createSyncEntity( Component c ) in SyncEngine that 
		//		Creates a new sync entity with the specified component and a SyncComponent
		starMap.icm.newLayer( "main", new InfiniteChunkLayerGenerator<StarMapChunkComponent>() {
			public StarMapChunkComponent generate( int x, int y, String layerName )
			{
				Entity chunkEntity = new Entity();
				chunkEntity.add( new SyncComponent( true ) );
				StarMapChunkComponent chunk = new StarMapChunkComponent();
				chunk.pos = new Point2i( x, y );
				
				chunk.stars = new ArrayList<SyncReference<StarSystemComponent>>();
				int starCount = DMath.randomi( 3, 9 );
				for( int i = 0; i < starCount; i++ )
				{
					StarSystemComponent star = new StarSystemComponent();
					star.pos = new Vector2( x*StarMapComponent.chunkSize + DMath.randomf( 0, StarMapComponent.chunkSize ), y*StarMapComponent.chunkSize + DMath.randomf( 0, StarMapComponent.chunkSize ) );
					star.name = StarNamer.genStarName();
					star.connectedStars = new ArrayList<>();
					
					Entity starEntity = new Entity();
					starEntity.add( new SyncComponent( true ) );
					starEntity.add( star );
					engine.addEntity( starEntity );
					
					if( sc.starSystemLocation == null ) sc.starSystemLocation = SyncReference.from( starEntity, star );
					
					Layer<StarMapChunkComponent> l = starMap.icm.getLayer( "main" );
					for( int xx = x-1; xx <= x+1; xx++ )
					{
						for( int yy = y-1; yy <= y+1; yy++ )
						{
							if( xx == 0 && yy == 0 ) continue;
							StarMapChunkComponent neighbor = l.getIfExists( xx, yy );
							if( neighbor == null ) continue;
							for( SyncReference<StarSystemComponent> otherStarMapSyncRef : neighbor.stars )
							{
								StarSystemComponent otherStar = otherStarMapSyncRef.get();
								Vector2 distance = otherStar.pos.cpy();
								distance.sub( star.pos );
								if( distance.len2() < StarMapComponent.connectDistance*StarMapComponent.connectDistance )
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
						Vector2 distance = star1.pos.cpy();
						distance.sub( star2.pos );
						if( distance.len2() < StarMapComponent.connectDistance*StarMapComponent.connectDistance )
						{
							star1.connectedStars.add( star2Ref );
							star2.connectedStars.add( star1Ref );
						}
					}
				}
				
				chunkEntity.add( chunk );
				engine.addEntity( chunkEntity );
				
				return chunk;
			}
		});
		
		starMapEntity.add( starMap );
		
		engine.addEntity( starMapEntity );
		
		starMap.icm.getLayer( "main" ).get( 0, 0 );
	}
}
