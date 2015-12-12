package se.ck.run.loaders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoreClassLoaderTest {

	CoreClassLoader instance, instance1, instance2 = null;
	
	Library library = null;
	URL one = null;
	URL two = null;
	String oneDir = "src/test/data/one/";
	String twoDir = "src/test/data/two/";
	String oneFile = "onecontents.properties";
	String twoFile = "twocontents.properties";
	String commonFile = "contents.properties";
	
	
	@Before
	public void setup() throws MalformedURLException
	{
		library = new Library();
		one = genURL( oneDir );
		two = genURL( twoDir );
	}
	
	@After
	public void tearDown() throws IOException
	{
		if( instance != null ){instance.close();}
		if( instance1 != null ){instance1.close();}
		if( instance2 != null ){instance2.close();}
		instance = instance1 = instance2 = null;
		library = null;
		one = two = null;		
	}
	
	private URL genURL( String dir ) throws MalformedURLException
	{
		File oneDir = new File( dir );
		assertThat( oneDir.exists(), is( equalTo( true ) ) );
		return oneDir.toURI().toURL();		
	}	
	
	@Test
	public void one()
	{		
		library.add( one );		
		instance = new CoreClassLoader( library );
		assertResource( oneFile, true );
		assertResource( twoFile, false );
		assertResource( commonFile, true );
	}
	
	@Test
	public void two()
	{		
		library.add( two );		
		instance = new CoreClassLoader( library );
		assertResource( oneFile, false );
		assertResource( twoFile, true );
		assertResource( commonFile, true );
	}
	
	@Test
	public void readProperties() throws IOException
	{
		Library library1 = new Library();
		library1.add( one );
		instance1 = new CoreClassLoader( library1 );
		
		Library library2 = new Library();
		library2.add( two );
		instance2 = new CoreClassLoader( library2 );
		
		Properties p1 = new Properties();
		p1.load( instance1.getResourceAsStream( commonFile ) );
		
		Properties p2 = new Properties();
		p2.load( instance2.getResourceAsStream( commonFile ) );
		
		assertThat( p1.getProperty( "value" ), is( equalTo( "one" ) ) );
		assertThat( p2.getProperty( "value" ), is( equalTo( "two" ) ) );
	}
	
	private void assertResource( String res, boolean exists )
	{
		URL actual = instance.getResource( res );
		assertThat( actual != null, is( equalTo( exists ) ) );
	}

}
