package coneforest.psylla;

import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsyStackUnderflow;
import coneforest.psylla.core.errors.PsyUnmatchedMark;
import coneforest.psylla.core.types.PsyMark;
import coneforest.psylla.core.types.PsyObject;

/**
*	An interpreter’s operand stack.
*/
public class OperandStack extends Stack<PsyObject>
{
	public <T extends PsyObject> T getBacked(final int index)
	{
		return (T)backup[index];
	}

	public void clearBackup()
	{
		backupSize=0;
	}

	public void ensureSize(final int size)
		throws PsyError
	{
		if(size()<size)
			throw new PsyStackUnderflow();
		if(size<0)
			throw new PsyRangeCheck();
	}

	public void popOperands(final int count)
		throws PsyError
	{
		final int offset=size()-count;
		if(offset<0)
		{
			backupSize=0;
			throw new PsyStackUnderflow();
		}
		for(backupSize=0; backupSize<count; backupSize++)
			backup[backupSize]=get(offset+backupSize);
		setSize(offset);
	}

	public void rollback()
	{
		for(int i=0; i<backupSize; i++)
			push(backup[i]);
		backupSize=0;
	}

	public int findMarkPosition()
		throws PsyUnmatchedMark
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsyMark.MARK)
				return i;
		throw new PsyUnmatchedMark();
	}

	private PsyObject[] backup=new PsyObject[5];
	private int backupSize=0;
}
