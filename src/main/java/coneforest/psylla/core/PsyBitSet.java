package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code bitset}, a set of nonnegative {@code integer}
*	objects.
*/
@Type("bitset")
public class PsyBitSet
	implements PsyFormalSet<PsyInteger>
{
	/**
	*	Instantiate an empty {@code bitset} object.
	*/
	public PsyBitSet()
	{
		this(new java.util.BitSet());
	}

	/**
	*	Instantiate a {@code bitset} object from a given {@link
	*	java.util.BitSet} object.
	*
	*	@param bitset a bit set.
	*/
	public PsyBitSet(final java.util.BitSet bitset)
	{
		this.bitset=bitset;
	}

	@Override
	public PsyBitSet psyClone()
	{
		return new PsyBitSet((java.util.BitSet)bitset.clone());
	}

	@Override
	public String toSyntaxString()
	{
		final var sb=new StringBuilder("%bitset=");
		int j=-1;
		for(int i=bitset.nextSetBit(0); i>=0; i=bitset.nextSetBit(i+1))
		{
			for(int k=j+1; k<i; k++)
				sb.append('0');
			sb.append('1');
			j=i;
		}
		sb.append('%');
		return sb.toString();
	}

	public java.util.BitSet getBitSet()
	{
		return bitset;
	}

	@Override
	public void psyAppend(final PsyInteger oIndex)
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		if(length()==Integer.MAX_VALUE)
			throw new PsyLimitCheckException();
		try
		{
			bitset.set(oIndex.intValue(), true);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public void psyAppendAll(final PsyIterable<? extends PsyInteger> oIterable)
		throws PsyErrorException
	{
		if(oIterable instanceof PsyBitSet oBitSet)
			bitset.or(oBitSet.bitset);
		else
			PsyFormalSet.super.psyAppendAll(oIterable);
	}

	@Override
	public void psyRemove(final PsyInteger oIndex)
	{
		try
		{
			bitset.set(oIndex.intValue(), false);
		}
		catch(final IndexOutOfBoundsException ex)
		{
			// NOP
		}
	}

	@Override
	public void psyRemoveAll(PsyIterable<? extends PsyInteger> oIterable)
	{
		if(oIterable instanceof PsyBitSet oBitSet)
			bitset.andNot(oBitSet.bitset);
		else
			PsyFormalSet.super.psyRemoveAll(oIterable);
	}

	@Override
	public java.util.Iterator<PsyInteger> iterator()
	{
		return new java.util.Iterator<PsyInteger>()
			{
				@Override
				public boolean hasNext()
				{
					return index>=0;
				}

				@Override
				public PsyInteger next()
				{
					//if(hasNext())
					//{
						PsyInteger result=PsyInteger.of(index);
						index=bitset.nextSetBit(index+1);
						return result;
					//}
					//else
					//	throw new java.util.NoSuchElementException();
				}

				private int index=bitset.nextSetBit(0);
			};
	}

	@Override
	public void psyClear()
	{
		bitset.clear();
	}

	@Override
	public int length()
	{
		return bitset.cardinality();
	}

	@Override
	public PsyBoolean psyContains(final PsyInteger oElement)
	{
		return PsyBoolean.of(bitset.get(oElement.intValue()));
	}

	@Override
	public PsyBoolean psyIntersects(final PsyFormalSet oSet)
	{
		if(oSet instanceof PsyBitSet oBitSet)
			return PsyBoolean.of(bitset.intersects(oBitSet.bitset));
		else
			return PsyFormalSet.super.psyIntersects(oSet);
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(o instanceof PsyBitSet oBitSet
				&& bitset.equals(oBitSet.bitset));
	}

	@Override
	public PsyStream psyStream()
	{
		return new PsyStream(bitset.stream().<PsyInteger>mapToObj(PsyInteger::of));
	}

	private final java.util.BitSet bitset;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity01
				("bitset", PsyBitSet::new),
		};

}
