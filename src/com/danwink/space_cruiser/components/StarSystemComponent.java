package com.danwink.space_cruiser.components;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.danwink.game_framework.network.SyncReference;

public class StarSystemComponent extends Component
{
	public Vector2 pos;
	public String name;
	public CopyOnWriteArrayList<SyncReference<StarSystemComponent>> connectedStars;
}
