package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("sequential")
public interface PsySequential<T extends PsyObject>
	extends PsyObject
{
	public void psyForAll(final PsyObject oProc, final PsyContext oContext)
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Action("forall",
				(oContext)->
				{
					final var ostack=oContext.operandStackBacked(2);
					ostack.<PsySequential>getBacked(0).psyForAll(ostack.getBacked(1), oContext);
				}),
		};
}
