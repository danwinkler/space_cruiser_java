package space_cruiser_java;

import com.danwink.game_framework.network.NetworkServer;

import space_cruiser_java.SpaceCruiserServer.ServerState;

public class ShipBuildServerState implements ServerState
{
	Ship s = new Ship( Ship.ShipSize.A );
	
	public void activate( SpaceCruiserServer s, NetworkServer n ) 
	{
		
	}
}
