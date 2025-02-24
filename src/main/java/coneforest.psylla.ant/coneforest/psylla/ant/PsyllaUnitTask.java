package coneforest.psylla.ant;

import coneforest.psylla.runtime.*;
import java.io.File;
import java.util.ArrayList;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.BaseTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.types.resources.Resources;

/**
*	The {@code psyllaunit} Ant task.
*/
public class PsyllaUnitTask
	extends JUnitTask
{
	private boolean haltOnError=false;
	private boolean haltOnFail=false;

	private final ArrayList<Test> tests=new ArrayList<>();
	private final ArrayList<Arg> args=new ArrayList<>();

	public PsyllaUnitTask()
		throws Exception
	{
	}

	@Override
	public void execute()
		throws BuildException
	{
		setFork(true);
		//System.out.println("FORK: "+getFork());
		final var args=new String[this.args.size()];
		int i=0;
		for(final var a: this.args)
			args[i++]=a.getValue();
		for(final var t: tests)
		{
			final var test=new JUnitTest(
					PsyllaTest.class.getName(),
					haltOnError,
					haltOnFail,
					true);
			test.setTodir(new File(t.getTodir()));
			final var testFiles
				=t.getResources().stream()
						.filter(f->f instanceof FileResource)
						.map(f->((FileResource)f).getFile().toString())
						.toArray(String[]::new);
			System.setProperty(PsyllaUnitTask.class.getName()+".psyllaArgs",
					Base64Codec.encode(args));
			System.setProperty(PsyllaUnitTask.class.getName()+".testFiles",
					Base64Codec.encode(testFiles));
			test.setFork(true);
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

	public class Test
		extends BaseTest
	{
		private Resources resources=new Resources();
		private String name;

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

		public void addConfiguredFileSet(final FileSet fileSet)
		{
			add(fileSet);
			if(fileSet.getProject()==null)
				fileSet.setProject(getProject());
		}

		public void add(final ResourceCollection rc)
		{
			resources.add(rc);
		}

		public Resources getResources()
		{
			return resources;
		}
	}

	public class Arg
	{
		private String value;

		public Arg()
		{
		}

		public void setValue(final String value)
		{
			this.value=value;
		}

		public String getValue()
		{
			return value;
		}
	}
}
