package space_cruiser_java;

import java.util.Optional;

import com.danwink.game_framework.network.NetworkClient;

import game_framework.ScreenManager;

public class PlayScreen extends ScreenManager
{
	NetworkClient client;
	
	public PlayScreen() 
	{
		add( "shipbuild", new ShipBuildSubScreen() );
	}
	
	@Override
	public void activate( Optional<Object> o ) 
	{
		o.ifPresent( c -> client = (NetworkClient)c );
		
		activate( "shipbuild" );
	}
}
