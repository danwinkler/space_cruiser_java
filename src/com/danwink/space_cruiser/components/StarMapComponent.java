package com.danwink.space_cruiser.components;

import game_framework.InfiniteChunkManager;

import com.badlogic.ashley.core.Component;

public class StarMapComponent extends Component
{
	public static final float mapViewRadius = 800;
	public static final float chunkSize = 400;
	public static final float connectDistance = 200;
	
	//This isn't a sync reference because we are never going to send the Star Map Component over the network in it's entirety
	public InfiniteChunkManager<StarMapChunkComponent> icm;
}
