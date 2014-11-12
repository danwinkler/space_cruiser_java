package com.danwink.space_cruiser;

import com.badlogic.ashley.core.ComponentMapper;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.ShipComponent;

public class Mappers
{
	public static ComponentMapper<MapComponent> map = ComponentMapper.getFor( MapComponent.class );
	public static ComponentMapper<ShipComponent> ship = ComponentMapper.getFor( ShipComponent.class );
}
