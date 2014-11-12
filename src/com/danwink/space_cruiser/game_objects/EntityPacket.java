package com.danwink.space_cruiser.game_objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;

public class EntityPacket
{
	public long id;
	public ArrayList<Component> components;
	
	public EntityPacket()
	{
		
	}
	
	@SafeVarargs
	public EntityPacket( long id, ImmutableArray<Component> components, Class<? extends Component>... classes )
	{
		List<Class<? extends Component>> classList = Arrays.asList( classes );
		this.id = id;
		this.components = new ArrayList<Component>();
		for( int i = 0; i < components.size(); i++ )
		{
			Component c = components.get( i );
			if( classes.length == 0 || classList.contains( c.getClass() ) ) this.components.add( components.get( i ) );
		}
	}

	public static Class[] getClasses()
	{
		return new Class[] {
			EntityPacket.class,
			ArrayList.class
		};
	}
}
