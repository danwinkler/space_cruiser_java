package space_cruiser_java;

import java.io.IOException;
import java.util.HashMap;

import com.danwink.game_framework.network.NetworkServer;

public class SpaceCruiserServer 
{
	NetworkServer server;
	
	HashMap<Class, ServerState> states = new HashMap<Class, ServerState>();
	
	public SpaceCruiserServer()
	{
		server = new NetworkServer( StaticFiles.registerClasses );
		
		states.put( ShipBuildServerState.class, new ShipBuildServerState() );
	}
	
	public void start() throws IOException
	{
		server.start( 54321, 54322 );
		
		activate( ShipBuildServerState.class );
	}
	
	private void activate( Class c )
	{
		states.get( c ).activate( this, server );
	}

	public interface ServerState
	{
		public void activate( SpaceCruiserServer s, NetworkServer n );
	}
}
