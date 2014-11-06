package space_cruiser_java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	
	public static Texture floor = new Texture( "data/floor1.png" );	
}
