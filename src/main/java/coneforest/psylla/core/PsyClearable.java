package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code clearable}, a type of objects that can be
*	cleared (emptied) in some sense.
*/
@coneforest.psylla.Type("clearable")
public interface PsyClearable
	extends PsyObject
{

	/**
	*	Clear this object.
	*/
	public void psyClear();
}
