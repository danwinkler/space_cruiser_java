package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.danwink.game_framework.network.MessagePacket;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.space_cruiser.ServerMessages;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.screens.ShipSubScreen;
import com.danwink.space_cruiser.game_objects.EntityPacket;

public class ClientShipSystem extends IteratingSystem
{
	public NetworkClient client;
	
	Engine engine;
	
	public ClientShipSystem( NetworkClient client )
	{
		super( Family.getFor( ShipComponent.class ) );
		
		this.client = client;
		
		client.group( ShipSubScreen.class, g -> {
			g.on( ServerMessages.Ship.SHIP, (MessagePacket<EntityPacket> m) -> {
				Entity ship = getOwnShip();
				for( Component c : m.getValue().components )
				{
					ship.add( c );
				}
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
		ImmutableArray<Entity> entities = engine.getEntitiesFor( Family.getFor( ShipComponent.class ) );
		if( entities.size() < 1 ) return null;
		return entities.get( 0 );
	}
}
