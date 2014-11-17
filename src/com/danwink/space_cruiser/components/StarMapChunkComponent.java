package com.danwink.space_cruiser.components;

import game_framework.SyncReference;

import java.util.ArrayList;

import com.badlogic.ashley.core.Component;
import com.phyloa.dlib.math.Point2i;

public class StarMapChunkComponent extends Component
{
	public Point2i pos;
	public ArrayList<SyncReference<StarSystemComponent>> stars;
}
