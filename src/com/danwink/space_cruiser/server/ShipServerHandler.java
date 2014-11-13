package com.danwink.space_cruiser.server;

import game_framework.ServerEntitySyncSystem;

import com.badlogic.ashley.core.Engine;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerGameHandler;
import com.danwink.space_cruiser.systems.ServerShipSystem;

public class ShipServerHandler implements ServerGameHandler
{
	Engine engine;
	
	public void activate( SpaceCruiserServer s, NetworkServer server )
	{
		engine = new Engine();
		ServerEntitySyncSystem sync = new ServerEntitySyncSystem( .1f, server, ShipServerHandler.class );
		sync.setEntityPresenceSync( true );
		
		//sync.setSync( , family );
		
		engine.addSystem( sync );
		
		engine.addSystem( new ServerShipSystem( server ) );
	}

	public void update( SpaceCruiserServer s, NetworkServer n, float delta )
	{
		engine.update( delta );
	}
}
