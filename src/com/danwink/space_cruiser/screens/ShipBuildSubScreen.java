package com.danwink.space_cruiser.screens;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.danwink.game_framework.network.MessagePacket;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.network.NetworkMessager.MessageListener;
import com.danwink.space_cruiser.ClientMessages;
import com.danwink.space_cruiser.ServerMessages;
import com.danwink.space_cruiser.ServerMessages.ShipBuild;
import com.danwink.space_cruiser.ServerMessages.ShipBuildChangeTilePacket;
import com.danwink.space_cruiser.game_objects.Ship;
import com.danwink.space_cruiser.game_objects.Tiles;
import com.phyloa.dlib.math.Point2i;

import game_framework.Screen;
import game_framework.ScreenManager;
import game_framework.TileMap.Tile;

public class ShipBuildSubScreen implements Screen, InputProcessor
{
	OrthographicCamera camera;
	Vector3 mousePos = new Vector3();
	
	Stage stage;
	Table table;
	
	ShapeRenderer sr;
	SpriteBatch batch;
	
	Texture floor;
	
	NetworkClient client;
	
	Ship ship;
	
	public void activate( Optional<Object> o )
	{
		camera = new OrthographicCamera();
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		
		stage = new Stage();
	    Gdx.input.setInputProcessor( stage );

	    table = new Table();
	    table.setFillParent( true );
	    stage.addActor( table );
	    
	    resize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		
	    //Grab the client instance from PlayScreen
	    client = PlayScreen.class.cast( ScreenManager.getScreenManager( this ) ).client;
	    
	    client.group( ShipBuildSubScreen.class, h -> {
	    	h.on( ServerMessages.ShipBuild.SHIP, (MessagePacket<Ship> m) -> {
		    	this.ship = m.getValue();
		    });
		    
		    h.on( ServerMessages.ShipBuild.CHANGETILE, (MessagePacket<ServerMessages.ShipBuildChangeTilePacket> m) -> {
		    	ShipBuildChangeTilePacket v = m.getValue();
		    	ship.setTile( v.t, v.p.x, v.p.y );
		    });
	    });
	    
	    client.sendTCP( ClientMessages.ShipBuild.JOIN, null );
	    
	    Gdx.input.setInputProcessor( this );
	}
	
	public void render() 
	{
		//Update code
		stage.act( Gdx.graphics.getDeltaTime() );
	    
		camera.update();
	
		sr.setProjectionMatrix( camera.combined );
		batch.setProjectionMatrix( camera.combined );
		
		client.update();
		
		//Drawing code
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
		sr.setColor( 1, 1, 1, 1 );
		
		if( ship != null )
		{
			ship.renderGrid( sr, batch, mousePos );
		}
		
		stage.draw();

	    table.drawDebug( sr );
	    
	    try {
			Thread.sleep( 20 );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private void computeMousePos( float x, float y )
	{
		mousePos.x = x;
		mousePos.y = y;
		camera.unproject( mousePos );
	}
	
	public void exit() 
	{
		sr.dispose();
		stage.dispose();
		batch.dispose();
		client.clearListeners( ShipBuildSubScreen.class );
	}
	
	public void resize( int width, int height )
	{
		camera.setToOrtho( true, width, height );
		stage.getViewport().update( width, height, true );
	}

	public boolean keyDown( int arg0 ) {
		return false;
	}

	public boolean keyTyped(char arg0) {
		return false;
	}

	public boolean keyUp(int arg0) {
		return false;
	}

	public boolean mouseMoved( int x, int y )
	{
		computeMousePos( x, y );
		return false;
	}

	public boolean scrolled(int arg0) {
		return false;
	}

	public boolean touchDown( int x, int y, int pointer, int button ) 
	{
		computeMousePos( x, y );
		
		if( button == Input.Buttons.LEFT )
		{
			setTile( new Tiles.Floor(), x, y );
		}
		return false;
	}

	public boolean touchDragged( int x, int y, int pointer ) 
	{
		computeMousePos( x, y );
		
		if( Gdx.input.isButtonPressed( Input.Buttons.LEFT ) )
		{
			setTile( new Tiles.Floor(), x, y );
		}
		return false;
	}

	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}
	
	public void setTile( Tile tile, float mouseX, float mouseY )
	{
		Point2i tileSpace = ship.worldToTileSpace( mousePos );
		Tile current = ship.getTile( tileSpace );
		if( current == null || !tile.getClass().equals( current.getClass() ) )
		{
			client.sendTCP( ClientMessages.ShipBuild.CHANGETILE, new ClientMessages.ShipBuildChangeTilePacket( tile, tileSpace ));
		}
	}
}
