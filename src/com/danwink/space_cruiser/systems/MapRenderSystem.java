package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.StaticFiles;
import com.danwink.space_cruiser.components.MapComponent;

public class MapRenderSystem extends IteratingSystem
{	
	private SpriteBatch batch;
	private ShapeRenderer sr;
	
	private Vector3 mousePos;

	public MapRenderSystem( ShapeRenderer sr, SpriteBatch batch )
	{
		super( Family.all( MapComponent.class ).get() );
		
		this.sr = sr;
		this.batch = batch;
	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		MapComponent mc = Mappers.map.get( entity );
		
		sr.begin( ShapeType.Line );
		batch.begin();
		mc.map.render( (t,x,y,w,h) -> {
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
			sr.setColor( 1, 0, 1, 1 );
			sr.rect( x, y, w, h );
		}, (east,north,west,south,x,y,w,h) -> {
			sr.rect( x, y, w, h );
		});
		
		/*
		for( int x = 0; x < mc.map.width; x++ )
		{
			for( int y = 0; y < mc.map.height; y++ )
			{
				for( int dx = -1; dx <= 1; dx++ )
				{
					for( int dy = -1; dy <= 1; dy++ )
					{
						int nx = x+dx;
						int ny = y+dy;
						if( dx == 0 && dy == 0 ) continue;
						if( dx != 0 && dy != 0 ) continue;
						if( nx < 0 || nx >= mc.map.width || ny < 0 || ny >= mc.map.height ) continue;
						
						if( !mc.map.blocked( x, y, nx, ny ) )
						{
							sr.line( x*64+32, y*64+32, nx*64+32, ny*64+32 );
							sr.triangle( nx*64+32, ny*64+32, x*64+32+10, y*64+32+10, x*64+32-10, y*64+32-10 );
						}
					}
				}
			}
		}
		*/
		
		batch.end();
		sr.end();
	}
	
	public void setMousePos( Vector3 mousePos )
	{
		this.mousePos = mousePos;
	}
}
