package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code value}, a type of immutable objects.
*/
@Type("value")
public interface PsyValue
	extends PsyObject
{
	/**
	*	{@return this object}
	*/
	@Override
	public default PsyObject psyClone()
	{
		return this;
	}
}
