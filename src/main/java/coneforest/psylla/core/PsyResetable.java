package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code resetable}, a type of objects that can be
*	reset in some sense.
*/
@coneforest.psylla.Type("resetable")
public interface PsyResetable
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
