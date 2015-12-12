package se.ck.run.loaders;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains lists of locations of resources
 * to be used when initialising a new class loader.
 * 
 * @author cklellie
 *
 */
public class Library 
{

	List<URL> mUrls = null;
	
	/**
	 * Create an empty library.
	 */
	public Library()
	{
		mUrls = new ArrayList<URL>();
	}

	/**
	 * Get all the currently stored URLs.
	 * @return all URLs.
	 */
	public List<URL> getUrls() 
	{
		return mUrls;
	}

	/**
	 * Get all the currently stored as an Array.
	 * @return all URLs as an Array.
	 */
	public URL[] getUrlsAsArray() 
	{
		return mUrls.toArray( new URL[mUrls.size()] );
	}

	/**
	 * Add a URL location to the library.
	 * 
	 * @param item location to added to the library.
	 */
	public void add(URL item) 
	{
		mUrls.add( item );
	}
	
	/**
	 * Add a file location to the library.
	 * 
	 * Constructs the necessary URL.
	 * 
	 * @param item file to be added to the library.
	 * 
	 * @throws MalformedURLException
	 */
	public void add( File item ) throws MalformedURLException
	{
		add( item.toURI().toURL() );
	}
	
	/**
	 * Add a path location to the library.
	 * 
	 * Constructs the necessary File and URL
	 * 
	 * @param item path location to be added to the library.
	 * 
	 * @throws MalformedURLException
	 */
	public void add( String item ) throws MalformedURLException 
	{
		add( new File( item ) );
	}
	
	/**
	 * Current number of locations in the library.
	 * 
	 * @return total number of locations.
	 */
	public int size()
	{
		return mUrls.size();
	}
	
	/**
	 * Empty the library of all locations.
	 */
	public void clear()
	{
		mUrls.clear();
	}
	
}
