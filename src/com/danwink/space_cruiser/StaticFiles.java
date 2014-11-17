package com.danwink.space_cruiser;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.SnapshotArray;
import com.danwink.game_framework.network.NetworkMessager;
import com.danwink.game_framework.network.NetworkMessager.ClassRegister;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.game_objects.EntityPacket;
import com.danwink.space_cruiser.game_objects.Ship;
import com.danwink.space_cruiser.game_objects.ShipSize;
import com.danwink.space_cruiser.game_objects.Tiles;
import com.danwink.space_cruiser.game_objects.Tiles.Floor;
import com.phyloa.dlib.math.Point2i;

import game_framework.SyncComponent;
import game_framework.SyncReference;
import game_framework.TileMap;

public class StaticFiles implements ClassRegister
{	
	private static StaticFiles singleton = new StaticFiles();
	public static StaticFiles get()
	{
		return singleton;
	}
	
	public static Texture floor = new Texture( "data/floor1.png" );

	private StaticFiles()
	{
		
	}
	
	public void register( NetworkMessager k )
	{
		k.register( new Class[] {
			Ship.class,
			
			TileMap.class,
			TileMap.Tile.class,
			TileMap.Tile[].class,
			TileMap.Tile[][].class,
			TileMap.Wall.class,
			TileMap.Wall[].class,
			TileMap.Wall[][].class,
			
			Point2i.class,
			
			EntityPacket.class,
			ArrayList.class,
			LinkedList.class,
			
			MapComponent.class,
			ShipComponent.class,
			MoveComponent.class,
			MoveComponent.Direction.class,
			
			SyncComponent.class,
			SyncReference.class,
			
			Class.class,
			
			ShipSize.class
		});
		
		k.register( ServerMessages.class.getDeclaredClasses() );
		k.register( ClientMessages.class.getDeclaredClasses() );
		k.register( Ship.class.getDeclaredClasses() );
		k.register( Tiles.class.getDeclaredClasses() );
	}	
}
