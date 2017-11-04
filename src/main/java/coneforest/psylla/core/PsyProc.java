package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	An implementation of Ψ-{@code proc}, a procedure.
*/
@coneforest.psylla.Type("proc")
public class PsyProc
	extends PsyArray
{
	public PsyProc()
	{
		super();
	}

	public PsyProc(final java.util.ArrayList<PsyObject> array)
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
		catch(final PsyRangeCheckException e)
		{
			// NOP
		}
	}

	@Override
	public PsyProc psyClone()
	{
		return new PsyProc((java.util.ArrayList<PsyObject>)array.clone());
	}

	@Override
	public String toSyntaxString()
	{
		return "{"+toSyntaxStringHelper(this)+"}";
	}

	public PsyProc psyBind()
	{
		final DictStack dstack=((Interpreter)PsyContext.psyCurrentContext()).dictStack();

		final java.util.ArrayList<PsyProc> agenda
			=new java.util.ArrayList<PsyProc>();
		final java.util.HashSet<PsyProc> bound
			=new java.util.HashSet<PsyProc>();

		agenda.add(this);

		while(!agenda.isEmpty())
		{
			final PsyProc oProc=agenda.remove(0);
			if(!bound.add(oProc))
				break;
			for(int i=0; i<oProc.length(); i++)
			{
				try
				{
					final PsyObject o=oProc.get(i);
					if(o instanceof PsyProc)
						agenda.add((PsyProc)o);
					else if(o instanceof PsyCommand)
					{
						final PsyObject oNew=dstack.load((PsyCommand)o);
						if(oNew instanceof PsyOperator
								|| oNew instanceof PsyMark
								|| oNew instanceof PsyNull)
							oProc.put(i, oNew);
					}
				}
				catch(final PsyException e)
				{
					// NOP
				}
			}
		}

		return this;
	}
}
