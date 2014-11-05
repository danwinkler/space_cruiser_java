package space_cruiser_java;

import java.io.IOException;

import com.danwink.game_framework.network.NetworkServer;

public class SpaceCruiserServer 
{
	NetworkServer server;
	
	public SpaceCruiserServer()
	{
		server = new NetworkServer( StaticFiles.registerClasses );
	}
	
	public void start() throws IOException
	{
		server.start( 54321, 54322 );
	}
	
	public interface ServerState
	{
		public void activate( SpaceCruiserServer s, NetworkServer n );
	}
}
