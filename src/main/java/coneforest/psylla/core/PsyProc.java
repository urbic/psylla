package coneforest.psylla.core;
import coneforest.psylla.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
*	An implementation of {@code proc}, a procedure.
*/
@Type("proc")
public class PsyProc
	extends PsyArray
	implements PsyExecutable
{
	/**
	*	Creates new empty {@code proc}.
	*/
	public PsyProc()
	{
		super();
	}

	public PsyProc(final ArrayList<PsyObject> array)
	{
		super(array);
	}

	@Override
	public void invoke(final PsyContext oContext)
	{
		final var estack=oContext.executionStack();
		try
		{
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
		return new PsyProc((ArrayList<PsyObject>)array.clone());
	}

	/*@Override
	public String toSyntaxString()
	{
		return '{'+toSyntaxStringHelper(this)+'}';
	}*/

	@Override
	public String toSyntaxStringHelper(final Set<PsyContainer<PsyObject>> processed)
	{
		if(!processed.add((PsyContainer<PsyObject>)this))
			return '%'+typeName()+'%';
		final var sj=new java.util.StringJoiner(" ", "{", "}");
		for(final var o: this)
			sj.add(o instanceof PsyContainer?
				((PsyContainer<PsyObject>)o).toSyntaxStringHelper(processed):
				o.toSyntaxString());
		return sj.toString();
	}

	public PsyProc psyBind()
	{
		final var dstack=PsyContext.psyCurrentContext().dictStack();

		final var agenda=new ArrayList<PsyProc>();
		final var bound=new HashSet<PsyProc>();

		agenda.add(this);

		while(!agenda.isEmpty())
		{
			final var oProc=agenda.remove(0);
			if(!bound.add(oProc))
				break;
			for(int i=0; i<oProc.length(); i++)
			{
				try
				{
					final var o=oProc.get(i);
					if(o instanceof PsyProc)
						agenda.add((PsyProc)o);
					else if(o instanceof PsyCommand)
					{
						final var oNew=dstack.load((PsyCommand)o);
						if(oNew instanceof PsyOperator
								|| oNew instanceof PsyMark
								|| oNew instanceof PsyNull)
							oProc.put(i, oNew);
					}
				}
				catch(final PsyErrorException e)
				{
					// NOP
				}
			}
		}

		return this;
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyProc>
				("bind", PsyProc::psyBind),
		};

}
