package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

@Type("process")
public class PsyProcess
	implements PsyObject
{
	public PsyProcess(final PsyFormalDict oDict)
		throws PsyErrorException
	{
		try
		{
			ProcessBuilder pb=null;

			// TODO: empty command name
			if(oDict.known("command"))
			{
				final var oCommand=oDict.get("command");
				if(oCommand instanceof PsyTextual)
					pb=new ProcessBuilder(((PsyTextual)oCommand).stringValue());
				else if(oCommand instanceof PsyFormalArray)
				{
					final var commandList=new ArrayList<String>(((PsyFormalArray)oCommand).length());
					for(final var o: (PsyFormalArray<PsyObject>)oCommand)
						commandList.add(((PsyTextual)o).stringValue());
					pb=new ProcessBuilder(commandList);
				}
				else
					throw new PsyTypeCheckException();
			}

			if(oDict.known("directory"))
				pb.directory(new File(((PsyTextual)oDict.get("directory")).stringValue()));

			if(oDict.known("environment"))
			{
				final var oEnv=(PsyFormalDict<PsyTextual>)oDict.get("environment");
				final var env=pb.environment();
				env.clear();
				for(final var oKey: oEnv.psyKeys().stream().toArray(PsyTextual[]::new))
					env.put(oKey.stringValue(), oEnv.psyGet(oKey).stringValue());
			}

			if(oDict.known("input")
					&& ((PsyBoolean)oDict.get("input")).booleanValue())
				pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

			if(oDict.known("output")
					&& ((PsyBoolean)oDict.get("output")).booleanValue())
				pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			if(oDict.known("error")
					&& ((PsyBoolean)oDict.get("error")).booleanValue())
				pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			process=pb.start();
		}
		catch(final NullPointerException|IndexOutOfBoundsException ex)
		{
			throw new PsyUndefinedException();
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheckException();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityErrorException();
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
		catch(final PsyErrorException e)
		{
			throw e;
		}
	}

	public PsyReader psyProcessReader()
	{
		return new PsyReader(new BufferedReader(
				new InputStreamReader(process.getInputStream())));
	}

	public PsyReader psyProcessError()
	{
		return new PsyReader(new BufferedReader(
				new InputStreamReader(process.getErrorStream())));
	}

	public PsyWriter psyProcessWriter()
	{
		return new PsyWriter(new BufferedWriter(
				new OutputStreamWriter(process.getOutputStream())));
	}

	public PsyInteger psyStatus()
		throws PsyInvalidStateException
	{
		try
		{
			return PsyInteger.of(process.exitValue());
		}
		catch(final IllegalThreadStateException ex)
		{
			throw new PsyInvalidStateException();
		}
	}

	private Process process;

	/**
	*	Context action of the {@code process} operator.
	*/
	@OperatorType("process")
	public static final ContextAction PSY_PROCESS
		=ContextAction.<PsyFormalDict>ofFunction(PsyProcess::new);

	/**
	*	Context action of the {@code processerror} operator.
	*/
	@OperatorType("processerror")
	public static final ContextAction PSY_PROCESSERROR
		=ContextAction.<PsyProcess>ofFunction(PsyProcess::psyProcessError);

	/**
	*	Context action of the {@code processreader} operator.
	*/
	@OperatorType("processreader")
	public static final ContextAction PSY_PROCESSREADER
		=ContextAction.<PsyProcess>ofFunction(PsyProcess::psyProcessReader);

	/**
	*	Context action of the {@code processwriter} operator.
	*/
	@OperatorType("processwriter")
	public static final ContextAction PSY_PROCESSWRITER
		=ContextAction.<PsyProcess>ofFunction(PsyProcess::psyProcessWriter);

	/**
	*	Context action of the {@code status} operator.
	*/
	@OperatorType("status")
	public static final ContextAction PSY_STATUS
		=ContextAction.<PsyProcess>ofFunction(PsyProcess::psyStatus);
}
