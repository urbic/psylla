package coneforest.psylla.ant;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

import coneforest.clianthus.processor.ProcessingException;
import coneforest.psylla.core.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PsyllaTest
{
	private final String testName;

	public PsyllaTest(final String testName)
	{
		this.testName=testName;
	}

	@Test
	public void test()
		throws IOException
	{
		final var args=(String[])Base64Codec.decode(
				System.getProperty(PsyllaUnitTask.class.getName()+".psyllaArgs"));
		runTest(testName, new String[0], args);
	}

	@Parameterized.Parameters(name="{0}")
	public static Iterable<String[]> data()
	{
		final var data=new ArrayList<String[]>();
		for(var s: (String[])Base64Codec.decode(
				System.getProperty(PsyllaUnitTask.class.getName()+".testFiles")))
			data.add(new String[] { new File(s).getAbsoluteFile().toString() });
		data.sort((item1, item2)->item1[0].compareTo(item2[0]));
		return data;
	}

	private static void runTest(final String testName, final String[] testArgs, final String[] psyllaArgs)
		throws IOException
	{
		final var cmdLine=new String[psyllaArgs.length+1+testArgs.length];
		for(int i=0; i<psyllaArgs.length; i++)
			cmdLine[i]=psyllaArgs[i];
		cmdLine[psyllaArgs.length]=testName;
		for(int i=0; i<testArgs.length; i++)
			cmdLine[psyllaArgs.length+1+i]=testArgs[i];

		final var out=System.out;
		final var outData=new ByteArrayOutputStream();
		System.setOut(new PrintStream(outData, false, UTF_8));

		final var err=System.err;
		final var errData=new ByteArrayOutputStream();
		System.setErr(new PrintStream(errData, false, UTF_8));

		try
		{
			coneforest.psylla.runtime.Psylla.launch(System.out, System.err, cmdLine).join();
		}
		catch(final PsyErrorException e)
		{
			// TODO
		}
		catch(final ProcessingException ex)
		{
			// TODO
		}
		catch(final InterruptedException ex)
		{
			// TODO
		}
		finally
		{
			System.setOut(out);
			System.setErr(err);
		}

		final var outFile=new File(testName+".out").getAbsoluteFile();
		assertEquals(outFile.exists()? slurp(outFile): "", outData.toString(UTF_8));

		final var errFile=new File(testName+".err").getAbsoluteFile();
		assertEquals(errFile.exists()? slurp(errFile): "", errData.toString(UTF_8));
	}

	private static void runTest(final String testName, final String[] testArgs)
		throws IOException
	{
		runTest(testName, testArgs, new String[0]);
	}

	private static void runTest(final String testName)
		throws IOException
	{
		runTest(testName, new String[0]);
	}

	private static String slurp(final File file)
		throws IOException
	{
		try(final var fis=new FileInputStream(file))
		{
			return new String(fis.readAllBytes(), UTF_8);
		}
	}
}
