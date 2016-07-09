package coneforest.psi.tools.ant;
import coneforest.psi.*;

public class PsyllaUnit
	extends org.apache.tools.ant.taskdefs.optional.junit.JUnitTask
{
	public PsyllaUnit()
		throws Exception
	{
	}

	@Override
	public void execute()
	{
		for(Test t: testList)
		{
			final org.apache.tools.ant.taskdefs.optional.junit.JUnitTest test
				=new org.apache.tools.ant.taskdefs.optional.junit.JUnitTest(coneforest.psi.tools.PsyllaTest.class.getName());
			System.setProperty(PsyllaUnit.class.getName()+".testName", t.getName());
			super.execute(test);
		}
	}

	public Test createTest()
	{
		final Test test=new Test();
		testList.add(test);
		return test;
	}

	private final java.util.ArrayList<Test> testList
		=new java.util.ArrayList<Test>();

	public class Test
	{
		public Test()
		{
		}

		public void setName(final String name)
		{
			this.name=name;
		}

		public String getName()
		{
			return name;
		}

		private String name;
	}
}
