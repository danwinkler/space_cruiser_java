package com.danwink.space_cruiser.game_objects;

import space_cruiser_java.StaticFiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

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

	public void renderGrid( ShapeRenderer sr, SpriteBatch batch, Vector3 mousePos )
	{
		sr.begin( ShapeType.Line );
		batch.begin();
		map.render( (t,x,y,w,h) -> {
			batch.draw( StaticFiles.floor, x, y );
			if( mousePos.x > x && mousePos.x < x+w && mousePos.y > y && mousePos.y < y+h )
			{
				sr.setColor( 1, 0, 0, 1 );
			}
			else
			{
				sr.setColor( 1, 1, 1, 1 );
			}
			sr.rect( x, y, w, h );
		}, (wall,x,y,w,h,d) -> {
			
		}, (east,north,west,south,x,y,w,h) -> {
			
		});
		batch.end();
		sr.end();
	}
}
