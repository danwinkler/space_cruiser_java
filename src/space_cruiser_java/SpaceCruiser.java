package space_cruiser_java;

import java.io.IOException;

import game_framework.DGame;
import game_framework.ScreenManager;

import com.danwink.game_framework.network.NetworkClient;
import com.danwink.game_framework.screens.TitleScreen;
import com.danwink.space_cruiser.screens.PlayScreen;
import com.danwink.space_cruiser.server.SpaceCruiserServer;

public class SpaceCruiser 
{
	public static SpaceCruiserServer localServer;
	
	public static void main( String[] args )
	{
		DGame g = new DGame( "Space Cruiser", 1280, 800 );
		
		g.begin( () -> {
			g.add( "title", new TitleScreen( "Space Cruiser" ) {{
				setTitleScale( 2 );
				addButton( "Start Local", () -> {
					try {
						startServer();
						ScreenManager.getScreenManager( this ).activate( "play", createClient( "localhost" ) );
					} catch( Exception e ) {
						e.printStackTrace();
					}
				});
			}});
			
			g.add( "play", new PlayScreen() );
			
			g.activate( "title" );
		});
	}
	
	public static void startServer() throws IOException
	{
		localServer = new SpaceCruiserServer();
		localServer.start();
	}
	
	public static NetworkClient createClient( String address ) throws IOException
	{
		NetworkClient c = new NetworkClient( StaticFiles.get() );
		c.connect( address, 500, 54321, 54322 );
		return c;
	}
}
