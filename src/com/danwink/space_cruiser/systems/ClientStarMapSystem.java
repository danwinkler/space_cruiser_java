package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.danwink.game_framework.network.MessagePacket;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.ServerMessages;import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.screens.PlayScreen;

public class ClientStarMapSystem extends EntitySystem
{
	NetworkClient client;
	
	public ClientStarMapSystem( NetworkClient client )
	{
		this.client = client;
	}
	
	@Override
	public void addedToEngine( Engine engine )
	{
		client.group( PlayScreen.class, g -> {
			g.on( ServerMessages.StarMap.CHUNK, (MessagePacket<StarMapChunkComponent> m) -> {
				Entity starMapEntity = engine.getEntitiesFor( Family.all( StarMapComponent.class ).get() ).first();
				StarMapComponent starMap = Mappers.starmap.get( starMapEntity );
				
				StarMapChunkComponent comp = m.getValue();
				
				starMap.icm.getLayer( "main" ).put( comp.pos.x, comp.pos.y, comp );
			});
		});
	}
}
