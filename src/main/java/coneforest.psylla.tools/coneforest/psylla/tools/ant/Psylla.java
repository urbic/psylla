package coneforest.psylla.tools.ant;

import coneforest.psylla.core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.apache.tools.ant.BuildException;

public class Psylla
	extends org.apache.tools.ant.Task
{
	@Override
	public void execute()
	{
		final var psyllaArgs=new ArrayList<String>();
		if(consoleEncoding!=null)
			psyllaArgs.add("--console-encoding="+consoleEncoding);
		if(classPath!=null)
			psyllaArgs.add("--classpath="+classPath);
		if(locale!=null)
			psyllaArgs.add("--locale="+locale);
		if(eval!=null)
			psyllaArgs.add("--eval="+eval);
		if(script!=null)
			psyllaArgs.add(script.toString());

		final String[] args=new String[psyllaArgs.size()+argList.size()];
		int i=0;
		for(final String arg: psyllaArgs)
			args[i++]=arg;
		for(final Arg argObject: argList)
		{
			final String arg=argObject.getValue();
			if(arg==null)
				throw new BuildException("\"arg\" element must have \"value\" attribute");
			args[i++]=arg;
		};

		try
		{
			coneforest.psylla.Psylla.launch(System.out, System.err, args).join(timeout);
		}
		catch(final PsyErrorException e)
		{
			// TODO
		}
		catch(final coneforest.clianthus.processor.ProcessingException e)
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
	}

	public void setEval(final String value)
	{
		eval=value;
	}

	public void setClassPath(final String value)
	{
		classPath=value;
	}

	public void setLocale(final String value)
	{
		locale=value;
	}

	public void setConsoleEncoding(final String value)
	{
		consoleEncoding=value;
	}

	public void setScript(final File value)
	{
		script=value;
	}

	public void setTimeout(final Integer timeout)
	{
		this.timeout=timeout.intValue();
	}

	public Arg createArg()
	{
		final Arg arg=new Arg();
		argList.add(arg);
		return arg;
	}

	private final ArrayList<Arg> argList=new ArrayList<>();

	private String eval, classPath, consoleEncoding, locale;

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
