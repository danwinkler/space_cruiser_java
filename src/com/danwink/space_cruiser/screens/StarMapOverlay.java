package com.danwink.space_cruiser.screens;

import java.util.Optional;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.network.SyncEngine;
import com.danwink.game_framework.screens.OverlayScreen;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.systems.client.StarMapRenderSystem;

public class StarMapOverlay extends OverlayScreen
{
	private SyncEngine engine;
	private NetworkClient client;
	
	StarMapRenderSystem smr;

	public StarMapOverlay( SyncEngine engine, NetworkClient client )
	{
		super( 100 );
		
		this.engine = engine;
		this.client = client;
	}

	public void activate( Optional<Object> o )
	{		
		engine.addSystem( smr = new StarMapRenderSystem( this, sr, batch ) );
		smr.setProcessing( false );
		
		table.add( new TextButton( "hello", skin ) );
		table.bottom().right();
		
		client.sendTCP( ClientMessages.StarMap.VIEW, null );
	}

	public void render()
	{
		sr.setColor( 1, 1, 1, 1 );
		
		sr.begin( ShapeType.Line );
		sr.rect( 1, 1, width-1, height-1 );
		sr.end();
			
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
}
