package com.danwink.space_cruiser.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import game_framework.InfiniteChunkLayerGenerator;
import game_framework.InfiniteChunkManager;
import game_framework.InfiniteChunkManager.Layer;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.game_framework.network.ServerEntitySyncSystem;
import com.danwink.game_framework.network.SyncComponent;
import com.danwink.game_framework.network.SyncEngine;
import com.danwink.game_framework.network.SyncReference;
import com.danwink.space_cruiser.EntityFactory;
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
		sync.setSync( StarSystemComponent.class, Family.all( StarSystemComponent.class ).get() );
		sync.setSync( StarMapChunkComponent.class, Family.all( StarMapChunkComponent.class ).get() );
		
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
		Entity ship = engine.newSyncEntity();
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
		
		for( int i = 0; i < 1; i++ )
		{
			Entity mover = engine.newSyncEntity();
			MoveComponent move = new MoveComponent();
			move.map = SyncReference.from( ship, mc );
			mover.add( move );
		}
		
		EntityFactory.newStarMap( engine );
	}
}
