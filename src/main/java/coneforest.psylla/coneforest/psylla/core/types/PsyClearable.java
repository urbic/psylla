package coneforest.psylla.core.types;

import coneforest.psylla.Type;

/**
*	A representation of {@code clearable}, a type of objects that can be
*	cleared (emptied) in some sense.
*/
@Type("clearable")
public interface PsyClearable
	extends PsyObject
{

	/**
	*	Clear this object.
	*/
	public void psyClear();

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity10<PsyClearable>("clear", PsyClearable::psyClear),
		};

}
