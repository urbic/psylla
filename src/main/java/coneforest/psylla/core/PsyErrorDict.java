package coneforest.psylla.core;
import coneforest.psylla.*;

public class PsyErrorDict
	extends PsyModule
{
	public PsyErrorDict()
		throws PsyException
	{
		//super("error");
		registerOperators(OP_HANDLEERROR);
	}

	public static final PsyOperator OP_HANDLEERROR
		=new PsyOperator("handleerror")
			{
				@Override
				public void action(final Interpreter interpreter)
					throws ClassCastException, PsyException
				{
					final PsyDictlike errorObj=(PsyDictlike)interpreter.systemDict().get("$error");
					errorObj.put("newerror", PsyBoolean.FALSE);

					System.err.println(Messages.format("handleErrorMessage",
							errorObj.get("errorname").toSyntaxString(),
							errorObj.get("emitter").toSyntaxString()));

					System.err.print(Messages.getString("handleErrorMessageOStack"));
					final PsyArray ostack=(PsyArray)errorObj.get("ostack");
					if(ostack.length()!=0)
					{
						System.err.print("\n\t");
						for(final var o: ostack)
							System.err.print(" "+o.toSyntaxString());
						System.err.println();
					}
					else
						System.err.println(" "+Messages.getString("handleErrorMessageEmpty"));

					System.err.print(Messages.getString("handleErrorMessageEStack"));
					final PsyArray estack=(PsyArray)errorObj.get("estack");
					if(estack.length()!=0)
					{
						System.err.print("\n\t");
						for(final var o: estack)
							System.err.print(" "+o.toSyntaxString());
						System.err.println();
					}
					else
						System.err.println(" "+Messages.getString("handleErrorMessageEmpty"));

					interpreter.setStopFlag(false);
				}
			};
}
