package se.ck.run.loaders;

import java.net.URLClassLoader;

public class CoreClassLoader extends URLClassLoader 
{	
	public CoreClassLoader( Library library )
	{
		super( library.getUrlsAsArray(), CoreClassLoader.class.getClassLoader() );
	}
}
