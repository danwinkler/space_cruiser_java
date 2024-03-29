package com.danwink.space_cruiser.systems.client;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danwink.space_cruiser.components.ShipComponent;

public class ClientShipSystem extends IteratingSystem
{
	Engine engine;
	
	public ClientShipSystem()
	{
		super( Family.all( ShipComponent.class ).get() );
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
