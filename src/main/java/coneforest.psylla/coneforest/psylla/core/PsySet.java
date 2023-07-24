package coneforest.psylla.core;

import coneforest.psylla.*;
import java.util.HashSet;
import java.util.Iterator;

/**
*	A representation of {@code set}.
*/
@Type("set")
public class PsySet
	implements PsyFormalSet<PsyObject>
{
	/**
	*	Creates a new empty {@code set} object.
	*/
	public PsySet()
	{
		this(new HashSet<PsyObject>());
	}

	/**
	*	Creates a new {@code set} object wrapped around the given hash set.
	*
	*	@param set a given hash set.
	*/
	public PsySet(final HashSet<PsyObject> set)
	{
		this.set=set;
	}

	@Override
	public PsySet psyClone()
	{
		return new PsySet((HashSet<PsyObject>)set.clone());
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
		return PsyBoolean.of(set.contains(o));
	}

	@Override
	public void psyClear()
	{
		set.clear();
	}

	@Override
	public Iterator<PsyObject> iterator()
	{
		return set.iterator();
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(set.stream());
	}

	private final HashSet<PsyObject> set;

	/**
	*	Context action of the {@code set} operator.
	*/
	@OperatorType("set")
	public static final ContextAction PSY_SET
		=ContextAction.ofSupplier(PsySet::new);

	/**
	*	Context action of the {@code settomark} operator.
	*/
	@OperatorType("settomark")
	public static final ContextAction PSY_SETTOMARK=oContext->
		{
			final var ostack=oContext.operandStack();
			final int i=ostack.findMarkPosition();
			final var oSet=new PsySet();
			for(int j=ostack.size()-1; j>=i+1; j--)
				oSet.psyAppend(ostack.get(j));
			ostack.setSize(i);
			ostack.push(oSet);
		};
}
