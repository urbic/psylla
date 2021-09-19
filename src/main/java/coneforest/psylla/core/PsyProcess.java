package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("process")
public class PsyProcess
	implements PsyObject
{
	public PsyProcess(final PsyFormalDict oDict)
		throws PsyException
	{
		try
		{
			ProcessBuilder pb=null;

			// TODO: empty command name
			if(oDict.known("command"))
			{
				final var command=oDict.get("command");
				if(command instanceof PsyTextual)
					pb=new ProcessBuilder(((PsyTextual)command).stringValue());
				else if(command instanceof PsyFormalArray)
				{
					java.util.ArrayList<String> commandList
						=new java.util.ArrayList(((PsyFormalArray)command).length());
					for(final var o: (PsyFormalArray<PsyObject>)command)
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
				// TODO
				//bp.environment();
			}
			//PsyDict environment=(PsyDict)oDict.get("environment");

			if(oDict.known("inheritinput")
					&& ((PsyBoolean)oDict.get("inheritinput")).booleanValue())
				pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

			if(oDict.known("inheritoutput")
					&& ((PsyBoolean)oDict.get("inheritoutput")).booleanValue())
				pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			process=pb.start();

		}
		catch(final ClassCastException e)
		{
			throw new PsyTypeCheckException();
		}
		catch(final SecurityException e)
		{
			throw new PsySecurityErrorException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
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
		throws PsyException
	{
		try
		{
			return PsyInteger.valueOf(process.exitValue());
		}
		catch(final IllegalThreadStateException e)
		{
			throw new PsyInvalidStateException();
		}
	}

	private Process process;

}
