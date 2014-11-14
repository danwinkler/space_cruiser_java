package com.danwink.space_cruiser.screens;

import game_framework.ClientEntitySyncSystem;
import game_framework.SyncEngine;

import java.util.Optional;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.screens.BasicScreen;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.Mappers;
import com.danwink.space_cruiser.SpaceCruiser;
import com.danwink.space_cruiser.components.MoveComponent;
import com.danwink.space_cruiser.server.ShipServerHandler;
import com.danwink.space_cruiser.systems.MapRenderSystem;
import com.danwink.space_cruiser.systems.ClientShipSystem;
import com.danwink.space_cruiser.systems.MoveRenderSystem;
import com.danwink.space_cruiser.systems.MoveSystem;

public class ShipSubScreen extends BasicScreen
{
	NetworkClient client;
	
	SyncEngine engine;
	
	MapRenderSystem mrs;
	
	Vector3 mousePos = new Vector3();
	
	public void activate( Optional<Object> o )
	{
		client = (NetworkClient)o.get();
		
		engine = new SyncEngine();
		
		engine.addSystem( new ClientEntitySyncSystem( client, ShipSubScreen.class ) );
		
		engine.addSystem( mrs = new MapRenderSystem( sr, batch ) );
		engine.addSystem( new ClientShipSystem() );
		
		engine.addSystem( new MoveSystem() );
		engine.addSystem( new MoveRenderSystem( sr, batch ) );
		
		client.sendTCP( ClientMessages.Ship.JOIN, null );
	}
	
	public void render()
	{
		client.update();
		mousePos.x = Gdx.input.getX();
		mousePos.y = Gdx.input.getY();
		camera.unproject( mousePos );
		mrs.setMousePos( mousePos );
		engine.update( Gdx.graphics.getDeltaTime() );
		
		
		MoveComponent sc = Mappers.move.get( ((ShipServerHandler)SpaceCruiser.localServer.states.get( ShipServerHandler.class )).engine.getEntitiesFor( Family.getFor( MoveComponent.class ) ).first() );
		sr.begin( ShapeType.Line );
		sr.setColor( 1, 1, 1, 1 );
		sr.circle( sc.x, sc.y, 10 );
		sr.end();
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
