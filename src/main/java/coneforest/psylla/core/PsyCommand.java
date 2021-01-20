package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code command} object.
*/
public class PsyCommand
	extends PsyName
{
	/**
	*	Creates a new Ψ-{@code command} object with the given name.
	*
	*	@param cs a name.
	*/
	public PsyCommand(final CharSequence cs)
	{
		super(cs);
	}

	@Override
	public void execute()
	{
		final var interpreter=PsyContext.psyCurrentContext();
		try
		{
			interpreter.psyLoad(this).invoke();
		}
		catch(final PsyException e)
		{
			e.setEmitter(this);
			interpreter.handleError(e);
		}
	}

	@Override
	public void invoke()
	{
		execute();
	}

	@Override
	public String toSyntaxString()
	{
		return stringValue();
	}
}
