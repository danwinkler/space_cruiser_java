package com.danwink.space_cruiser.systems.client;

import game_framework.InfiniteChunkManager.Layer;
import game_framework.SyncReference;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.components.StarSystemComponent;
import com.danwink.space_cruiser.screens.StarMapOverlay;

public class StarMapRenderSystem extends IteratingSystem
{
	private Engine engine;

	ShapeRenderer sr;
	SpriteBatch batch;
	BitmapFont font;

	private StarMapOverlay overlay;
	
	public StarMapRenderSystem( StarMapOverlay overlay, ShapeRenderer sr, SpriteBatch batch )
	{
		super( Family.all( StarMapComponent.class ).get() );
		
		this.overlay = overlay;
		
		this.sr = sr;
		this.batch = batch;
	}

	@Override
	public void addedToEngine( Engine engine )
	{
		super.addedToEngine( engine );
		this.engine = engine;
		
		font = new BitmapFont( true );
	}
	
	protected void processEntity( Entity entity, float deltaTime )
	{
		Entity shipEntity = engine.getEntitiesFor( Family.all( PlayerShipComponent.class ).get() ).first();
		ShipComponent playerShip = Mappers.ship.get( shipEntity );
		StarSystemComponent ssc = playerShip.starSystemLocation.get();
				
		StarMapComponent starMap = Mappers.starmap.get( entity );
		Layer<StarMapChunkComponent> l = starMap.icm.getLayer( "main" );
		
		float vx = ssc.pos.x - StarMapComponent.mapViewRadius;
		float vy = ssc.pos.y - StarMapComponent.mapViewRadius;
		float vw = StarMapComponent.mapViewRadius*2;
		float vh = StarMapComponent.mapViewRadius*2;
		
		float xOffset = -ssc.pos.x + overlay.width*.5f;
		float yOffset = -ssc.pos.y + overlay.height*.5f;
		
		float xMouse = Gdx.input.getX() - xOffset - overlay.margin;
		float yMouse = Gdx.input.getY() - yOffset - overlay.margin;
		
		Matrix4 oldTransform = sr.getTransformMatrix().cpy();
		
		sr.translate( xOffset, yOffset, 0 );
		batch.setTransformMatrix( sr.getTransformMatrix() );
		
		l.resetIterator( vx, vy, vw, vh );
		sr.begin( ShapeType.Line );
		while( l.hasNext() )
		{
			StarMapChunkComponent chunk = l.next();
			if( chunk == null ) 
			{
				continue;	
			}
			
			sr.rect( chunk.pos.x * StarMapComponent.chunkSize, chunk.pos.y * StarMapComponent.chunkSize, StarMapComponent.chunkSize, StarMapComponent.chunkSize );
			
			for( SyncReference<StarSystemComponent> starRef : chunk.stars )
			{
				StarSystemComponent star = starRef.get();
				if( star != null )
				{
					if( star.pos.dst2( xMouse, yMouse ) < 20*20 )
					{
						sr.setColor( 1, 0, 0, 1 );
						sr.circle( star.pos.x, star.pos.y, StarMapComponent.connectDistance );
					}
					else if( star == ssc )
					{
						sr.setColor( 0, 1, 0, 1 );
					}
					else
					{
						sr.setColor( 1, 1, 1, 1 );
					}
					sr.circle( star.pos.x, star.pos.y, 10 );
				
					for( SyncReference<StarSystemComponent> otherStarRef : star.connectedStars )
					{
						StarSystemComponent otherStar = otherStarRef.get();
						if( otherStar != null )
						{
							sr.line( star.pos, otherStar.pos );
						}
					}
				}
			}
		}
		sr.end();
		
		batch.begin();
		l.resetIterator( vx, vy, vw, vh );
		while( l.hasNext() )
		{
			StarMapChunkComponent chunk = l.next();
			if( chunk == null ) 
			{
				continue;	
			}
			for( SyncReference<StarSystemComponent> starRef : chunk.stars )
			{
				StarSystemComponent star = starRef.get();
				if( star != null )
				{
					font.draw( batch, star.name, star.pos.x + 10, star.pos.y );
				}
			}
		}
		batch.end();
		
		sr.setTransformMatrix( oldTransform );
		batch.setTransformMatrix( oldTransform );
	}
}
