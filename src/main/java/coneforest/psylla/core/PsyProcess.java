package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("process")
public class PsyProcess
	implements PsyObject
{
	public PsyProcess(final PsyDictlike oDictlike)
		throws PsyException
	{
		try
		{
			ProcessBuilder pb=null;

			// TODO: empty command name
			if(oDictlike.known("command"))
			{
				final var command=oDictlike.get("command");
				if(command instanceof PsyStringy)
					pb=new ProcessBuilder(((PsyStringy)command).stringValue());
				else if(command instanceof PsyArraylike)
				{
					java.util.ArrayList<String> commandList
						=new java.util.ArrayList(((PsyArraylike)command).length());
					for(final var o: (PsyArraylike<PsyObject>)command)
						commandList.add(((PsyStringy)o).stringValue());
					pb=new ProcessBuilder(commandList);
				}
				else
					throw new PsyTypeCheckException();
			}

			if(oDictlike.known("directory"))
				pb.directory(new java.io.File(((PsyStringy)oDictlike.get("directory")).stringValue()));

			if(oDictlike.known("environment"))
			{
				// TODO
				//bp.environment();
			}
			//PsyDictlike environment=(PsyDictlike)oDictlike.get("environment");

			if(oDictlike.known("inheritinput")
					&& ((PsyBoolean)oDictlike.get("inheritinput")).booleanValue())
				pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

			if(oDictlike.known("inheritoutput")
					&& ((PsyBoolean)oDictlike.get("inheritoutput")).booleanValue())
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
