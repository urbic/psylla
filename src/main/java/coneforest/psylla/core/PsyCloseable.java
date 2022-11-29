package coneforest.psylla.core;
import coneforest.psylla.*;

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
	*	@throws PsyErrorException when error occured during closing.
	*/
	public void psyClose()
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyCloseable>("close", PsyCloseable::psyClose),
		};
}
