package coneforest.psylla.core;
import coneforest.psylla.*;
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
					throw new PsyTypeCheckException();
			}

			if(oDict.known("directory"))
				pb.directory(new java.io.File(((PsyTextual)oDict.get("directory")).stringValue()));

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
		catch(final java.io.IOException ex)
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
		return new PsyReader(new java.io.BufferedReader(
				new java.io.InputStreamReader(process.getInputStream())));
	}

	public PsyReader psyProcessError()
	{
		return new PsyReader(new java.io.BufferedReader(
				new java.io.InputStreamReader(process.getErrorStream())));
	}

	public PsyWriter psyProcessWriter()
	{
		return new PsyWriter(new java.io.BufferedWriter(
				new java.io.OutputStreamWriter(process.getOutputStream())));
	}

	public PsyInteger psyStatus()
		throws PsyErrorException
	{
		try
		{
			return PsyInteger.valueOf(process.exitValue());
		}
		catch(final IllegalThreadStateException ex)
		{
			throw new PsyInvalidStateException();
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
