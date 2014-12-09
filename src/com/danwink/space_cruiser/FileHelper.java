package com.danwink.space_cruiser;

import java.io.FileNotFoundException;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.danwink.space_cruiser.game_objects.Tiles.NormalWall;
import com.danwink.space_cruiser.game_objects.Tiles.SCTile;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.phyloa.dlib.util.DFile;

import game_framework.TileMap;
import game_framework.TileMap.Tile;
import game_framework.TileMap.Wall;

public class FileHelper
{
	public static String tileMapToJSON( TileMap m )
	{
		Json json = new Json();
		
		json.setSerializer( TileMap.class, tileMapSerializer );
		
		return json.prettyPrint( m );
	}
	
	public static TileMap JSONtoTileMap( String jsonText )
	{
		Json json = new Json();
		
		json.setSerializer( TileMap.class, tileMapSerializer );
		
		return json.fromJson( TileMap.class, jsonText );
	}
	
	public static HashBiMap<Class<? extends Tile>, Integer> tileClassToInt;
	public static BiMap<Integer, Class<? extends Tile>> intToTileClass;
	
	public static HashBiMap<Class<? extends Wall>, Integer> wallClassToInt;
	public static BiMap<Integer, Class<? extends Wall>> intToWallClass;
	static 
	{
		tileClassToInt = HashBiMap.create();
		tileClassToInt.put( null, 0 );
		tileClassToInt.put( SCTile.class, 1 );
		
		intToTileClass = tileClassToInt.inverse();
		
		wallClassToInt = HashBiMap.create();
		wallClassToInt.put( null, 0 );
		wallClassToInt.put( NormalWall.class, 1 );
		
		intToWallClass = wallClassToInt.inverse();
	}
	
	public interface Array2DIterateHandler<T>
	{
		public void compute( T t, int x, int y );
	}
	
	public <T> void array2DIterate( T[][] arr, Array2DIterateHandler<T> h )
	{
		for( int x = 0; x < arr.length; x++ )
		{
			for( int y = 0; y < arr[x].length; y++ )
			{
				h.compute( arr[x][y], x, y );
			}
		}
	}
	
	public static Json.Serializer<TileMap> tileMapSerializer = new Json.Serializer<TileMap>() {
		public void write( Json json, TileMap map, Class knownType )
		{
			int[][] intMap = new int[map.height][map.width];
			for( int y = 0; y < map.getHeightInTiles(); y++ )
			{
				int[] row = new int[map.width];
				for( int x = 0; x < map.getWidthInTiles(); x++ )
				{
					Tile t = map.getTile( x, y );
					row[x] = tileClassToInt.get( t == null ? null : t.getClass() );
				}
				intMap[y] = row;
			}
			
			int[][] vertWalls = new int[map.height][map.width+1];
			for( int y = 0; y < map.height; y++ )
			{
				int[] row = new int[map.width+1];
				for( int x = 0; x < map.width+1; x++ )
				{
					Wall w = map.getVertWall( x, y );
					row[x] = wallClassToInt.get( w == null ? null : w.getClass() );
				}
				vertWalls[y] = row;
			}
			
			int[][] horiWalls = new int[map.height+1][map.width];
			for( int y = 0; y < map.height+1; y++ )
			{
				int[] row = new int[map.width];
				for( int x = 0; x < map.width; x++ )
				{
					Wall w = map.getHoriWall( x, y );
					row[x] = wallClassToInt.get( w == null ? null : w.getClass() );
				}
				horiWalls[y] = row;
			}
			
			json.writeObjectStart();
			json.writeValue( "version", 1 );
			json.writeValue( "width", map.width );
			json.writeValue( "height", map.height );
			json.writeValue( "map", intMap );
			json.writeValue( "hori", horiWalls );
			json.writeValue( "vert", vertWalls );
			json.writeObjectEnd();
		}
		
		public TileMap read( Json json, JsonValue jsonData, Class type )
		{
			int version = jsonData.getInt( "version" );
			switch( version )
			{
				case 1:
					TileMap m = new TileMap( jsonData.getInt( "width" ), jsonData.getInt( "height" ) );
					
					JsonValue map = jsonData.get( "map" );
					for( int y = 0; y < map.size; y++ )
					{
						int[] row = map.get( y ).asIntArray();
						for( int x = 0; x < row.length; x++ )
						{
							try
							{
								Class<? extends Tile> c = intToTileClass.get( row[x] );
								if( c != null ) 
								{
									m.setTile( (Tile)c.newInstance(), x, y );
								}
							}
							catch( InstantiationException | IllegalAccessException e ) { e.printStackTrace(); }
						}
					}
					
					JsonValue horiWalls = jsonData.get( "hori" );
					for( int y = 0; y < horiWalls.size; y++ )
					{
						int[] row = horiWalls.get( y ).asIntArray();
						for( int x = 0; x < row.length; x++ )
						{
							try
							{
								Class<? extends Wall> c = intToWallClass.get( row[x] );
								if( c != null ) m.setHoriWall( (Wall)c.newInstance(), x, y );
							}
							catch( InstantiationException | IllegalAccessException e ) { e.printStackTrace(); }
						}
					}
					
					JsonValue vertWalls = jsonData.get( "vert" );
					for( int y = 0; y < vertWalls.size; y++ )
					{
						int[] row = vertWalls.get( y ).asIntArray();
						for( int x = 0; x < row.length; x++ )
						{
							try
							{
								Class<? extends Wall> c = intToWallClass.get( row[x] );
								if( c != null ) m.setVertWall( (Wall)c.newInstance(), x, y );
							}
							catch( InstantiationException | IllegalAccessException e ) { e.printStackTrace(); }
						}
					}
					return m;
			}
			return null;
		}
	};
	
	
	public static void main( String[] args ) throws FileNotFoundException
	{
		/*
		TileMap m = new TileMap( 10, 8 );
		m.setTile( new Floor(), 0, 2 );
		DFile.saveText( "testMap.json", tileMapToJSON( m ) );
		*/
		TileMap m = JSONtoTileMap( DFile.loadText( "testMap.json" )  );
		m.toJson( t -> tileClassToInt.get( t == null ? null : t.getClass() ), w -> wallClassToInt.get( w == null ? null : w.getClass() ) );
	}
}
