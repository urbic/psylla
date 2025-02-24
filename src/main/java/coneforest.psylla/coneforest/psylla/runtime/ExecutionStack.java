package coneforest.psylla.runtime;

import coneforest.psylla.core.*;

/**
*	An interpreter’s execution stack.
*/
@SuppressWarnings("serial")
public class ExecutionStack extends Stack<PsyObject>
{
	/**
	*	Constructs an empty stack.
	*/
	public ExecutionStack()
	{
		// NOP
	}

	/**
	*	Enters the loop environment. Pushes the loop mark onto this stack.
	*/
	public void enterLoop()
	{
		push(PsyExecMark.LOOP_MARK);
	}

	public boolean checkLoop()
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsyExecMark.LOOP_MARK)
				return true;
		return false;
	}

	/**
	*	Exits the innermost loop environment. Pops all the elements from this stack upto the first
	*	loop mark.
	*
	*	@throws PsyInvalidExitException when there is not loop mark on this stack.
	*/
	public void exitLoop()
		throws PsyInvalidExitException
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsyExecMark.LOOP_MARK)
			{
				setSize(i);
				return;
			}
		throw new PsyInvalidExitException();
	}

	/**
	*	Enters the stopped environment. Pushes the stop mark onto this stack.
	*/
	public void enterStop()
	{
		push(PsyExecMark.STOP_MARK);
	}

	public boolean checkStop()
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsyExecMark.STOP_MARK)
				return true;
		return false;
	}

	public void exitStop()
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsyExecMark.STOP_MARK)
			{
				setSize(i);
				return;
			}
		//setSize(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExecutionStack clone()
	{
		return (ExecutionStack)super.clone();
	}
}
