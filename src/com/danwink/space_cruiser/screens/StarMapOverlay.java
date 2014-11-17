package com.danwink.space_cruiser.screens;

import game_framework.SyncEngine;

import java.util.Optional;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.screens.OverlayScreen;

public class StarMapOverlay extends OverlayScreen
{
	public StarMapOverlay( SyncEngine engine, NetworkClient client )
	{
		super( 100 );
		
	}

	public void activate( Optional<Object> o )
	{		
		table.add( new TextButton( "hello", skin ) );
		table.bottom().right();
	}

	public void render()
	{
		sr.setColor( 1, 1, 1, 1 );
		
		sr.begin( ShapeType.Line );
		sr.rect( 0, 0, width, height );
		sr.rect( 10, 10, 40, 40 );
		sr.end();
	}

	public void exit()
	{
		sr.dispose();
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
