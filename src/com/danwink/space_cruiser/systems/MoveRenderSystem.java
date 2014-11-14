package com.danwink.space_cruiser.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MoveComponent;

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
		sr.circle( move.x, move.y, 10 );
		sr.end();
	}
}
