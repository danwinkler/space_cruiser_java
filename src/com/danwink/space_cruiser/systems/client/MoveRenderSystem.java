package com.danwink.space_cruiser.systems.client;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MoveComponent;
import com.phyloa.dlib.math.Point2i;

public class MoveRenderSystem extends IteratingSystem
{	
	private SpriteBatch batch;
	private ShapeRenderer sr;
	
	public MoveRenderSystem( ShapeRenderer sr, SpriteBatch batch )
	{
		super( Family.all( MoveComponent.class ).get() );
		
		this.sr = sr;
		this.batch = batch;
	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		MoveComponent move = Mappers.move.get( entity );
		
		sr.begin( ShapeType.Line );
		sr.circle( move.pos.x, move.pos.y, 10 );
		
		if( move.path != null ) 
		{
			Point2i last = null;
			for( Point2i p : move.path )
			{
				if( last != null )
				{
					sr.line( last.x * 64 + 32, last.y * 64 + 32, p.x * 64 + 32, p.y * 64 + 32 );
				}
				last = p;
			}
		}
		sr.end();
	}
}
