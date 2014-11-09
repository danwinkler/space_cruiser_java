package com.danwink.space_cruiser.screens;

import java.util.Optional;

import com.danwink.game_framework.network.NetworkClient;

import game_framework.ScreenManager;

public class PlayScreen extends ScreenManager
{
	NetworkClient client;
	
	public PlayScreen() 
	{
		add( "shipbuild", new ShipBuildSubScreen() );
		add( "spacenavigate", new SpaceNavigateSubScreen() );
	}
	
	@Override
	public void activate( Optional<Object> o ) 
	{
		o.ifPresent( c -> client = (NetworkClient)c );
		
		activate( "shipbuild" );
	}
}
