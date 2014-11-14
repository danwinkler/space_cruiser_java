package com.danwink.space_cruiser.server;

import game_framework.ServerEntitySyncSystem;
import game_framework.SyncComponent;
import game_framework.SyncEngine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerGameHandler;
import com.danwink.space_cruiser.systems.MoveSystem;
import com.danwink.space_cruiser.systems.RandomMoverSystem;
import com.danwink.space_cruiser.systems.ServerShipSystem;

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
		
		Entity mover = new Entity();
		mover.add( new SyncComponent( true ) );
		mover.add( new MoveComponent() );
		
		engine.addEntity( mover );
	}

	public void update( SpaceCruiserServer s, NetworkServer n, float delta )
	{
		engine.update( delta );
	}
}
