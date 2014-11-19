package com.danwink.space_cruiser.systems.client;

import game_framework.InfiniteChunkManager.Layer;
import game_framework.SyncReference;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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
	
	public StarMapRenderSystem( StarMapOverlay overlay )
	{
		super( Family.all( StarMapComponent.class ).get() );
		
		this.overlay = overlay;
	}

	@Override
	public void addedToEngine( Engine engine )
	{
		super.addedToEngine( engine );
		this.engine = engine;
		
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		
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
		
		//TODO: figure out a better way to do this junk. What a mess
		sr.translate( -ssc.pos.x + overlay.width*.5f + overlay.margin, -ssc.pos.y + overlay.height*.5f + overlay.margin, 0 );
		batch.setTransformMatrix( sr.getTransformMatrix() );
		
		l.resetIterator( vx, vy, vw, vh );
		sr.begin( ShapeType.Filled );
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
					if( star == ssc )
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
		
		sr.setTransformMatrix( new Matrix4() );
	}
}
