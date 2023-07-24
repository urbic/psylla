package coneforest.psylla.core;

import coneforest.psylla.*;

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

	/**
	*	Context action of the {@code clear} operator.
	*/
	@OperatorType("clear")
	public static final ContextAction PSY_CLEAR
		=ContextAction.<PsyClearable>ofConsumer(PsyClearable::psyClear);
}
