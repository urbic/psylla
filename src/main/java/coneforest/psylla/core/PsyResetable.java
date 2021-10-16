package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code resetable}, an object that can be reset in some
*	sense.
*/
@Type("resetable")
public interface PsyResetable
	extends PsyObject
{

	/**
	*	Reset this {@code resetable} object.
	*
	*	@throws PsyException when error occured during reset.
	*/
	public void psyReset()
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyResetable>
				("reset", PsyResetable::psyReset),
		};

}
