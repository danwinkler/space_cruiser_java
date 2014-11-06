package space_cruiser_java;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.danwink.game_framework.network.MessagePacket;
import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.network.NetworkMessager.MessageListener;
import com.danwink.space_cruiser.game_objects.Ship;

import game_framework.Screen;
import game_framework.ScreenManager;

public class ShipBuildSubScreen implements Screen 
{
	OrthographicCamera camera;
	ShapeRenderer sr;
	
	Stage stage;
	Table table;
	
	NetworkClient client;
	
	Ship ship;
	
	public void activate( Optional<Object> o )
	{
		camera = new OrthographicCamera();
		sr = new ShapeRenderer();
		
		stage = new Stage();
	    Gdx.input.setInputProcessor( stage );

	    table = new Table();
	    table.setFillParent( true );
	    stage.addActor( table );
	    
	    resize( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		
	    //Grab the client instance from PlayScreen
	    client = PlayScreen.class.cast( ScreenManager.getScreenManager( this ) ).client;
	    
	    client.on( ShipBuildSubScreen.class, ServerMessages.ShipBuild.SHIP, (MessagePacket<Ship> m) -> {
	    	this.ship = m.getValue();
	    });
	    
	    client.sendTCP( ClientMessages.ShipBuild.JOIN, null );
	}
	
	public class T implements MessageListener<Ship>
	{
		public void on(MessagePacket<Ship> m) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void render() 
	{
		//Update code
		stage.act( Gdx.graphics.getDeltaTime() );
	    
		camera.update();
		
		sr.setProjectionMatrix( camera.combined );
		
		client.update();
		
		//Drawing code
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
		sr.setColor( 1, 1, 1, 1 );
		
		if( ship != null )
		{
			ship.render( true, sr );
		}
		
		stage.draw();

	    table.drawDebug( sr );
	}	

	public void exit() 
	{
		sr.dispose();
		stage.dispose();
		client.clearListeners( ShipBuildSubScreen.class );
	}
	
	public void resize( int width, int height )
	{
		camera.setToOrtho( true, width, height );
		stage.getViewport().update( width, height, true );
	}
}
