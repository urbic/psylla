package coneforest.psi;

public class PsiErrorDictionary extends PsiModule
{
	public PsiErrorDictionary()
	{
		super();

		try
		{
			registerOperatorClasses
				(
					_handleerror.class
				);
		}
		catch(PsiException e)
		{
			// TODO
		}
	}

	public static class _handleerror extends PsiOperator
	{
		@Override
		public void action(final Interpreter interpreter)
		{
			try
			{
				PsiDictionary errorObj=(PsiDictionary)interpreter.getSystemDictionary().psiGet("$error");
				errorObj.psiPut("newerror", PsiBoolean.FALSE);

				System.out.println("Error: "
						+errorObj.psiGet("errorname")
						+" in "
						+errorObj.psiGet("command"));

				System.out.print("Operand stack:\n\t");
				for(PsiObject obj: (PsiArray)errorObj.psiGet("ostack"))
					System.out.print(" "+obj);
				System.out.println();

				System.out.print("Execution stack:\n\t");
				for(PsiObject obj: (PsiArray)errorObj.psiGet("estack"))
					System.out.print(" "+obj);
				System.out.println();
			}
			catch(PsiException e)
			{
				throw new AssertionError();
			}
		}
	}
}
