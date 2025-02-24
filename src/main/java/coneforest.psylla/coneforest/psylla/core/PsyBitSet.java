package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.BitSet;
import java.util.Iterator;

/**
*	The representation of {@code bitset}, a set of nonnegative {@code integer} objects.
*/
@Type("bitset")
public class PsyBitSet
	implements PsyFormalSet<PsyInteger>
{
	/**
	*	Context action of the {@code bitset} operator.
	*/
	@OperatorType("bitset")
	public static final ContextAction PSY_BITSET
		=ContextAction.ofSupplier(PsyBitSet::new);

	private final BitSet bitset;

	/**
	*	Instantiate an empty {@code bitset} object.
	*/
	public PsyBitSet()
	{
		this(new BitSet());
	}

	/**
	*	Instantiate a {@code bitset} object from a given {@link BitSet} object.
	*
	*	@param bitset a bit set.
	*/
	public PsyBitSet(final BitSet bitset)
	{
		this.bitset=bitset;
	}

	@Override
	public PsyBitSet psyClone()
	{
		return new PsyBitSet((BitSet)bitset.clone());
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

	/*public BitSet getBitSet()
	{
		return bitset;
	}*/

	@Override
	public void psyAppend(final PsyInteger oIndex)
		throws PsyLimitCheckException, PsyRangeCheckException
	{
		//if(length()==Integer.MAX_VALUE)
		//	throw new PsyLimitCheckException();
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
		throws PsyLimitCheckException, PsyRangeCheckException
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
	public void psyRemoveAll(final PsyIterable<? extends PsyInteger> oIterable)
	{
		switch(oIterable)
		{
			case PsyBitSet oBitSet->bitset.andNot(oBitSet.bitset);
			default->PsyFormalSet.super.psyRemoveAll(oIterable);
		}
	}

	@Override
	public Iterator<PsyInteger> iterator()
	{
		return new Iterator<PsyInteger>()
			{
				private int index=bitset.nextSetBit(0);

				@Override
				public boolean hasNext()
				{
					return index>=0;
				}

				@Override
				public PsyInteger next()
				{
					final var result=PsyInteger.of(index);
					index=bitset.nextSetBit(index+1);
					return result;
				}
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
	public PsyBoolean psyIntersects(final PsyFormalSet<? extends PsyInteger> oSet)
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
	public PsyFormalStream<PsyInteger> psyStream()
	{
		return PsyFormalStream.<PsyInteger>of(
				bitset.stream().<PsyInteger>mapToObj(PsyInteger::of));
	}
}
