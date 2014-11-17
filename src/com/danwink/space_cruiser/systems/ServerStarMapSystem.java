package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.ServerMessages;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.server.ShipServerHandler;

public class ServerStarMapSystem extends EntitySystem
{
	Engine engine;
	
	public ServerStarMapSystem( NetworkServer ns )
	{
		ns.group( ShipServerHandler.class, g -> {
			g.on( ServerMessages.StarMap.VIEW, m -> {
				Entity e = engine.getEntitiesFor( Family.all( PlayerShipComponent.class ).get() ).first();
				if( e != null )
				{
					ShipComponent sc = Mappers.ship.get( e );
				}
			});
		});
	}
	
	@Override
	public void addedToEngine( Engine engine )
	{
		this.engine = engine;
	}	
}
