package com.danwink.space_cruiser;

import java.io.FileNotFoundException;
import java.util.HashMap;

import com.phyloa.dlib.util.DFile;
import com.phyloa.dlib.util.DMath;
import com.phyloa.dlib.util.DUtil;

public class StarNamer
{
	public static HashMap<String, String[]> nameLists;
	
	static 
	{
		try
		{
			nameLists = new HashMap<String, String[]>();
			
			loadName( "alienprefix", "alienprefix.txt" );
			loadName( "aliensuffix", "aliensuffix.txt" );
			loadName( "greek", "greek.txt" );
			loadName( "suffix", "suffix.txt" );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
	}
	
	private static void loadName( String name, String filename ) throws FileNotFoundException
	{
		nameLists.put( name, DFile.loadText( "data/names/" + filename ).split( "\n" ) );
	}
	
	public static String genStarName()
	{
		int i = DMath.randomi( 0, 3 );
		switch( i )
		{
			case 0:
				return DUtil.capitalize( getName( "alienprefix" ) ) + getName( "aliensuffix" );
			default:
				return DUtil.capitalize( getName( "greek" ) ) + 
				" " + 
				DUtil.capitalize( getName( "greek" ) ) + 
				" " +  
				(DMath.randomf() > .5f ? DUtil.capitalize( getName( "suffix" ) ) : DUtil.romanNumeral( DMath.randomi( 1, 11 ) ));
		}
	}
	
	public static String getName( String name )
	{
		String[] list = nameLists.get( name );
		return list[DMath.randomi( 0, list.length )];
	}
	
	public static void main( String[] args )
	{
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( genStarName() );
		}
	}
}
