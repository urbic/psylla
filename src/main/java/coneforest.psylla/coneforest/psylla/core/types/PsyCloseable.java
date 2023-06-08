package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;

/**
*	A representation of {@code closeable}, a type of objects that can be closed
*	in some sense.
*/
@Type("closeable")
public interface PsyCloseable
	extends PsyObject
{

	/**
	*	Closes this object.
	*
	*	@throws PsyError when error occured during closing.
	*/
	public void psyClose()
		throws PsyError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyCloseable>("close", PsyCloseable::psyClose),
		};
}
