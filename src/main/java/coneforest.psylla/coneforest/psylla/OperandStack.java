package coneforest.psylla;

import coneforest.psylla.core.*;
import java.util.Optional;

/**
*	An interpreter’s operand stack.
*/
public class OperandStack
	extends Stack<PsyObject>
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
		throws PsyRangeCheckException, PsyStackUnderflowException
	{
		if(size()<size)
			throw new PsyStackUnderflowException();
		if(size<0)
			throw new PsyRangeCheckException();
	}

	public void popOperands(final int count)
		throws PsyStackUnderflowException
	{
		final int offset=size()-count;
		if(offset<0)
		{
			backupSize=0;
			throw new PsyStackUnderflowException();
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
		throws PsyUnmatchedMarkException
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsyMark.MARK)
				return i;
		throw new PsyUnmatchedMarkException();
	}

	public void pushOptional(final Optional<? extends PsyObject> opt)
	{
		var present=opt.isPresent();
		if(present)
			push(opt.get());
		push(PsyBoolean.of(present));
	}

	private PsyObject[] backup=new PsyObject[5];
	private int backupSize=0;
}
