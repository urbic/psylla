package coneforest.psylla.ant;

import coneforest.clianthus.processor.ProcessingException;
import coneforest.psylla.core.*;
import coneforest.psylla.runtime.Psylla;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

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
public class PsyllaTask
	extends Task
{
	private final ArrayList<String> args=new ArrayList<>();

	private String eval, classPath, libraryPath, consoleEncoding, locale;

	private File script;

	private int timeout=0;

	public PsyllaTask()
	{
	}

	@Override
	public void execute()
		throws BuildException
	{
		if(consoleEncoding!=null)
			args.add("--console-encoding="+consoleEncoding);
		if(classPath!=null)
			args.add("--classpath="+classPath);
		if(libraryPath!=null)
			args.add("--librarypath="+libraryPath);
		if(locale!=null)
			args.add("--locale="+locale);
		if(eval!=null)
			args.add("--eval="+eval);
		if(script!=null)
			args.add(script.toString());

		try
		{
			Psylla.launch(System.out, System.err, args.toArray(String[]::new)).join(timeout);
		}
		catch(final PsyErrorException|ProcessingException|FileNotFoundException ex)
		{
			throw new BuildException(ex);
		}
		catch(final InterruptedException ex)
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
	*	@param timeout the timeout.
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
		args.add(arg.getValue());
		return arg;
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
