package se.ck.run.loaders;

import java.net.URLClassLoader;

import se.ck.run.loaders.url.CoreUrlClassLoaderFactory;

public class CoreClassLoader extends URLClassLoader 
{	
	public CoreClassLoader( Library library )
	{
		super( library.getUrlsAsArray(), CoreClassLoader.class.getClassLoader(), new CoreUrlClassLoaderFactory() );
	}		
}