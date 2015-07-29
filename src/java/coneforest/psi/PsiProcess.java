package coneforest.psi;

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
					pb=new ProcessBuilder(((PsiStringy)command).getString());
				else if(command instanceof PsiArraylike)
				{
					java.util.ArrayList<String> commandList
						=new java.util.ArrayList(((PsiArraylike)command).length());
					for(PsiObject obj: (PsiArraylike<PsiObject>)command)
						commandList.add(((PsiStringy)obj).getString());
					pb=new ProcessBuilder(commandList);
				}
				else throw new PsiException("typecheck");
			}
			
			if(dictlike.known("directory"))
				pb.directory(new java.io.File(((PsiStringy)dictlike.get("dictlike")).getString()));

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
		catch(ClassCastException e)
		{
			throw new PsiException("typecheck");
		}
		catch(SecurityException e)
		{
			throw new PsiException("securityerror");
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
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
		catch(IllegalThreadStateException e)
		{
			throw new PsiException("invalidstate");
		}
	}

	@Override
	public String getTypeName()
	{
		return "process";
	}

	private Process process;
}
