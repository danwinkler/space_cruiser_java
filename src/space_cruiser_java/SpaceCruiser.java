package space_cruiser_java;

import game_framework.DGame;
import game_framework.ScreenManager;

import com.danwink.game_framework.screens.TitleScreen;

public class SpaceCruiser 
{
	NetworkServer
	
	public static void main( String[] args )
	{
		DGame g = new DGame( "Space Cruiser", 800, 600 );
		
		g.begin( () -> {
			g.add( "title", new TitleScreen( "Space Cruiser" ) {{
				setTitleScale( 2 );
				addButton( "Start Local", () -> {
					ScreenManager.getScreenManager( this ).activate( "play" );
				});
			}});
			
			g.add( "play", new PlayScreen() );
			
			g.activate( "title" );
		});
	}
}
