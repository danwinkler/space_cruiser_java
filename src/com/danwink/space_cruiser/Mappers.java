package com.danwink.space_cruiser;

import com.badlogic.ashley.core.ComponentMapper;
import com.danwink.game_framework.network.SyncComponent;
import com.danwink.space_cruiser.components.*;

public class Mappers
{
	public static ComponentMapper<MapComponent> map = ComponentMapper.getFor( MapComponent.class );
	public static ComponentMapper<ShipComponent> ship = ComponentMapper.getFor( ShipComponent.class );
	public static ComponentMapper<MoveComponent> move = ComponentMapper.getFor( MoveComponent.class );
	public static ComponentMapper<SyncComponent> sync = ComponentMapper.getFor( SyncComponent.class );
	public static ComponentMapper<StarMapChunkComponent> starmapchunk = ComponentMapper.getFor( StarMapChunkComponent.class );
	public static ComponentMapper<StarMapComponent> starmap = ComponentMapper.getFor( StarMapComponent.class );
	public static ComponentMapper<PlayerShipComponent> playership = ComponentMapper.getFor( PlayerShipComponent.class );
}
