package coneforest.psylla.tools.ant;

import coneforest.psylla.runtime.*;
import java.util.ArrayList;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

/**
*	The {@code psyllaunit} Ant task.
*/
public class PsyllaUnit
	extends JUnitTask
{
	public PsyllaUnit()
		throws Exception
	{
	}

	@Override
	public void execute()
		throws BuildException
	{
		final var args=new String[this.args.size()];
		int i=0;
		for(final Arg a: this.args)
			args[i++]=a.getValue();
		final String argsEncoded=Base64Codec.encode(args);
		for(final Test t: tests)
		{
			final var test=new JUnitTest(
					PsyllaTest.class.getName(),
					haltOnError,
					haltOnFail,
					true);
			System.setProperty(PsyllaUnit.class.getName()+".testName", t.getName());
			System.setProperty(PsyllaUnit.class.getName()+".psyllaArgs", argsEncoded);
			execute(test);
		}
	}

	public Test createTest()
	{
		final var test=new Test();
		tests.add(test);
		return test;
	}

	public Arg createArg()
	{
		final var arg=new Arg();
		args.add(arg);
		return arg;
	}

	@Override
	public void setHaltonerror(final boolean value)
	{
		haltOnError=value;
		super.setHaltonerror(value);
	}

	@Override
	public void setHaltonfailure(final boolean value)
	{
		haltOnFail=value;
		super.setHaltonfailure(value);
	}

	private boolean haltOnError=false;
	private boolean haltOnFail=false;

	private final static ArrayList<Test> tests=new ArrayList<>();

	private final static ArrayList<Arg> args=new ArrayList<>();

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
