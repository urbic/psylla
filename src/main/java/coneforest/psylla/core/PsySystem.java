package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("system")
public class PsySystem
{
				/*new PsyOperator.Action
					("prettyprint",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyWriter stdwriter=interpreter.dictStack().load("stdout");
							stdwriter.psyWriteString(ostack.getBacked(0).psySyntax());
							stdwriter.psyWriteString(interpreter.dictStack().load("eol"));
							stdwriter.psyFlush();
						}
					),
				*/

	@Operator("?")
	public static void psyPrettyPrint(final PsyObject o)
		throws PsyException
	{
		((PsyWriter)PsyNamespace.namespace("system").get("stdout")).psyWriteString(o.psySyntax());
		((PsyWriter)PsyNamespace.namespace("system").get("stdout")).psyWriteString(new PsyName("\n")); // TODO
	}

}
