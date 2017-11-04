package coneforest.psylla.tools;
import static org.junit.Assert.*;

@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class PsyllaTest
	extends junit.framework.TestCase
{
	public static junit.framework.Test suite()
	{
		return new junit.framework.JUnit4TestAdapter(PsyllaTest.class);
	}

	public PsyllaTest(final String testName)
	{
		super(testName);
		this.testName=testName;
	}

	public final String testName;

	@org.junit.Test
	public void test()
		throws java.io.IOException
	{
		runTest(testName, new String[0], new String[] { "-LC" });
	}

	@org.junit.runners.Parameterized.Parameters(name="{0}")
	public static Iterable<String[]> data()
	{
		final String root=System.getProperty(coneforest.psylla.tools.ant.PsyllaUnit.class.getName()+".testName");
		final java.util.ArrayList<java.io.File> files=new java.util.ArrayList<java.io.File>();
		files.add(new java.io.File(root));
		final java.util.ArrayList<String[]> data=new java.util.ArrayList<String[]>();
		while(!files.isEmpty())
		{
			final java.io.File current=files.remove(0);
			if(current.isDirectory())
				for(java.io.File item: current.listFiles())
					files.add(item);
			else if(current.isFile())
				if(current.toString().endsWith(".t"))
					data.add(new String[] { current.toString() });
		}
		return data;
	}

	public static void runTest(final String testName, final String[] testArgs, final String[] psyllaArgs)
		throws java.io.IOException
	{
		final String[] cmdLine=new String[psyllaArgs.length+1+testArgs.length];
		for(int i=0; i<psyllaArgs.length; i++)
			cmdLine[i]=psyllaArgs[i];
		cmdLine[psyllaArgs.length]=testName;
		for(int i=0; i<testArgs.length; i++)
			cmdLine[psyllaArgs.length+1+i]=testArgs[i];

		final java.io.PrintStream out=System.out;
		final java.io.ByteArrayOutputStream outData
			=new java.io.ByteArrayOutputStream();
		System.setOut(new java.io.PrintStream(outData));

		final java.io.PrintStream err=System.err;
		final java.io.ByteArrayOutputStream errData
			=new java.io.ByteArrayOutputStream();
		System.setErr(new java.io.PrintStream(errData));

		try
		{
			coneforest.psylla.Psylla.launch(cmdLine).join();
		}
		catch(final coneforest.psylla.core.PsyException e)
		{
			// TODO
		}
		catch(final coneforest.cli.ProcessingException e)
		{
			// TODO
		}
		catch(final java.io.FileNotFoundException e)
		{
			// TODO
		}
		catch(final InterruptedException e)
		{
		}
		finally
		{
			System.setOut(out);
			System.setErr(err);
		}

		final java.io.File outFile=new java.io.File(testName+".out");
		assertEquals(outFile.exists()? slurp(outFile): "", outData.toString());

		final java.io.File errFile=new java.io.File(testName+".err");
		assertEquals(errFile.exists()? slurp(errFile): "", errData.toString());
	}

	private static String slurp(final java.io.File file)
		throws java.io.IOException
	{
		final byte[] slurpBuffer=new byte[(int)file.length()];
		new java.io.FileInputStream(file).read(slurpBuffer);
		return new String(slurpBuffer);
	}

	public static void runTest(final String testName, final String[] testArgs)
		throws java.io.IOException
	{
		runTest(testName, testArgs, new String[0]);
	}

	public static void runTest(final String testName)
		throws java.io.IOException
	{
		runTest(testName, new String[0]);
	}

}
