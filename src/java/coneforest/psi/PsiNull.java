package coneforest.psi;

public final class PsiNull
	extends PsiObject
	implements PsiAtomic
{
	@Override
	public String getTypeName() { return "null"; }

	@Override
	public void invoke(Interpreter interpreter)
	{
		if(isLiteral())
			interpreter.getOperandStack().push(this);
	}

	@Override
	public String toString()
	{
		return "null";
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNull);
	}
}
