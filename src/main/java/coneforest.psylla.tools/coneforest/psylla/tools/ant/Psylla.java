package coneforest.psylla.tools.ant;

import coneforest.psylla.core.*;
import coneforest.clianthus.processor.ProcessingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;

/**
*	The {@code psylla} Ant task.
*
*   <p>
*	Supported attributes:
*	<ul>
*		<li>{@code eval}</li>
*		<li>{@code script}</li>
*		<li>{@code locale}</li>
*		<li>{@code consoleEncoding}</li>
*		<li>{@code classPath}</li>
*		<li>{@code libraryPath}</li>
*		<li>{@code timeout}</li>
*	</ul>
*/
public class Psylla
	extends Task
{
	@Override
	public void execute()
		throws BuildException
	{
		final var psyllaArgs=new ArrayList<String>();
		if(consoleEncoding!=null)
			psyllaArgs.add("--console-encoding="+consoleEncoding);
		if(classPath!=null)
			psyllaArgs.add("--classpath="+classPath);
		if(libraryPath!=null)
			psyllaArgs.add("--librarypath="+libraryPath);
		if(locale!=null)
			psyllaArgs.add("--locale="+locale);
		if(eval!=null)
			psyllaArgs.add("--eval="+eval);
		if(script!=null)
			psyllaArgs.add(script.toString());

		final var args=new String[psyllaArgs.size()+argList.size()];
		int i=0;
		for(final var arg: psyllaArgs)
			args[i++]=arg;
		for(final var argObject: argList)
		{
			final var arg=argObject.getValue();
			if(arg==null)
				throw new BuildException("The \"arg\" element must have the \"value\" attribute");
			args[i++]=arg;
		};

		try
		{
			coneforest.psylla.runtime.Psylla.launch(System.out, System.err, args).join(timeout);
		}
		catch(final PsyErrorException|ProcessingException|FileNotFoundException e)
		{
			throw new BuildException(e);
		}
		catch(final InterruptedException e)
		{
			// NOP
		}
	}

	/**
	*	Set the eval string.
	*
	*	@param value the eval string.
	*/
	public void setEval(final String value)
	{
		eval=value;
	}

	/**
	*	Set the class path.
	*
	*	@param value the class path.
	*/
	public void setClassPath(final String value)
	{
		classPath=value;
	}

	/**
	*	Set the library path.
	*
	*	@param value the library path.
	*/
	public void setLibraryPath(final String value)
	{
		libraryPath=value;
	}

	/**
	*	Set the locale.
	*
	*	@param value the locale name.
	*/
	public void setLocale(final String value)
	{
		locale=value;
	}

	/**
	*	Set the console encoding.
	*
	*	@param value the console encoding.
	*/
	public void setConsoleEncoding(final String value)
	{
		consoleEncoding=value;
	}

	/**
	*	Set the script.
	*
	*	@param value the script.
	*/
	public void setScript(final File value)
	{
		script=value;
	}

	/**
	*	Set the timeout.
	*
	*	@param value the timeout.
	*/
	public void setTimeout(final Integer timeout)
	{
		this.timeout=timeout.intValue();
	}

	/**
	*	Add a command-line argument.
	*/
	public Arg createArg()
	{
		final var arg=new Arg();
		argList.add(arg);
		return arg;
	}

	private final ArrayList<Arg> argList=new ArrayList<>();

	private String eval, classPath, libraryPath, consoleEncoding, locale;

	private File script;

	private int timeout;

	public class Arg
	{
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

		private String value;
	}
}
