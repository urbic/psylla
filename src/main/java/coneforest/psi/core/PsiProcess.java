package coneforest.psi.core;

@coneforest.psi.Type("process")
public class PsiProcess
	implements PsiObject
{
	public PsiProcess(PsiDictlike dictlike)
		throws PsiException
	{
		try
		{
			ProcessBuilder pb=null;

			// TODO: empty command name
			if(dictlike.known("command"))
			{
				PsiObject command=dictlike.get("command");
				if(command instanceof PsiStringy)
					pb=new ProcessBuilder(((PsiStringy)command).stringValue());
				else if(command instanceof PsiArraylike)
				{
					java.util.ArrayList<String> commandList
						=new java.util.ArrayList(((PsiArraylike)command).length());
					for(PsiObject obj: (PsiArraylike<PsiObject>)command)
						commandList.add(((PsiStringy)obj).stringValue());
					pb=new ProcessBuilder(commandList);
				}
				else
					throw new PsiTypeCheckException();
			}

			if(dictlike.known("directory"))
				pb.directory(new java.io.File(((PsiStringy)dictlike.get("dictlike")).stringValue()));

			if(dictlike.known("environment"))
			{
				// TODO
				//bp.environment();
			}
			//PsiDictlike environment=(PsiDictlike)dictlike.get("environment");

			if(dictlike.known("inheritinput")
					&& ((PsiBoolean)dictlike.get("inheritinput")).booleanValue())
				pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

			if(dictlike.known("inheritoutput")
					&& ((PsiBoolean)dictlike.get("inheritoutput")).booleanValue())
				pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			process=pb.start();

		}
		catch(final ClassCastException e)
		{
			throw new PsiTypeCheckException();
		}
		catch(final SecurityException e)
		{
			throw new PsiSecurityErrorException();
		}
		catch(final java.io.IOException e)
		{
			throw new PsiIOErrorException();
		}
	}

	public PsiReader psiProcessReader()
	{
		return new PsiReader(new java.io.BufferedReader(
				new java.io.InputStreamReader(process.getInputStream())));
	}

	public PsiReader psiProcessError()
	{
		return new PsiReader(new java.io.BufferedReader(
				new java.io.InputStreamReader(process.getErrorStream())));
	}

	public PsiWriter psiProcessWriter()
	{
		return new PsiWriter(new java.io.BufferedWriter(
				new java.io.OutputStreamWriter(process.getOutputStream())));
	}

	public PsiInteger psiStatus()
		throws PsiException
	{
		try
		{
			return PsiInteger.valueOf(process.exitValue());
		}
		catch(final IllegalThreadStateException e)
		{
			throw new PsiInvalidStateException();
		}
	}

	private Process process;
}
