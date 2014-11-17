package com.danwink.space_cruiser.components;

import game_framework.SyncReference;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class StarSystemComponent extends Component
{
	public Vector2 pos;
	public String name;
	public ArrayList<SyncReference<StarSystemComponent>> connectedStars;
}
