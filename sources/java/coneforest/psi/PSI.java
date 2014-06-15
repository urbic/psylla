package coneforest.psi;

class PSI
{
	public static void main(String args[])
	{
		PSIInterpreter interpreter;
		try
		{
			java.io.FileInputStream is=new java.io.FileInputStream(args[0]);
			interpreter=new PSIInterpreter(is);
			interpreter.interpret();
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println("FILE NOT FOUND");
		}
	}
}
