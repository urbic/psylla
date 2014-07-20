package coneforest.psi;

class Psi
{
	public static void main(String args[])
	{
		try
		{
			Interpreter interpreter=new Interpreter();
			interpreter.acceptShellArguments(args);
			interpreter.interpret(new PsiFileReader(args[0]));
		}
		catch(PsiException e)
		{
			System.out.println("ERROR: "+e.kind());
		}
	}
}
