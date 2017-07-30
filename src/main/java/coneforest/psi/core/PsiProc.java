package coneforest.psi.core;
import coneforest.psi.*;

/**
*	An implementation of Ψ-{@code proc}, a procedure.
*/
@coneforest.psi.Type("proc")
public class PsiProc
	extends PsiArray
{
	public PsiProc()
	{
		super();
	}

	public PsiProc(final java.util.ArrayList<PsiObject> array)
	{
		super(array);
	}

	@Override
	public void invoke(final Interpreter interpreter)
	{
		try
		{
			final ExecutionStack estack=interpreter.executionStack();
			for(int i=length()-1; i>=0; i--)
				estack.push(get(i));
		}
		catch(final PsiRangeCheckException e)
		{
			// NOP
		}
	}

	@Override
	public PsiProc psiClone()
	{
		return new PsiProc((java.util.ArrayList<PsiObject>)array.clone());
	}

	@Override
	public String toSyntaxString()
	{
		return "{"+toSyntaxStringHelper(this)+"}";
	}

	public PsiProc psiBind()
	{
		final DictStack dstack=((Interpreter)PsiContext.psiCurrentContext()).dictStack();

		final java.util.ArrayList<PsiProc> agenda
			=new java.util.ArrayList<PsiProc>();
		final java.util.HashSet<PsiProc> bound
			=new java.util.HashSet<PsiProc>();

		agenda.add(this);

		while(!agenda.isEmpty())
		{
			final PsiProc oProc=agenda.remove(0);
			if(!bound.add(oProc))
				break;
			for(int i=0; i<oProc.length(); i++)
			{
				try
				{
					final PsiObject o=oProc.get(i);
					if(o instanceof PsiProc)
						agenda.add((PsiProc)o);
					else if(o instanceof PsiCommand)
					{
						final PsiObject oNew=dstack.load((PsiCommand)o);
						if(oNew instanceof PsiOperator
								|| oNew instanceof PsiMark
								|| oNew instanceof PsiNull)
							oProc.put(i, oNew);
					}
				}
				catch(final PsiException e)
				{
					// NOP
				}
			}
		}

		return this;
	}
}
