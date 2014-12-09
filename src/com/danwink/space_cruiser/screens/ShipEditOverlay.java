package com.danwink.space_cruiser.screens;

import java.util.Optional;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.network.SyncEngine;
import com.danwink.game_framework.screens.OverlayScreen;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.systems.client.ShipEditSystem;
import com.phyloa.dlib.math.Point2i;

public class ShipEditOverlay extends OverlayScreen implements InputProcessor
{
	private SyncEngine engine;
	private NetworkClient client;
	
	ShipEditSystem smr;

	MapComponent map;
	
	public ShipEditOverlay( SyncEngine engine, NetworkClient client )
	{
		super( 0 );
		this.setFadeBackground( false );
		
		this.engine = engine;
		this.client = client;
	}

	public void activate( Optional<Object> o )
	{		
		engine.addSystem( smr = new ShipEditSystem( this, sr, batch ) );
		smr.setProcessing( false );
		this.addInputProcessor( smr );
		this.addInputProcessor( this );
		
		table.add( new TextButton( "hello", skin ) );
		table.bottom().right();
		
		//Find the only entity with a PlayerShipComponent, and grab the map component from it
		map = Mappers.map.get( engine.getEntitiesFor( Family.all( PlayerShipComponent.class ).get() ).first() );
	}

	public void render()
	{
		smr.update( 0 );
	}

	public void exit()
	{
		sr.dispose();
		
		engine.removeSystem( smr );
	}

	public void resize( int width, int height )
	{
		
	}
	
	public boolean keyDown( int keycode )
	{
		return false;
	}

	public boolean keyUp( int keycode )
	{
		return false;
	}

	public boolean keyTyped( char character )
	{
		return false;
	}

	public boolean touchDown( int screenX, int screenY, int pointer, int button )
	{
		Point2i tilePos = map.map.worldToTileSpace( screenX, screenY );
		
		map.map.setTile( null, tilePos.x, tilePos.y );
		
		return false;
	}

	public boolean touchUp( int screenX, int screenY, int pointer, int button )
	{
		return false;
	}

	public boolean touchDragged( int screenX, int screenY, int pointer )
	{
		return false;
	}

	public boolean mouseMoved( int screenX, int screenY )
	{
		return false;
	}

	public boolean scrolled( int amount )
	{
		return false;
	}	
}