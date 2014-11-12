package com.danwink.space_cruiser;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.danwink.space_cruiser.game_objects.Tiles.Floor;
import com.google.common.collect.HashBiMap;

import game_framework.TileMap;
import game_framework.TileMap.Tile;

public class FileHelper
{
	public static String tileMapToJSON( TileMap m )
	{
		Json json = new Json();
		
		json.setSerializer( TileMap.class, tileMapSerializer );
		
		return json.prettyPrint( m );
	}
	
	public static TileMap JSONtoTileMap( String json )
	{
		return null;
	}
	
	public static HashBiMap<Class, Integer> tileMapping;
	static 
	{
		tileMapping = HashBiMap.create();
		tileMapping.put( null, 0 );
		tileMapping.put( Floor.class, 1 );
	}
	
	public static Json.Serializer<TileMap> tileMapSerializer = new Json.Serializer<TileMap>() {
		public void write( Json json, TileMap map, Class knownType )
		{
			ArrayList<ArrayList<Integer>> intMap = new ArrayList<>();
			for( int y = 0; y < map.getHeightInTiles(); y++ )
			{
				ArrayList<Integer> row = new ArrayList<Integer>();
				for( int x = 0; x < map.getWidthInTiles(); x++ )
				{
					Tile t = map.getTile( x, y );
					row.add( tileMapping.get( t == null ? null : t.getClass() ) );
				}
				intMap.add( row );
			}
			
			json.writeArrayStart();
			for( ArrayList<Integer> ia : intMap )
			{
				json.writeValue( ia, ArrayList.class, Integer.class );
			}
			json.writeArrayEnd();
		}

		public TileMap read( Json json, JsonValue jsonData, Class type )
		{
			return null;
		}
	};
	
	//public static Json.Serializer<ArrayList>
	
	public static void main( String[] args )
	{
		TileMap m = new TileMap( 10, 8 );
		m.setTile( new Floor(), 2, 2 );
		System.out.println( tileMapToJSON( m ) );
	}
}
