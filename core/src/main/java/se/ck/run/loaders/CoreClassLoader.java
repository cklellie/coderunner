package se.ck.run.loaders;

import java.net.URLClassLoader;

import se.ck.run.loaders.url.CoreUrlClassLoaderFactory;

/**
 * 
 * ClassLoader that takes care of all types and protocols.
 * 
 * @author cklellie
 *
 */
public class CoreClassLoader extends URLClassLoader 
{	
	/**
	 * Create a class loader including current parent 
	 * class loader and all URLs from the library
	 * provided.
	 * 
	 * @param library of locations to be accessible to class loader.
	 */
	public CoreClassLoader( Library library )
	{
		this( library, CoreClassLoader.class.getClassLoader() );
	}
	
	/**
	 * Create a class loader with the specific parent
	 * class loader and all URLs from the library
	 * provided.
	 * 
	 * @param library of locations to be accessible to class loader. 
	 * @param parent class loader for the new class loader.
	 */
	public CoreClassLoader( Library library, ClassLoader parent )
	{
		super( library.getUrlsAsArray(), parent, new CoreUrlClassLoaderFactory() );
	}
}