package com.danwink.space_cruiser;

import java.io.FileNotFoundException;

import com.phyloa.dlib.util.DFile;
import com.phyloa.dlib.util.DMath;
import com.phyloa.dlib.util.DUtil;

public class StarNamer
{
	public static String[] names;
	
	static 
	{
		try
		{
			names = DFile.loadText( "starnames.txt" ).split( "\n" );
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
	}
	
	public static String genStarName()
	{
		return DUtil.capitalize( names[DMath.randomi( 0, names.length )] ) + " " + DUtil.capitalize( names[DMath.randomi( 0, names.length )] ) + " " + DUtil.romanNumeral( DMath.randomi( 1, 11 ) ); 
	}
	
	public static void main( String[] args )
	{
		for( int i = 0; i < 10; i++ )
		{
			System.out.println( genStarName() );
		}
	}
}
