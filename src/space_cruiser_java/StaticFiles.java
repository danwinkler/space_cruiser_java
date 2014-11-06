package space_cruiser_java;

import com.danwink.space_cruiser.game_objects.Ship;

import game_framework.TileMap;

public class StaticFiles 
{
	public static Class[] registerClasses = {
		Ship.class,
		Ship.ShipSize.class,
		
		TileMap.class,
		TileMap.Tile.class,
		TileMap.Tile[].class,
		TileMap.Tile[][].class,
		TileMap.Wall.class,
		TileMap.Wall[].class,
		TileMap.Wall[][].class,
		
		ServerMessages.ShipBuild.class,
		ClientMessages.ShipBuild.class
	};
}
