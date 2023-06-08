package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

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
	*	@throws PsyError when error occured during reset.
	*/
	public void psyReset()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyResetable>
				("reset", PsyResetable::psyReset),
		};

}
