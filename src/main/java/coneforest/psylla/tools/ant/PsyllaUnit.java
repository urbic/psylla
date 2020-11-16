package coneforest.psylla.tools.ant;
import coneforest.psylla.*;

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
		final String[] args=new String[this.args.size()];
		int i=0;
		for(final Arg a: this.args)
			args[i++]=a.getValue();
		final String argsEncoded=coneforest.psylla.tools.ant.Base64Codec.encode(args);
		for(final Test t: tests)
		{
			final org.apache.tools.ant.taskdefs.optional.junit.JUnitTest test
				=new org.apache.tools.ant.taskdefs.optional.junit.JUnitTest(coneforest.psylla.tools.PsyllaTest.class.getName());
			System.setProperty(PsyllaUnit.class.getName()+".testName", t.getName());
			System.setProperty(PsyllaUnit.class.getName()+".psyllaArgs", argsEncoded);
			super.execute(test);
		}
	}

	public Test createTest()
	{
		final Test test=new Test();
		tests.add(test);
		return test;
	}

	public Arg createArg()
	{
		final Arg arg=new Arg();
		args.add(arg);
		return arg;
	}

	private final static java.util.ArrayList<Test> tests
		=new java.util.ArrayList<Test>();

	private final static java.util.ArrayList<Arg> args
		=new java.util.ArrayList<Arg>();

	public class Test
	{
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

	public class Arg
	{
		public void setValue(final String value)
		{
			this.value=value;
		}

		public String getValue()
		{
			return value;
		}

		private String value;
	}
}
