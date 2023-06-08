package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyIOError;

/**
*	A representation of {@code readable}, an object that can be treated as a
*	source of characters to read from.
*/
@Type("readable")
public interface PsyReadable
	extends
		PsyReady
{

	public int read()
		throws PsyError;

	/**
	*	Read an {@code integer} character from this object and returns it. Returns {@link
	*	PsyInteger#MINUS_ONE} when end of input has been reached.
	*
	*	@return an {@code integer} representing the character read from this object.
	*	@throws PsyError when error occurs.
	*/
	default public PsyInteger psyRead()
		throws PsyError
	{
		return PsyInteger.of(read());
	}

	/**
	*	Read a {@code string} from this object and returns it.
	*
	*	@param oCount an {@code integer} representing the length of the string.
	*	@return a string read.
	*	@throws PsyError when error occurs.
	*/
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyError;

	/**
	*	Read a line ({@code string}) from this object and returns it.
	*
	*	@return a line read,
	*	@throws PsyError when error occurs.
	*/
	public PsyString psyReadLine()
		throws PsyError;

	/**
	*	Skips characters. This method will block until some characters are available, an I/O error
	*	occurs, or end of input is reached.
	*
	*	@param oCount an {@code integer} representing the number of characters to be skipped.
	*	@return an {@code integer} representing the number of characters actually skipped.
	*	@throws PsyError when an error occurs.
	*/
	public PsyInteger psySkip(final PsyInteger oCount)
		throws PsyError;

	/**
	*	Returns a {@code boolean} object indicating whether this object is ready to be read.
	*
	*	@return {@code true} if this object is ready to be read, and {@code false} otherwise.
	*	@throws PsyIOError when I/O error occurs.
	*/
	@Override
	public PsyBoolean psyReady()
		throws PsyIOError;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyReadable, PsyInteger>
				("skip", PsyReadable::psySkip),
			new PsyOperator.Action
				("read", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						final var oCharacter=ostack.<PsyReadable>getBacked(0).psyRead();
						boolean notEOF=(oCharacter!=PsyInteger.MINUS_ONE);
						if(notEOF)
							ostack.push(oCharacter);
						ostack.push(PsyBoolean.of(notEOF));
					}),
		};
}
