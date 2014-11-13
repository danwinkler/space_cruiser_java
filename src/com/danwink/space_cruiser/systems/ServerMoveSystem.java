package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MoveComponent;

public class ServerMoveSystem extends IteratingSystem
{
	NetworkServer server;
	
	public ServerMoveSystem( NetworkServer server )
	{
		super( Family.getFor( MoveComponent.class ) );
		this.server = server;
	}

	protected void processEntity( Entity entity, float delta )
	{
		MoveComponent mc = Mappers.move.get( entity );
		
	}
}
