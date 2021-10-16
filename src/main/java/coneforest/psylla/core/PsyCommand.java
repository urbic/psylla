package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code command} object.
*/
@Type("command")
public class PsyCommand
	extends PsyName
{
	/**
	*	Creates a new {@code command} object with the given name.
	*
	*	@param cs a name.
	*/
	public PsyCommand(final CharSequence cs)
	{
		super(cs);
	}

	@Override
	public void execute(final PsyContext oContext)
	{
		try
		{
			oContext.psyLoad(this).invoke(oContext);
		}
		catch(final PsyException e)
		{
			e.setEmitter(this);
			oContext.handleError(e);
		}
	}

	@Override
	public void invoke(final PsyContext oContext)
	{
		execute(oContext);
	}

	@Override
	public String toSyntaxString()
	{
		return stringValue();
	}
}
