package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code command} object.
*/
public class PsiCommand
	extends PsiName
{
	/**
	*	Creates a new Ψ-{@code command} object with the given name.
	*
	*	@param cs a name.
	*/
	public PsiCommand(final CharSequence cs)
	{
		super(cs);
	}

	@Override
	public void execute(final Interpreter interpreter)
	{
		try
		{
			interpreter.psiLoad(this).invoke(interpreter);
		}
		catch(PsiException e)
		{
			e.setEmitter(this);
			interpreter.handleError(e);
		}
	}

	@Override
	public String toSyntaxString()
	{
		return stringValue();
	}
}
