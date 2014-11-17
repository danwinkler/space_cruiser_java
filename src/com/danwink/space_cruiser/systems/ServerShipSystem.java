package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.components.ShipComponent;

public class ServerShipSystem extends IteratingSystem
{
	NetworkServer server;
	
	Engine engine;
	
	public ServerShipSystem( NetworkServer server )
	{
		super( Family.all( ShipComponent.class ).get() );
		
		this.server = server;
	}
	
	@Override
    public void addedToEngine( Engine engine ) 
	{
		super.addedToEngine( engine );
        this.engine = engine;
    }

	protected void processEntity( Entity entity, float deltaTime )
	{
		
	}
}
