package coneforest.psylla.runtime;

import coneforest.psylla.core.*;
import java.util.Optional;

/**
*	An interpreter’s operand stack.
*/
@SuppressWarnings("serial")
public class OperandStack
	extends Stack<PsyObject>
{
	private PsyObject[] backup=new PsyObject[5];
	private int backupSize=0;

	public OperandStack()
	{
	}

	@SuppressWarnings("unchecked")
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
	*/
	public void ensureSize(final int size)
		throws PsyStackUnderflowException
	{
		if(size()<size)
			throw new PsyStackUnderflowException();
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
	*	{@return the position of the topmost {@code mark} object on the operand stack}
	*
	*	@throws PsyUnmatchedMarkException if there is no {@code mark} object on the operand stack.
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
		final var present=opt.isPresent();
		if(present)
			push(opt.get());
		push(PsyBoolean.of(present));
	}

	@Override
	public boolean equals(final Object obj)
	{
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperandStack clone()
	{
		return (OperandStack)super.clone();
	}
}
