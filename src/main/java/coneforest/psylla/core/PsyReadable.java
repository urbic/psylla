package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code readable}, a type of objects that can be
*	treated as a source of characters.
*/
@Type("readable")
public interface PsyReadable
	extends PsyObject
{

	public int read()
		throws PsyException;

	/**
	*	Read a character (Ψ-{@code integer}) from this object and returns it.
	*	Returns {@link PsyInteger#MINUS_ONE} when end of input has been
	*	reached.
	*
	*	@return a Ψ-{@code integer} representing the character read from this
	*	object.
	*	@throws PsyException when error occurs.
	*/
	default public PsyInteger psyRead()
		throws PsyException
	{
		return PsyInteger.valueOf(read());
	}

	/**
	*	Read a Ψ-{@code string} from this object and returns it.
	*
	*	@param oCount a Ψ-{@code integer} representing the length of the string.
	*	@return a string read.
	*	@throws PsyException when error occurs.
	*/
	public PsyString psyReadString(final PsyInteger oCount)
		throws PsyException;

	/**
	*	Read a line (Ψ-{@code string}) from this object and returns it.
	*
	*	@return a line read,
	*	@throws PsyException when error occurs.
	*/
	public PsyString psyReadLine()
		throws PsyException;

	/**
	*	Skips characters. This method will block until some characters are
	*	available, an I/O error occurs, or end of input is reached.
	*
	*	@param oCount a Ψ-{@code integer} representing the number of characters
	*	to be skipped.
	*	@return a Ψ-{@code boolean} indicating whether all the characters were
	*	skipped successfully
	*	@throws PsyException when an error occurs.
	*/
	public PsyBoolean psySkip(final PsyInteger oCount)
		throws PsyException;

	/**
	*	Returns a Ψ-{@code boolean} indicating whether this object is ready to
	*	be read.
	*
	*	@return {@code true} if this object is ready to be read, and {@code
	*	false} otherwise.
	*	@throws PsyException when I/O error occurs.
	*/
	public PsyBoolean psyReady()
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyReadable>("ready", PsyReadable::psyReady),
			new PsyOperator.Arity21<PsyReadable, PsyInteger>("skip", PsyReadable::psySkip),
		};

}
