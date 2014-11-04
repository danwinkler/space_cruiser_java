package space_cruiser_java;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.danwink.game_framework.network.NetworkClient;

import game_framework.Screen;
import game_framework.ScreenManager;

public class ShipBuildSubScreen implements Screen 
{
	OrthographicCamera camera;
	ShapeRenderer sr;
	
	Stage stage;
	Table table;
	
	NetworkClient c;
	
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
	    c = PlayScreen.class.cast( ScreenManager.getScreenManager( this ) ).client;
	    
	    //Clear the listeners; we're in control now
	    c.clearListeners();
	}
	
	public void render() 
	{
		//Update code
		stage.act( Gdx.graphics.getDeltaTime() );
	    
		camera.update();
		
		sr.setProjectionMatrix( camera.combined );
		
		//Drawing code
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
		sr.setColor( 1, 1, 1, 1 );
		
		sr.begin( ShapeType.Line );
		
		sr.rect( Gdx.input.getX(), Gdx.input.getY(), 20, 20 );
		
		sr.end();
		
		stage.draw();

	    table.drawDebug( sr );
	}	

	public void exit() 
	{
		sr.dispose();
		stage.dispose();
	}
	
	public void resize( int width, int height )
	{
		camera.setToOrtho( true, width, height );
		stage.getViewport().update( width, height, true );
	}
}
