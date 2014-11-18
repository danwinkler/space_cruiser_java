package com.danwink.space_cruiser.screens;

import game_framework.ClientEntitySyncSystem;
import game_framework.InfiniteChunkManager;
import game_framework.ScreenManager;
import game_framework.SyncEngine;

import java.util.Optional;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.network.NetworkServer;
import com.danwink.game_framework.network.Subscription;
import com.danwink.game_framework.screens.BasicScreen;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.components.StarMapChunkComponent;
import com.danwink.space_cruiser.components.StarMapComponent;
import com.danwink.space_cruiser.systems.MoveSystem;
import com.danwink.space_cruiser.systems.client.ClientShipSystem;
import com.danwink.space_cruiser.systems.client.ClientStarMapSystem;
import com.danwink.space_cruiser.systems.client.MapRenderSystem;
import com.danwink.space_cruiser.systems.client.MoveRenderSystem;

public class PlayScreen extends BasicScreen
{
	NetworkClient client;
	
	SyncEngine engine;
	
	MapRenderSystem mrs;
	
	Vector3 mousePos = new Vector3();
	
	ScreenManager overlayManager;
	
	public void activate( Optional<Object> o )
	{
		client = (NetworkClient)o.get();
		
		engine = new SyncEngine();
		
		engine.addSystem( new ClientEntitySyncSystem( client, PlayScreen.class ) );
		
		engine.addSystem( mrs = new MapRenderSystem( sr, batch ) );
		engine.addSystem( new ClientShipSystem() );
		
		engine.addSystem( new MoveSystem() );
		engine.addSystem( new MoveRenderSystem( sr, batch ) );
		
		engine.addSystem( new ClientStarMapSystem( client ) );
		
		Entity starMapEntity = new Entity();
		StarMapComponent starMap = new StarMapComponent();
		starMap.icm = new InfiniteChunkManager<StarMapChunkComponent>( StarMapChunkComponent.class, StarMapComponent.chunkSize, StarMapComponent.chunkSize );
		starMap.icm.newLayer( "main" );
		starMapEntity.add( starMap );
		
		engine.addEntity( starMapEntity );
		
		overlayManager = new ScreenManager();
		overlayManager.add( "starmap", new StarMapOverlay( engine, client ) );
		
		buildUI();
		
		client.getClient().sendTCP( new Subscription( NetworkServer.DEFAULT_SUBSCRIPTION, true ) );
		
		client.subscribeDefault();
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
		
		//We call the BasicScreen post render function here (we overrode it so it's not called automatically later)
		//This is so we can have the overlay be last
		super.internalPostRender();
		
		overlayManager.render();
	}
	
	//Override the BasicScreen post render so that we can control when the postRender happens
	@Override
	public void internalPostRender() {}
	
	public void buildUI()
	{
		TextButton starMap = new TextButton( "Star Map", skin );
		table.add( starMap );
		table.bottom().left();
		
		starMap.addListener( new ChangeListener() {
			public void changed( ChangeEvent event, Actor actor )
			{
				overlayManager.activate( "starmap" );
			}
		});
	}

	public void exit()
	{
	
	}

	public void resize( int width, int height )
	{
		overlayManager.resize( width, height );
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
