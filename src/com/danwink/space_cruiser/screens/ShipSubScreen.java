package com.danwink.space_cruiser.screens;

import java.util.Optional;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.screens.BasicScreen;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.systems.MapRenderSystem;
import com.danwink.space_cruiser.systems.ClientShipSystem;

public class ShipSubScreen extends BasicScreen
{
	NetworkClient client;
	
	Engine engine;
	
	public void activate( Optional<Object> o )
	{
		client = (NetworkClient)o.get();
		
		engine = new Engine();
		
		client.group( ShipSubScreen.class, g -> {
			engine.addSystem( new MapRenderSystem( sr, batch ) );
			engine.addSystem( new ClientShipSystem( client ) );
		});
		
		client.sendTCP( ClientMessages.Ship.JOIN, null );
	}
	
	public void render()
	{
		engine.update( Gdx.graphics.getDeltaTime() );
	}

	public void exit()
	{
		
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
