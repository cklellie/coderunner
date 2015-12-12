package se.ck.run.loaders.url;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CoreUrlClassLoaderFactoryTest {

	CoreUrlClassLoaderFactory instance = null;
	
	@Before
	public void setup()
	{
		instance = new CoreUrlClassLoaderFactory();
	}
	
	@After
	public void tearDown()
	{
		instance = null;
	}
	
	@Test
	public void create() 
	{				
		assertThat( instance.createURLStreamHandler( "http" ), is( not( nullValue() ) ) );
		assertThat( instance.createURLStreamHandler( "https" ), is( not( nullValue() ) ) );
		assertThat( instance.createURLStreamHandler( "HtTp" ), is( not( nullValue() ) ) );
		assertThat( instance.createURLStreamHandler( "hTtPs" ), is( not( nullValue() ) ) );
		assertThat( instance.createURLStreamHandler( "file" ), is(  nullValue() ) );
		
	}		
}
