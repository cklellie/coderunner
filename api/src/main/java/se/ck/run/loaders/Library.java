package se.ck.run.loaders;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Library 
{

	List<URL> mUrls = null;
	
	public Library()
	{
		mUrls = new ArrayList<URL>();
	}

	public List<URL> getUrls() 
	{
		return mUrls;
	}

	public URL[] getUrlsAsArray() 
	{
		return mUrls.toArray( new URL[mUrls.size()] );
	}

	public void add(URL item) 
	{
		mUrls.add( item );
	}
	
}
