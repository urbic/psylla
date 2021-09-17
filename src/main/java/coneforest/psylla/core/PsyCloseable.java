package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code closeable}, a type of objects that can be
*	closed in some sense.
*/
@Type("closeable")
public interface PsyCloseable
	extends PsyObject
{

	/**
	*	Closes this object.
	*
	*	@throws PsyException when error occured during closing.
	*/
	public void psyClose()
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyCloseable>("close", PsyCloseable::psyClose),
		};
}
