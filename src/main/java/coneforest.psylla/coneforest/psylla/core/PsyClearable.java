package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code clearable}, a type of objects that can be cleared (emptied) in some
*	manner.
*/
@Type("clearable")
public interface PsyClearable
	extends PsyObject
{
	/**
	*	Context action of the {@code clear} operator.
	*/
	@OperatorType("clear")
	public static final ContextAction PSY_CLEAR
		=ContextAction.<PsyClearable>ofConsumer(PsyClearable::psyClear);

	/**
	*	Clear this object.
	*/
	public void psyClear();
}
