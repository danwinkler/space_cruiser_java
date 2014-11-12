package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.server.ShipServerHandler;
import com.danwink.space_cruiser.ServerMessages;
import com.danwink.space_cruiser.game_objects.EntityPacket;

public class ServerShipSystem extends IteratingSystem
{
	NetworkServer server;
	
	Engine engine;
	
	public ServerShipSystem( NetworkServer server )
	{
		super( Family.getFor( ShipComponent.class ) );
		
		this.server = server;
		
		server.group( ShipServerHandler.class, g -> {
			g.on( ClientMessages.Ship.JOIN, m -> {
				Entity ship = getOwnShip();
				server.sendTCP( m.getSender(), ServerMessages.Ship.SHIP, new EntityPacket( ship.getId(), ship.getComponents() ) );
			});
		});
	}
	
	@Override
    public void addedToEngine(Engine engine) 
	{
		super.addedToEngine( engine );
        this.engine = engine;
    }

	protected void processEntity( Entity entity, float deltaTime )
	{
	
	}
	
	public Entity getOwnShip()
	{
		return engine.getEntitiesFor( Family.getFor( ShipComponent.class ) ).get( 0 );
	}
}
