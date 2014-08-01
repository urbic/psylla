package coneforest.psi;

public class Psi
{
	public static void main(String args[])
	{
		Interpreter interpreter=new Interpreter();
		interpreter.acceptShellArguments(args);
		//System.out.println(System.getenv().getClass());
		interpreter.acceptEnvironment(System.getenv());
		try
		{
			interpreter.setReader(new java.io.InputStreamReader(System.in));
			interpreter.setWriter(new java.io.OutputStreamWriter(System.out));

			interpreter.interpret(new java.io.FileReader(args[0]));
			//interpreter.pushSourceReader(new java.io.FileReader(args[0]));
			//interpreter.interpret();
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println("FILE NOT FOUND");
		}
		//catch(PsiException e)
		//{
		//	System.out.println("ERROR: "+e.kind());
		//}
		/*
		try
		{
			coneforest.xopt.OptionsProcessor xopt
				=new coneforest.xopt.OptionsProcessor(Psi.class.getResource("psi.xopt"));
			xopt.parse(args);

			Interpreter interpreter=new Interpreter();


			String[] freeArgs=xopt.getFreeArgs();
			interpreter.acceptShellArguments(freeArgs);
			if(freeArgs.length>0)
				for(String fileName: freeArgs)
				{
					if(fileName.equals("-"))
					{
						System.out.println("STDIN");
						interpreter.interpret(new PsiReader(System.in));
					}
					else
						interpreter.interpret(new PsiFileReader(fileName));
				}
		}
		catch(coneforest.xopt.ConfigurationException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		catch(coneforest.xopt.ProcessingException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		catch(PsiException e)
		{
			System.out.println("ERROR: "+e.kind());
		}
		*/
	}
}
