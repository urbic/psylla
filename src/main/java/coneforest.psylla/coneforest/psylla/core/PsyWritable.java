package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	A representation of {@code writable}, a type of objects that can be treated as sink for
*	characters.
*/
@Type("writable")
public interface PsyWritable
	extends PsyObject
{
	/**
	*	Writes an {@code integer} character to this object.
	*
	*	@param oCharacter an {@code integer} object representing a character to be written.
	*	@throws PsyIOErrorException when I/O error occurs during write.
	*/
	public void psyWrite(final PsyInteger oCharacter)
		throws PsyIOErrorException;

	/**
	*	Writes a {@code textual} string of characters to this object.
	*
	*	@param oTextual a {@code textual} object representing a string to be written.
	*	@throws PsyIOErrorException when I/O error occurs during write.
	*/
	public void psyWriteString(final PsyTextual oTextual)
		throws PsyIOErrorException;

	/**
	*	Context action of the {@code write} operator.
	*/
	@OperatorType("write")
	public static final ContextAction PSY_WRITE
		=ContextAction.<PsyWritable, PsyInteger>ofBiConsumer(PsyWritable::psyWrite);

	/**
	*	Context action of the {@code writestring} operator.
	*/
	@OperatorType("writestring")
	public static final ContextAction PSY_WRITESTRING
		=ContextAction.<PsyWritable, PsyTextual>ofBiConsumer(PsyWritable::psyWriteString);
}
