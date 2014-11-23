package com.danwink.space_cruiser.components;

import com.badlogic.ashley.core.Component;
import com.danwink.game_framework.network.SyncReference;

public class ShipComponent extends Component
{
	public SyncReference<StarSystemComponent> starSystemLocation;
}
