package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code writable}, a type of objects that can be
*	treated as sink for characters.
*/
@Type("writable")
public interface PsyWritable
	extends PsyObject
{

	/**
	*	Writes a Ψ-{@code integer} character to this object.
	*
	*	@param oCharacter a Ψ-{@code integer} object representing a character
	*	to be written.
	*	@throws PsyException when an error occurs during write.
	*/
	public void psyWrite(final PsyInteger oCharacter)
		throws PsyException;

	/**
	*	Writes a Ψ-{@code stringy} string of characters to this object.
	*
	*	@param oStringy a Ψ-{@code stringy} object representing a string to be
	*	written.
	*	@throws PsyException when an error occurs during write.
	*/
	public void psyWriteString(final PsyStringy oStringy)
		throws PsyException;

}
