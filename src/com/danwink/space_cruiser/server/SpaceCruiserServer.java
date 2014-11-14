package com.danwink.space_cruiser.server;

import game_framework.GameLoop;

import java.io.IOException;
import java.util.HashMap;

import com.danwink.game_framework.network.NetworkServer;
import com.danwink.space_cruiser.StaticFiles;

public class SpaceCruiserServer 
{
	NetworkServer server;
	
	public HashMap<Class, ServerGameHandler> states = new HashMap<Class, ServerGameHandler>();
	
	GameLoop gl;
	
	boolean running = true;
	
	public SpaceCruiserServer()
	{
		server = new NetworkServer( StaticFiles.get() );
		
		states.put( ShipBuildServerHandler.class, new ShipBuildServerHandler() );
		states.put( SpaceNavigateServerHandler.class, new SpaceNavigateServerHandler() );
		states.put( ShipServerHandler.class, new ShipServerHandler() );
	}
	
	public void start() throws IOException
	{
		server.start( 54321, 54322 );
		
		activate( ShipServerHandler.class );
		
		gl = new GameLoop();
		gl.start( delta -> {
			update( delta );
		});
	}
	
	public void update( float delta )
	{
		server.update();
		for( ServerGameHandler s : states.values() )
		{
			s.update( this, server, delta );
		}
	}
	
	private void activate( Class c )
	{
		states.get( c ).activate( this, server );
	}

	public interface ServerGameHandler
	{
		public void activate( SpaceCruiserServer s, NetworkServer n );
		public void update( SpaceCruiserServer s, NetworkServer n, float delta );
	}
}
