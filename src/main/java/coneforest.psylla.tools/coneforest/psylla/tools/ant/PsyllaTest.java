package coneforest.psylla.tools.ant;

import static org.junit.Assert.*;

import coneforest.clianthus.processor.ProcessingException;
import coneforest.psylla.core.errors.PsyError;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

@RunWith(Parameterized.class)
public class PsyllaTest
{

	public PsyllaTest(final String testName)
	{
		this.testName=testName;
	}

	public final String testName;

	@Test
	public void test()
		throws java.io.IOException
	{
		final var args=(String [])Base64Codec.decode(
				System.getProperty(PsyllaUnit.class.getName()+".psyllaArgs"));
		runTest(testName, new String[0], args);
	}

	@Parameterized.Parameters(name="{0}")
	public static Iterable<String[]> data()
	{
		final var root=System.getProperty(PsyllaUnit.class.getName()+".testName");
		final var files=new ArrayList<File>();
		files.add(new File(root));
		final var data=new ArrayList<String[]>();
		while(!files.isEmpty())
		{
			final var current=files.remove(0);
			if(current.isDirectory())
				for(final var item: current.listFiles())
					files.add(item);
			else if(current.isFile())
				if(current.toString().endsWith(".t"))
					data.add(new String[] { current.toString() });
		}
		data.sort((item1, item2)->item1[0].compareTo(item2[0]));
		return data;
	}

	public static void runTest(final String testName, final String[] testArgs, final String[] psyllaArgs)
		throws java.io.IOException
	{
		final var cmdLine=new String[psyllaArgs.length+1+testArgs.length];
		for(int i=0; i<psyllaArgs.length; i++)
			cmdLine[i]=psyllaArgs[i];
		cmdLine[psyllaArgs.length]=testName;
		for(int i=0; i<testArgs.length; i++)
			cmdLine[psyllaArgs.length+1+i]=testArgs[i];

		final var out=System.out;
		final var outData=new ByteArrayOutputStream();
		System.setOut(new PrintStream(outData));

		final var err=System.err;
		final var errData=new ByteArrayOutputStream();
		System.setErr(new PrintStream(errData));

		try
		{
			coneforest.psylla.Psylla.launch(
					System.out, System.err, cmdLine).join();
		}
		catch(final PsyError e)
		{
			// TODO
		}
		catch(final ProcessingException e)
		{
			// TODO
		}
		catch(final FileNotFoundException e)
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

		final var outFile=new File(testName+".out");
		assertEquals(outFile.exists()? slurp(outFile): "", outData.toString());

		final var errFile=new File(testName+".err");
		assertEquals(errFile.exists()? slurp(errFile): "", errData.toString());
	}

	private static String slurp(final File file)
		throws java.io.IOException
	{
		final var slurpBuffer=new byte[(int)file.length()];
		new FileInputStream(file).read(slurpBuffer);
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
