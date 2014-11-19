package com.danwink.space_cruiser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.danwink.game_framework.network.NetworkMessager;
import com.danwink.game_framework.network.NetworkMessager.ClassRegister;
import com.danwink.space_cruiser.game_objects.EntityPacket;
import com.danwink.space_cruiser.game_objects.ShipSize;
import com.danwink.space_cruiser.game_objects.Tiles;
import com.phyloa.dlib.math.Point2i;
import com.danwink.space_cruiser.components.*;

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
			TileMap.class,
			TileMap.Tile.class,
			TileMap.Tile[].class,
			TileMap.Tile[][].class,
			TileMap.Wall.class,
			TileMap.Wall[].class,
			TileMap.Wall[][].class,
			
			Point2i.class,
			
			Vector2.class,
			
			EntityPacket.class,
			ArrayList.class,
			LinkedList.class,
			CopyOnWriteArrayList.class,
			
			MapComponent.class,
			ShipComponent.class,
			MoveComponent.class,
			MoveComponent.Direction.class,
			PlayerShipComponent.class,
			StarSystemComponent.class,
			StarMapChunkComponent.class,
			
			SyncComponent.class,
			SyncReference.class,
			
			Class.class,
			
			ShipSize.class
		});
		
		k.register( ServerMessages.class.getDeclaredClasses() );
		k.register( ClientMessages.class.getDeclaredClasses() );
		k.register( Tiles.class.getDeclaredClasses() );
	}	
}
