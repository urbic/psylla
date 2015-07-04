package coneforest.psi;

public class PsiErrorDict extends PsiModule
{
	public PsiErrorDict()
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
			throws ClassCastException, PsiException
		{
			PsiDict errorObj=(PsiDict)interpreter.getSystemDict().get("$error");
			errorObj.put("newerror", PsiBoolean.FALSE);

			System.out.println("Error: "
					+errorObj.get("errorname")
					+" in "
					+errorObj.get("command"));

			System.out.print("Operand stack:\n\t");
			for(PsiObject obj: (PsiArray)errorObj.get("ostack"))
				System.out.print(" "+obj);
			System.out.println();

			System.out.print("Execution stack:\n\t");
			for(PsiObject obj: (PsiArray)errorObj.get("estack"))
				System.out.print(" "+obj);
			System.out.println();

			interpreter.quit();
		}
	}
}