package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code writable}, a type of objects that can be treated
*	as sink for characters.
*/
@Type("writable")
public interface PsyWritable
	extends PsyObject
{

	/**
	*	Writes an {@code integer} character to this object.
	*
	*	@param oCharacter an {@code integer} object representing a character to
	*	be written.
	*
	*	@throws PsyException when an error occurs during write.
	*/
	public void psyWrite(final PsyInteger oCharacter)
		throws PsyException;

	/**
	*	Writes a {@code textual} string of characters to this object.
	*
	*	@param oTextual a {@code textual} object representing a string to be
	*	written.
	*
	*	@throws PsyException when an error occurs during write.
	*/
	public void psyWriteString(final PsyTextual oTextual)
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity20<PsyWritable, PsyInteger>("write", PsyWritable::psyWrite),
			new PsyOperator.Arity20<PsyWritable, PsyTextual>("writestring", PsyWritable::psyWriteString),
		};
}
