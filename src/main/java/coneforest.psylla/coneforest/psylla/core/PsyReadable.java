package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Optional;

/**
*	The representation of {@code readable}, an object that can be treated as a source of characters
*	to read from.
*/
@Type("readable")
public interface PsyReadable
	extends
		PsyReady
{
	/**
	*	Context action of the {@code skip} operator.
	*/
	@OperatorType("skip")
	public static final ContextAction PSY_SKIP
		=ContextAction.<PsyReadable, PsyInteger>ofBiFunction(PsyReadable::psySkip);

	/**
	*	Context action of the {@code read} operator.
	*/
	@OperatorType("read")
	public static final ContextAction PSY_READ
		=ContextAction.<PsyReadable>ofOptionalFunction(PsyReadable::psyRead);

	/**
	*	Reads a single character.
	*
	*	@return the character read, as an integer in the range 0 to 65535 ({@code 0x00–0xFFFF}), or
	*		-1 if the end of the source has been reached.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public int read()
		throws PsyIOErrorException;

	/**
	*	Read an {@code integer} character from this object and returns it. Returns {@link
	*	PsyInteger#MINUS_ONE} when end of input has been reached. TODO
	*
	*	@return an {@code integer} representing the character read from this object.
	*	@throws PsyIOErrorException when an I/O error occurs.
	*/
	public default Optional<PsyInteger> psyRead()
		throws PsyIOErrorException
	{
		final int c=read();
		return c==-1? Optional.<PsyInteger>empty(): Optional.<PsyInteger>of(PsyInteger.of(c));
	}

	/**
	*	Read a {@code string} from this object and returns it.
	*
	*	@param oCount an {@code integer} representing the length of the string.
	*	@return a string read.
	*	@throws PsyIOErrorException when I/O error occurs.
	*/
	public PsyStringBuffer psyReadString(final PsyInteger oCount)
		throws PsyIOErrorException, PsyLimitCheckException, PsyRangeCheckException, PsyUnsupportedException;

	/**
	*	Read a line ({@code string}) from this object and returns it.
	*
	*	@return a line read.
	*	@throws PsyIOErrorException when I/O error occurs.
	*/
	public PsyStringBuffer psyReadLine()
		throws PsyIOErrorException, PsyUnsupportedException;

	/**
	*	Skips characters. This method will block until some characters are available, an I/O error
	*	occurs, or end of input is reached.
	*
	*	@param oCount an {@code integer} representing the number of characters to be skipped.
	*	@return an {@code integer} representing the number of characters actually skipped.
	*	@throws PsyIOErrorException when I/O error occurs.
	*/
	public PsyInteger psySkip(final PsyInteger oCount)
		throws PsyIOErrorException, PsyRangeCheckException;

	/**
	*	Returns a {@code boolean} object indicating whether this object is ready to be read.
	*
	*	@return {@code true} if this object is ready to be read, and {@code false} otherwise.
	*	@throws PsyIOErrorException when I/O error occurs.
	*/
	@Override
	public PsyBoolean psyReady()
		throws PsyIOErrorException;
}
