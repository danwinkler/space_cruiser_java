package com.danwink.space_cruiser.server;

import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.game_objects.Ship;
import com.danwink.space_cruiser.game_objects.Ship.ShipSize;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerState;

import space_cruiser_java.ClientMessages;
import space_cruiser_java.ServerMessages;
import space_cruiser_java.ServerMessages.ShipBuild;

public class ShipBuildServerState implements ServerState
{
	Ship ship = new Ship( Ship.ShipSize.A );
	
	public void activate( SpaceCruiserServer s, NetworkServer n ) 
	{
		n.on( ShipBuildServerState.class, ClientMessages.ShipBuild.JOIN, m -> {
			n.sendTCP( m.getSender(), ServerMessages.ShipBuild.SHIP, ship );
		});
	}
	
	public void update( SpaceCruiserServer s, NetworkServer n ) 
	{
		
	}
}
