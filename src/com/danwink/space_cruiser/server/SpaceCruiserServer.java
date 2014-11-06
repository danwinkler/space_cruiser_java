package com.danwink.space_cruiser.server;

import java.io.IOException;
import java.util.HashMap;

import space_cruiser_java.StaticFiles;

import com.danwink.game_framework.network.NetworkServer;

public class SpaceCruiserServer 
{
	NetworkServer server;
	
	HashMap<Class, ServerState> states = new HashMap<Class, ServerState>();
	ServerState current = null;
	
	Thread updateLoop;
	
	boolean running = true;

	private long lastTime;
	private long timeDiff;
	long frameTime = 1000/30;
	
	public SpaceCruiserServer()
	{
		server = new NetworkServer( StaticFiles.registerClasses );
		
		states.put( ShipBuildServerState.class, new ShipBuildServerState() );
	}
	
	public void start() throws IOException
	{
		server.start( 54321, 54322 );
		
		activate( ShipBuildServerState.class );
		
		updateLoop = new Thread( () -> {
			lastTime = System.currentTimeMillis();
			while( running )
			{
				try{
				update();
				} catch( Exception ex )
				{
					ex.printStackTrace();
				}
				long time = System.currentTimeMillis();
				timeDiff = (lastTime + frameTime) - time;
				if( timeDiff > 0 )
				{
					try {
						Thread.sleep( timeDiff );
					} catch( InterruptedException e ) {
						e.printStackTrace();
					}
				}
				lastTime = System.currentTimeMillis();
			}
			server.stop();
		});
		updateLoop.start();
		
	}
	
	public void update()
	{
		server.update();
		if( current != null )
		{
			current.update( this, server );
		}
	}
	
	private void activate( Class c )
	{
		states.get( c ).activate( this, server );
	}

	public interface ServerState
	{
		public void activate( SpaceCruiserServer s, NetworkServer n );
		public void update( SpaceCruiserServer s, NetworkServer n );
	}
}
