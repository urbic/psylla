package coneforest.psi.core;
import coneforest.psi.*;

public class PsiProc
	extends PsiArray
{
	@Override
	public String typeName()
	{
		return "proc";
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
		catch(PsiException e)
		{
			// TODO?
		}
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
						if(oNew instanceof PsiOperator)
							oProc.put(i, oNew);
					}
				}
				catch(PsiException e)
				{
					// NOP
				}
			}
		}

		return this;
	}
}
