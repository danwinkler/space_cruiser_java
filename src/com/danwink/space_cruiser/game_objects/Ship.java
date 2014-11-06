package com.danwink.space_cruiser.game_objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import game_framework.TileMap;

public class Ship 
{
	TileMap map;
	ShipSize size;
	
	public Ship()
	{
		
	}
	
	public Ship( ShipSize size )
	{
		this.size = size;
		map = new TileMap( size.width+2, size.height+2 ); //Extra size for exterior cosmetic tiles
		map.setScale( 64 );
	}
	
	public enum ShipSize
	{
		A( "A-Class", 12, 8 ),
		B( "B-Class", 15, 10 ),
		C( "C-Class", 20, 15 );
		
		String name;
		int width;
		int height;
		
		ShipSize( String name, int width, int height )
		{
			this.name = name;
			this.width = width;
			this.height = height;
		}
	}

	public void render( boolean grid, ShapeRenderer sr )
	{
		sr.begin( ShapeType.Line );
		map.render( (t,x,y,w,h) -> {
			if( grid ) {
				sr.rect( x, y, w, h );
			}
		}, (wall,x,y,w,h,d) -> {
			
		}, (east,north,west,south,x,y,w,h) -> {
			
		});
		sr.end();
	}
}
