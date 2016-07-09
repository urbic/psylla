package coneforest.psi.core;
import coneforest.psi.*;

public class PsiErrorDict
	extends PsiModule
{
	public PsiErrorDict()
		throws PsiException
	{
		registerOperators(OP_HANDLEERROR);
	}

	public static final PsiOperator OP_HANDLEERROR
		=new PsiOperator("handleerror")
			{
				@Override
				public void action(final Interpreter interpreter)
					throws ClassCastException, PsiException
				{
					final PsiDictlike errorObj=(PsiDictlike)interpreter.systemDict().get("$error");
					errorObj.put("newerror", PsiBoolean.FALSE);

					System.err.println(Messages.format("handleErrorMessage",
							errorObj.get("errorname").toSyntaxString(),
							errorObj.get("emitter").toSyntaxString()));

					System.err.print(Messages.getString("handleErrorMessageOStack"));
					final PsiArray ostack=(PsiArray)errorObj.get("ostack");
					if(ostack.length()!=0)
					{
						System.err.print("\n\t");
						for(PsiObject obj: ostack)
							System.err.print(" "+obj.toSyntaxString());
						System.err.println();
					}
					else
						System.err.println(" "+Messages.getString("handleErrorMessageEmpty"));

					System.err.print(Messages.getString("handleErrorMessageEStack"));
					final PsiArray estack=(PsiArray)errorObj.get("estack");
					if(estack.length()!=0)
					{
						System.err.print("\n\t");
						for(PsiObject obj: estack)
							System.err.print(" "+obj.toSyntaxString());
						System.err.println();
					}
					else
						System.err.println(" "+Messages.getString("handleErrorMessageEmpty"));

					interpreter.setStopFlag(false);
				}
			};
}
