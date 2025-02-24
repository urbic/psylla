package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code writable}, an object that can be treated as sink for characters.
*/
@Type("writable")
public interface PsyWritable
	extends PsyObject
{
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
		=ContextAction.<PsyWritable, PsyString>ofBiConsumer(PsyWritable::psyWriteString);

	/**
	*	Writes an {@code integer} character to this object.
	*
	*	@param oCharacter an {@code integer} object representing a character to be written.
	*	@throws PsyIOErrorException when I/O error occurs during write.
	*/
	public void psyWrite(final PsyInteger oCharacter)
		throws PsyIOErrorException;

	/**
	*	Writes a {@code string} string of characters to this object.
	*
	*	@param oString a {@code string} object representing a string to be written.
	*	@throws PsyIOErrorException when I/O error occurs during write.
	*/
	public void psyWriteString(final PsyString oString)
		throws PsyIOErrorException;
}
