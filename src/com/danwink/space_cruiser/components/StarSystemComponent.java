package com.danwink.space_cruiser.components;

import game_framework.SyncReference;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;

public class StarSystemComponent extends Component
{
	float x, y;
	public String name;
	
	ArrayList<SyncReference<StarSystemComponent>> connectedStars;
}
