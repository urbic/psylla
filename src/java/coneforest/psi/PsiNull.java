package coneforest.psi;

public final class PsiNull
	extends PsiObject
	implements PsiAtomic
{
	public String getTypeName() { return "null"; }

	public void invoke(Interpreter interpreter)
	{
		if(isLiteral())
			interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return "null";
	}

	public PsiBoolean eq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNull);
	}
}
