package com.danwink.space_cruiser.components;

import game_framework.SyncReference;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class StarSystemComponent extends Component
{
	public Vector2 pos;
	public String name;
	public CopyOnWriteArrayList<SyncReference<StarSystemComponent>> connectedStars;
}
