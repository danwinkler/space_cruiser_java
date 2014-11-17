package com.danwink.space_cruiser.components;

import game_framework.SyncReference;

import com.badlogic.ashley.core.Component;

public class ShipComponent extends Component
{
	public SyncReference<StarSystemComponent> starSystemLocation;
}
