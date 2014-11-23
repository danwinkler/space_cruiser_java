package com.danwink.space_cruiser.systems.client;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danwink.space_cruiser.components.PlayerShipComponent;

public class ShipEditSystem extends IteratingSystem
{
	public ShipEditSystem()
	{
		super( Family.all( PlayerShipComponent.class ).get() );
	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		
	}
}
