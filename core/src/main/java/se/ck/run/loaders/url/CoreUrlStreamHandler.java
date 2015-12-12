package se.ck.run.loaders.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Handler for URL Streams.
 * 
 * @author cklellie
 *
 */
public class CoreUrlStreamHandler extends URLStreamHandler
{
	@Override
	protected URLConnection openConnection( URL url ) throws IOException 
	{
		return url.openConnection();
	}

}
