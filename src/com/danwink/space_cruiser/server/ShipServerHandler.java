package com.danwink.space_cruiser.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerGameHandler;
import com.danwink.space_cruiser.systems.ServerShipSystem;

public class ShipServerHandler implements ServerGameHandler
{
	Engine engine;
	
	public void activate( SpaceCruiserServer s, NetworkServer server )
	{
		engine = new Engine();
		
		Entity ship = new Entity();
		ship.add( new MapComponent() );
		ship.add( new ShipComponent() );
		
		engine.addEntity( ship );
		
		engine.addSystem( new ServerShipSystem( server ) );
		
		/*
		server.group( ShipServerHandler.class, g -> {
			g.on( ClientMessages.Ship.JOIN, m -> {
				server.sendTCP( m.getSender(), ServerMessages.Ship.SHIP, getOwnShip().getComponents() );
			});
		});
		*/
	}

	public void update( SpaceCruiserServer s, NetworkServer n, float delta )
	{
		engine.update( delta );
	}
}
