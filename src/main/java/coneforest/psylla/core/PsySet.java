package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code set} object.
*/
@coneforest.psylla.Type("set")
public class PsySet
	implements PsySetlike<PsyObject>
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

	private final java.util.HashSet<PsyObject> set;
}
