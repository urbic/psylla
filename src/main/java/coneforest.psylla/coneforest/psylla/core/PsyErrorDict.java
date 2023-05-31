package coneforest.psylla.core;
import coneforest.psylla.*;

public class PsyErrorDict
	extends PsyModule
{
	public PsyErrorDict()
		throws PsyErrorException
	{
		//super("error");
		registerOperators(OP_HANDLEERROR);
	}

	public static final PsyOperator OP_HANDLEERROR
		=new PsyOperator("handleerror")
			{
				@Override
				public void perform(final PsyContext oContext)
					throws ClassCastException, PsyErrorException
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
