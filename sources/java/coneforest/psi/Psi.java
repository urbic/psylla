package coneforest.psi;

class Psi
{
	public static void main(String args[])
	{
		System.out.println(new Character[]{'a', 'b', 'c'});
		Interpreter interpreter;
		try
		{
			java.io.FileInputStream is=new java.io.FileInputStream(args[0]);
			interpreter=new Interpreter(is);
			interpreter.interpret();
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println("FILE NOT FOUND");
		}
	}
}
