package space_cruiser_java;

import game_framework.DGame;

import com.danwink.game_framework.screens.TitleScreen;

public class SpaceCruiser 
{
	public static void main( String[] args )
	{
		DGame g = new DGame( "Space Cruiser", 800, 600 );
		
		g.begin( () -> {
			g.add( "title", new TitleScreen( "Space Cruiser" ) );
			
			g.add( "play", new PlayScreen() );
			
			g.activate( "title" );
		});
	}
}
