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

					System.out.println(Messages.format("handleErrorMessage",
							errorObj.get("errorname").toSyntaxString(),
							errorObj.get("emitter").toSyntaxString()));

					System.out.print(Messages.getString("handleErrorMessageOStack"));
					final PsiArray ostack=(PsiArray)errorObj.get("ostack");
					if(ostack.length()!=0)
					{
						System.out.print("\n\t");
						for(PsiObject obj: ostack)
							System.out.print(" "+obj.toSyntaxString());
						System.out.println();
					}
					else
						System.out.println(" "+Messages.getString("handleErrorMessageEmpty"));

					System.out.print(Messages.getString("handleErrorMessageEStack"));
					final PsiArray estack=(PsiArray)errorObj.get("estack");
					if(estack.length()!=0)
					{
						System.out.print("\n\t");
						for(PsiObject obj: estack)
							System.out.print(" "+obj.toSyntaxString());
						System.out.println();
					}
					else
						System.out.println(" "+Messages.getString("handleErrorMessageEmpty"));

					interpreter.setStopFlag(false);
				}
			};
}
