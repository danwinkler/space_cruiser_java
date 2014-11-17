package com.danwink.space_cruiser.systems;

import game_framework.InfiniteChunkManager.Layer;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.ServerMessages;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.server.ShipServerHandler;

public class ServerStarMapSystem extends EntitySystem
{
	Engine engine;
	
	NetworkServer server;
	
	public ServerStarMapSystem( NetworkServer server )
	{
		this.server = server;
	}
	
	@Override
	public void addedToEngine( Engine engine )
	{
		this.engine = engine;
		
		server.group( ShipServerHandler.class, g -> {
			g.on( ClientMessages.StarMap.VIEW, m -> {
				Entity playerShipEntity = engine.getEntitiesFor( Family.all( PlayerShipComponent.class ).get() ).first();
				Entity starMapEntity = engine.getEntitiesFor( Family.all( StarMapComponent.class ).get() ).first();
				
				StarMapComponent starmap = Mappers.starmap.get( starMapEntity );
				
				ShipComponent ship = Mappers.ship.get( playerShipEntity );
				
				Vector2 pos = ship.starSystemLocation.get().pos;
				
				Layer<StarMapChunkComponent> l = starmap.icm.getLayer( "main" );
				l.resetIterator( pos.x - StarMapComponent.mapViewRadius, pos.y - StarMapComponent.mapViewRadius, StarMapComponent.mapViewRadius*2, StarMapComponent.mapViewRadius*2 );
				
				while( l.hasNext() )
				{
					StarMapChunkComponent chunk = l.next();
					server.sendTCP( m.getSender(), ServerMessages.StarMap.CHUNK, chunk );
				}
			});
		});
	}	
}
