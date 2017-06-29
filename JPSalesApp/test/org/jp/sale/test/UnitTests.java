package org.jp.sale.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(IntegratedTests.class)
public class UnitTests {

	@BeforeClass
	public static void init()	{
	}//End Of Method
	
	
	@After
	@Before
	public void newTestLog()	{
		System.out.println();
	}//End Of Method	
	
	
	@Test(timeout=1100)
	public void equalTest()			{
		
		int dtmf = 5;
		Assert.assertEquals(3,dtmf);
			
	}//End Of Method
	
	
}
