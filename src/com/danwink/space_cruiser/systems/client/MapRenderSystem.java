package com.danwink.space_cruiser.systems.client;

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
		
		batch.begin();
		mc.map.renderTiles( (t,x,y,w,h) -> {
			if( t != null )
			{
				batch.draw( StaticFiles.floor, x, y );
			}
		});
		batch.end();
		
		sr.begin( ShapeType.Filled );
		mc.map.renderWalls( (wall,x,y,w,h,d) -> {
			sr.setColor( 1, 0, 1, 1 );
			sr.rect( x, y, w, h );
		});
		mc.map.renderWallJoints( (east,north,west,south,x,y,w,h) -> {
			sr.rect( x, y, w, h );
		});
		
		sr.end();
	}
	
	public void setMousePos( Vector3 mousePos )
	{
		this.mousePos = mousePos;
	}
}
