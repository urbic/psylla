package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code resettable}, a type of objects that can be
*	reset in some sense.
*/
@coneforest.psylla.Type("resettable")
public interface PsyResettable
	extends PsyObject
{

	/**
	*	Reset this object.
	*
	*	@throws PsyException when error occured during reset.
	*/
	public void psyReset()
		throws PsyException;
}
