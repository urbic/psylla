package coneforest.psi.errordict;
import coneforest.psi.*;

public class _handleerror extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
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

		/*
		System.out.print("Operand stack:\n\t");
		for(PsiObject obj: opstack)
			System.out.print(" "+obj);
		System.out.println();

		System.out.print("Execution stack:\n\t");
		for(PsiObject obj: execstack)
			System.out.print(" "+obj);
		System.out.println();
		*/

		/*
		System.out.println("Dictionary stack:");
		System.out.print("⊢\t");
		for(PsiObject obj: dictstack)
			System.out.print(" "+obj);
		System.out.println();
		*/

		/*
		System.out.print("Loop level stack:\n\t");
		for(int item: loopstack)
			System.out.print(" "+item);
		System.out.println();
		*/

	}
}
