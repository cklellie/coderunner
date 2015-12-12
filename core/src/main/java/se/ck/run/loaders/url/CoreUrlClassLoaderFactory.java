package se.ck.run.loaders.url;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for handling http and https URL Streams.
 * @author cklellie
 *
 */
public class CoreUrlClassLoaderFactory implements URLStreamHandlerFactory{

	private Map<String,URLStreamHandler> mMap = null;
	
	/**
	 * Create a Factory.
	 */
	public CoreUrlClassLoaderFactory( )
	{
		mMap = new HashMap<String,URLStreamHandler>();
		mMap.put( "http", new CoreUrlStreamHandler() );
		mMap.put( "https", new CoreUrlStreamHandler() );
	}
	
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) 
	{
		return mMap.get( protocol.toLowerCase() );
	}

}
