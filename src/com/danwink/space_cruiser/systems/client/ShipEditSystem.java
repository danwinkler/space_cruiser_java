package com.danwink.space_cruiser.systems.client;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.danwink.game_framework.network.SyncEngine;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.components.MapComponent;
import com.danwink.space_cruiser.components.PlayerShipComponent;
import com.danwink.space_cruiser.components.ShipComponent;
import com.danwink.space_cruiser.game_objects.Tiles.SCTile;
import com.danwink.space_cruiser.screens.ShipEditOverlay;
import com.phyloa.dlib.math.Point2i;

public class ShipEditSystem extends IteratingSystem implements InputProcessor
{
	SyncEngine engine;
	
	MapComponent map;
	
	ShapeRenderer sr;
	SpriteBatch batch;
	BitmapFont font;
	
	ShipEditOverlay overlay;

	public ShipEditSystem( ShipEditOverlay overlay, ShapeRenderer sr, SpriteBatch batch )
	{
		super( Family.all( PlayerShipComponent.class ).get() );
		
		this.overlay = overlay;
		this.sr = sr;
		this.batch = batch;
	}
	
	@Override
	public void addedToEngine( Engine engine )
	{
		super.addedToEngine( engine );
		this.engine = (SyncEngine)engine;
		
		font = new BitmapFont( true );
	}
	
	@Override
	public void removedFromEngine( Engine engine )
	{
		super.removedFromEngine( engine );
		font.dispose();
	}
	
	public void end()
	{

	}

	protected void processEntity( Entity entity, float deltaTime )
	{
		map = Mappers.map.get( entity );
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
		//Point2i tilePos = map.map.worldToTileSpace( screenX, screenY );
		
		//map.map.setTile( null, tilePos.x, tilePos.y );
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
