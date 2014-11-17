package com.danwink.space_cruiser.server;

import java.io.FileNotFoundException;

import game_framework.ServerEntitySyncSystem;
import game_framework.SyncComponent;
import game_framework.SyncEngine;
import game_framework.SyncReference;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.FileHelper;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerGameHandler;
import com.danwink.space_cruiser.systems.MoveSystem;
import com.danwink.space_cruiser.systems.RandomMoverSystem;
import com.danwink.space_cruiser.systems.ServerShipSystem;
import com.phyloa.dlib.util.DFile;

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
		ship.add( new ShipComponent() );
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
	}

	public void update( SpaceCruiserServer s, NetworkServer n, float delta )
	{
		engine.update( delta );
	}
}
