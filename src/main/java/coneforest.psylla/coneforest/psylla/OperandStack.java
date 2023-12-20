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

	/**
	*	Ensures that the operand stack is at least the given size.
	*
	*	@param size the given size.
	*	@throws PsyStackUnderflowException when the operand stack’s size less than the given size.
	*	@throws PsyRangeCheckException when given size is negative.
	*/
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

	/**
	*	Returns the position of the topmost {@code mark} object on the operand stack.
	*
	*	@return the position of the topmost {@code mark} object on the operand stack.
	*	@throws PsyUnmatchedMarkException when there is no {@code mark} object on the operand stack.
	*/
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
