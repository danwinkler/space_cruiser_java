package com.danwink.space_cruiser.game_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.danwink.space_cruiser.StaticFiles;
import com.phyloa.dlib.math.Point2i;

import game_framework.TileMap;
import game_framework.TileMap.Tile;

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
	
	public void renderGrid( ShapeRenderer sr, SpriteBatch batch, Vector3 mousePos )
	{
		sr.begin( ShapeType.Line );
		batch.begin();
		map.render( (t,x,y,w,h) -> {
			if( t != null )
			{
				batch.draw( StaticFiles.floor, x, y );
			}
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

	public Point2i worldToTileSpace( Vector3 mousePos ) 
	{
		return map.worldToTileSpace( mousePos.x, mousePos.y );
	}
	
	public static class Floor implements Tile
	{
		public boolean isPassable() 
		{
			return true;
		}
	}

	public Tile getTile( int x, int y )
	{
		return map.getTile( x, y );
	}
	
	public Tile getTile( Point2i pos )
	{
		return map.getTile( pos );
	}

	public void setTile( Tile t, int x, int y )
	{
		map.setTile( t, x, y );
	}
}
