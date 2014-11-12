package com.danwink.space_cruiser.server;

import com.danwink.game_framework.network.MessagePacket;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.ServerMessages;
import com.danwink.space_cruiser.ClientMessages.ShipBuildChangeTilePacket;
import com.danwink.space_cruiser.game_objects.Ship;
import com.danwink.space_cruiser.game_objects.ShipSize;
import com.danwink.space_cruiser.server.SpaceCruiserServer.ServerGameHandler;

public class ShipBuildServerHandler implements ServerGameHandler
{
	Ship editorShip = new Ship( ShipSize.A );
	
	public void activate( SpaceCruiserServer s, NetworkServer n ) 
	{
		n.group( ShipBuildServerHandler.class, h -> {
			h.on( ClientMessages.ShipBuild.JOIN, m -> {
				n.sendTCP( m.getSender(), ServerMessages.ShipBuild.SHIP, editorShip );
			});
			
			h.on( ClientMessages.ShipBuild.CHANGETILE, (MessagePacket<ClientMessages.ShipBuildChangeTilePacket> m) -> {
				ShipBuildChangeTilePacket v = m.getValue();
				editorShip.setTile( v.t, v.p.x, v.p.y );
				n.sendAllTCP( ServerMessages.ShipBuild.CHANGETILE, new ServerMessages.ShipBuildChangeTilePacket( v.t, v.p ) );
			});
		});
	}
	
	public void update( SpaceCruiserServer s, NetworkServer n, float delta ) 
	{
		
	}
}
