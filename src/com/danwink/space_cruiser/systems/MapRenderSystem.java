package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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
		super( Family.getFor( MapComponent.class ) );
		
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
		batch.end();
		sr.end();
	}
	
	public void setMousePos( Vector3 mousePos )
	{
		this.mousePos = mousePos;
	}
}
