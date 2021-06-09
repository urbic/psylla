package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code set} object.
*/
@Type("set")
public class PsySet
	implements PsyFormalSet<PsyObject>
{
	/**
	*	Creates a new empty Ψ-{@code set}.
	*/
	public PsySet()
	{
		this(new java.util.HashSet<PsyObject>());
	}

	/**
	*	Creates a new Ψ-{@code set} wrapped around the given hash set.
	*
	*	@param set a given hash set.
	*/
	public PsySet(final java.util.HashSet<PsyObject> set)
	{
		this.set=set;
	}

	@Override
	public PsySet psyClone()
	{
		return new PsySet((java.util.HashSet<PsyObject>)set.clone());
	}

	@Override
	public int length()
	{
		return set.size();
	}

	@Override
	public void psyAppend(final PsyObject o)
	{
		set.add(o);
	}

	@Override
	public void psyRemove(final PsyObject o)
	{
		set.remove(o);
	}

	@Override
	public PsyBoolean psyContains(final PsyObject o)
	{
		return PsyBoolean.valueOf(set.contains(o));
	}

	@Override
	public void psyClear()
	{
		set.clear();
	}

	@Override
	public java.util.Iterator<PsyObject> iterator()
	{
		return set.iterator();
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(set.stream());
	}

	private final java.util.HashSet<PsyObject> set;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01("set", PsySet::new),
			new PsyOperator.Action("settomark",
				(oContext)->
				{
					final var ostack=oContext.operandStack();
					final int i=ostack.findMarkPosition();
					final PsySet oSet=new PsySet();
					for(int j=ostack.size()-1; j>=i+1; j--)
						oSet.psyAppend(ostack.get(j));
					ostack.setSize(i);
					ostack.push(oSet);
				}),
		};
}
