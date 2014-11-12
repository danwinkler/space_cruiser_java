package com.danwink.space_cruiser.components;

import game_framework.TileMap;

import com.badlogic.ashley.core.Component;
import com.danwink.space_cruiser.game_objects.ShipSize;

public class MapComponent extends Component
{
	public TileMap map;
	public ShipSize size;
}
