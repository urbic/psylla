package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyIOError;
import coneforest.psylla.core.errors.PsyInvalidState;
import coneforest.psylla.core.errors.PsySecurityError;
import coneforest.psylla.core.errors.PsyTypeCheck;
import coneforest.psylla.core.errors.PsyUndefined;
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
		throws PsyError
	{
		try
		{
			ProcessBuilder pb=null;

			// TODO: empty command name
			if(oDict.known("command"))
			{
				//System.out.println("PROCESS");
				final var oCommand=oDict.get("command");
				if(oCommand instanceof PsyTextual)
					pb=new ProcessBuilder(((PsyTextual)oCommand).stringValue());
				else if(oCommand instanceof PsyFormalArray)
				{
					final var commandList
						=new ArrayList<String>(((PsyFormalArray)oCommand).length());
					for(final var o: (PsyFormalArray<PsyObject>)oCommand)
						commandList.add(((PsyTextual)o).stringValue());
					pb=new ProcessBuilder(commandList);
				}
				else
					throw new PsyTypeCheck();
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
			throw new PsyUndefined();
		}
		catch(final ClassCastException ex)
		{
			throw new PsyTypeCheck();
		}
		catch(final SecurityException ex)
		{
			throw new PsySecurityError();
		}
		catch(final IOException ex)
		{
			throw new PsyIOError();
		}
		catch(final PsyError e)
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
		throws PsyInvalidState
	{
		try
		{
			return PsyInteger.of(process.exitValue());
		}
		catch(final IllegalThreadStateException ex)
		{
			throw new PsyInvalidState();
		}
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyFormalDict>("process", PsyProcess::new),
			new PsyOperator.Arity11<PsyProcess>("processerror", PsyProcess::psyProcessError),
			new PsyOperator.Arity11<PsyProcess>("processreader", PsyProcess::psyProcessReader),
			new PsyOperator.Arity11<PsyProcess>("processwriter", PsyProcess::psyProcessWriter),
			new PsyOperator.Arity11<PsyProcess>("status", PsyProcess::psyStatus),
		};

	private Process process;

}
