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
	
	String oneDir = "src/test/data/one/";
	String twoDir = "src/test/data/two/";
	String oneFile = "onecontents.properties";
	String twoFile = "twocontents.properties";
	String commonFile = "contents.properties";
	
	
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

}
