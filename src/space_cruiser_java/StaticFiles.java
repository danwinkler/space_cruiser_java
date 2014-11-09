package space_cruiser_java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.danwink.game_framework.network.NetworkMessager;
import com.danwink.game_framework.network.NetworkMessager.ClassRegister;
import com.danwink.space_cruiser.game_objects.Ship;
import com.esotericsoftware.kryo.Kryo;
import com.phyloa.dlib.math.Point2i;

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
		});
		
		k.register( ServerMessages.class.getDeclaredClasses() );
		k.register( ClientMessages.class.getDeclaredClasses() );
		k.register( Ship.class.getDeclaredClasses() );
	}	
}
