package com.danwink.space_cruiser.systems.client;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;

public class ShipEditSystem extends IteratingSystem implements InputProcessor
{
	ShipComponent ship;
	
	public ShipEditSystem()
	{
		super( Family.all( PlayerShipComponent.class ).get() );
	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		ship = Mappers.ship.get( entity );
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
