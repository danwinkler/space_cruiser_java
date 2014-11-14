package com.danwink.space_cruiser;

import game_framework.SyncComponent;

import com.badlogic.ashley.core.ComponentMapper;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.components.ShipComponent;

public class Mappers
{
	public static ComponentMapper<MapComponent> map = ComponentMapper.getFor( MapComponent.class );
	public static ComponentMapper<ShipComponent> ship = ComponentMapper.getFor( ShipComponent.class );
	public static ComponentMapper<MoveComponent> move = ComponentMapper.getFor( MoveComponent.class );
	public static ComponentMapper<SyncComponent> sync = ComponentMapper.getFor( SyncComponent.class );
}
