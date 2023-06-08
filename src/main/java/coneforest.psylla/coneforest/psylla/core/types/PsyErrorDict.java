package coneforest.psylla.core.types;

import coneforest.psylla.Messages;
import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

public class PsyErrorDict
	extends PsyModule
{
	public PsyErrorDict()
		throws PsyError
	{
		//super("error");
		registerOperators(OP_HANDLEERROR);
	}

	public static final PsyOperator OP_HANDLEERROR
		=new PsyOperator("handleerror")
			{
				@Override
				public void perform(final PsyContext oContext)
					throws ClassCastException, PsyError
				{
					final var errorObj=(PsyFormalDict)oContext.systemDict().get("$error");
					errorObj.put("newerror", PsyBoolean.FALSE);

					System.err.println(Messages.format("handleErrorMessage",
							errorObj.get("errorname").toSyntaxString(),
							errorObj.get("emitter").toSyntaxString()));

					System.err.print(Messages.getString("handleErrorMessageOStack"));
					final var ostack=(PsyArray)errorObj.get("ostack");
					{
						final var sj=new java.util.StringJoiner(" ", "\n\t", "");
						sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
						ostack.forEach(o->sj.add(o.toSyntaxString()));
						System.err.println(sj.toString());
					}

					System.err.print(Messages.getString("handleErrorMessageEStack"));
					final var estack=(PsyArray)errorObj.get("estack");
					{
						final var sj=new java.util.StringJoiner(" ", "\n\t", "");
						sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
						estack.forEach(o->sj.add(o.toSyntaxString()));
						System.err.println(sj.toString());
					}

					oContext.setStopped(false);
				}
			};
}
