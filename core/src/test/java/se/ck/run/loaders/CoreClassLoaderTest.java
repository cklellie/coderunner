package se.ck.run.loaders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoreClassLoaderTest {

	CoreClassLoader instance, instance1, instance2 = null;
	
	Library library = null;
	
	String oneDir = "src/test/data/one/";
	String twoDir = "src/test/data/two/";
	String oneFile = "onecontents.properties";
	String twoFile = "twocontents.properties";
	String commonFile = "contents.properties";
	
	String guavaJar = "http://repo1.maven.org/maven2/com/google/guava/guava/19.0/guava-19.0.jar";	
	String guavaRes = "com/google/common/primitives/Booleans.class";
	
	@Before
	public void setup() throws MalformedURLException
	{
		library = new Library();	
	}
	
	@After
	public void tearDown() throws IOException
	{
		if( instance != null ){instance.close();}
		if( instance1 != null ){instance1.close();}
		if( instance2 != null ){instance2.close();}
		instance = instance1 = instance2 = null;
		library = null;		
	}			
	
	@Test
	public void one() throws MalformedURLException
	{		
		library.add( oneDir );		
		instance = new CoreClassLoader( library );
		assertResource( instance, oneFile, true );
		assertResource( instance, twoFile, false );
		assertResource( instance, commonFile, true );
	}
	
	@Test
	public void two() throws MalformedURLException
	{		
		library.add( twoDir );		
		instance = new CoreClassLoader( library );
		assertResource( instance, oneFile, false );
		assertResource( instance, twoFile, true );
		assertResource( instance, commonFile, true );
	}
	
	@Test
	public void readProperties() throws IOException
	{
		Library library1 = new Library();
		library1.add( oneDir );
		instance1 = new CoreClassLoader( library1 );
		
		Library library2 = new Library();
		library2.add( twoDir );
		instance2 = new CoreClassLoader( library2 );
		
		Properties p1 = new Properties();
		p1.load( instance1.getResourceAsStream( commonFile ) );
		
		Properties p2 = new Properties();
		p2.load( instance2.getResourceAsStream( commonFile ) );
		
		assertThat( p1.getProperty( "value" ), is( equalTo( "one" ) ) );
		assertThat( p2.getProperty( "value" ), is( equalTo( "two" ) ) );
	}
	
	@Test
	public void chained( ) throws IOException
	{
		library.add( oneDir );
		instance = new CoreClassLoader( library );
		
		Library library2 = new Library();
		library2.add( twoDir );
		instance1 = new CoreClassLoader(library2, instance );
		
		assertResource( instance, oneFile, true );
		assertResource( instance, twoFile, false );
		assertResource( instance, commonFile, true );
		
		assertResource( instance1, oneFile, true );
		assertResource( instance1, twoFile, true );
		assertResource( instance1, commonFile, true );
		
		Properties p1 = new Properties();
		p1.load( instance.getResourceAsStream( commonFile ) );
		
		Properties p2 = new Properties();
		p2.load( instance1.getResourceAsStream( commonFile ) );
		
		assertThat( p1.getProperty( "value" ), is( equalTo( "one" ) ) );
		assertThat( p2.getProperty( "value" ), is( equalTo( "one" ) ) );
		
	}
	
	private void assertResource( CoreClassLoader instance, String res, boolean exists )
	{
		URL actual = instance.getResource( res );
		assertThat( actual != null, is( equalTo( exists ) ) );
	}
	
	@Test
	public void httpJar( ) throws MalformedURLException
	{
		library.add( guavaJar );
		instance = new CoreClassLoader(library);
		
		assertResource( instance, guavaRes, true );		
	}
	
	@Test
	public void localJar( ) throws IOException
	{
		String path = retrieveResourceLocally( guavaJar );
		library.add( path );
		instance = new CoreClassLoader( library );
		
		assertResource( instance, guavaRes, true );	
	}
	
	private String retrieveResourceLocally( String remotePath ) throws IOException
	{
		URL url = new URL( remotePath );
		
		InputStream is = null;
		FileOutputStream os = null;
		File localCache = null;
		try
		{
			is = url.openConnection().getInputStream();
			url.openConnection().getInputStream();		
			localCache = File.createTempFile( "locallyCached", ".jar" );			
		
			os = new FileOutputStream( localCache );
						
			byte[] buffer = new byte[1024];
			
			int read =0;
			while( ( read = is.read(buffer ) ) != -1  )
			{	
				os.write(buffer, 0, read );								
			}		
		}
		finally
		{
			if( localCache != null )
			{
				localCache.deleteOnExit();
			}
			if( is != null )
			{
				is.close();
			}
			if( os != null )
			{
				os.flush();
				os.close();
			}			
		}
		
		return localCache.getAbsolutePath();
	}

}
