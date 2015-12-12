package se.ck.run.loaders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LibraryTest {

	Library instance = null;
	
	@Before
	public void setup()
	{
		instance = new Library();
	}
	
	@After
	public void tearDown()
	{
		instance = null;
	}
	
	@Test
	public void getUrls() 
	{
		List<URL> expected = new ArrayList<URL>();
		List<URL> actual = instance.getUrls();
		assertThat( actual, is( not( nullValue() ) ) );
		assertThat( actual, is( equalTo( expected ) ) );
	}
	
	@Test	
	public void getUrlsAsArray()
	{
		URL[] expected = new URL[0];
		URL[] actual = instance.getUrlsAsArray();
		assertThat( actual, is( not( nullValue() ) ) );
		assertThat( actual, is( equalTo( expected ) ) );
	}
	
	@Test
	public void add( ) throws MalformedURLException
	{
		URL expected = new URL( "file:///." );		
		instance.add( expected );
		List<URL> actual = instance.getUrls();
		assertThat( actual.size(), is( equalTo( 1 ) ) );
		assertThat( actual, contains( expected ) );		
		
		URL[] actualArray = instance.getUrlsAsArray();
		assertThat( actualArray.length, is( equalTo( 1 ) ) );
		assertThat( actualArray[0], is( equalTo( expected ) ) );
	}
	
	@Test
	public void size() throws MalformedURLException
	{
		for( int i=0; i<10; i++ )
		{
			assertThat( instance.size(), is( equalTo( i ) ) );
			instance.add( new URL( "file:///." ) );
		}
		assertThat( instance.size(), is( equalTo( 10 ) ) );		
	}
	
	@Test
	public void clear() throws MalformedURLException
	{
		for( int i=0; i<10; i++ )
		{			
			instance.add( new URL( "file:///." ) );
		}
		assertThat( instance.size(), is( equalTo( 10 ) ) );
		instance.clear();
		assertThat( instance.size(), is( equalTo( 0 ) ) );
	}
	
	@Test
	public void addFile( ) throws MalformedURLException
	{
		File f = new File( "src" );
		URL expected = f.toURI().toURL();
		instance.add( f );
		
		List<URL> actual = instance.getUrls();
		assertThat( actual, is( not( nullValue() ) ) );
		assertThat( actual.size(), is( equalTo( 1 ) ) );
		assertThat( actual, contains( expected ) );		
	}
	
	@Test
	public void addPath( ) throws MalformedURLException
	{
		String item = "src";
		File f = new File( item );
		URL expected = f.toURI().toURL();
		instance.add( item );
		
		List<URL> actual = instance.getUrls();
		assertThat( actual, is( not( nullValue() ) ) );
		assertThat( actual.size(), is( equalTo( 1 ) ) );
		assertThat( actual, contains( expected ) );
	}

}
