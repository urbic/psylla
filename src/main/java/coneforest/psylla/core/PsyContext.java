package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of Ψ-{@code context}, an execution context.
*/
@Type("context")
public interface PsyContext
	extends PsyObject
{

	public long getId();

	public void join()
		throws InterruptedException;

	default public void psyJoin()
		throws PsyException
	{
		if(this==psyCurrentContext())
			throw new PsyInvalidContextException();
		try
		{
			join();
		}
		catch(final InterruptedException e)
		{
			throw new PsyInterruptException();
		}
	}

	@Override
	default public String toSyntaxString()
	{
		return "%context="+getId()+"%";
	}

	/**
	*	Causes the currently executing context to sleep for the specified
	*	number of milliseconds.
	*
	*	@param oDuration the duration to sleep for in milliseconds.
	*	@throws PsyRangeCheckException when the duration is negative.
	*	@throws PsyInterruptException TODO.
	*/
	public static void psySleep(final PsyInteger oDuration)
		throws PsyException
	{
		try
		{
			Thread.sleep(oDuration.longValue());
		}
		catch(final IllegalArgumentException e)
		{
			throw new PsyRangeCheckException();
		}
		catch(final InterruptedException e)
		{
			throw new PsyInterruptException();
		}
	}

	/**
	*	Returns the currently executing context.
	*
	*	@return the currently executing context.
	*/
	public static PsyContext psyCurrentContext()
	{
		return (PsyContext)Thread.currentThread();
	}

	public static PsyDictlike psyCurrentDict()
	{
		return psyCurrentContext().currentDict();
	}

	public void quit();

	public static void psyQuit()
	{
		psyCurrentContext().quit();
	}

	public void stop_();

	public static void psyStop()
	{
		psyCurrentContext().stop_();
	}

	// TODO: move to PsyObject?
	public static void psyExec(final PsyObject o)
	{
		o.invoke();
	}

	public static void psyRepeat(final PsyInteger oCount, final PsyObject oProc)
		throws PsyException
	{
		final var interpreter=PsyContext.psyCurrentContext();
		final var estack=interpreter.executionStack();
		final long count=oCount.longValue();

		if(count<0)
			throw new PsyRangeCheckException();
		if(count==0)
			return;

		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsyOperator("#repeat_continue")
			{
				private long count1=count;

				@Override
				public void action()
				{
					if(count1--==0)
						PsyContext.psyCurrentContext().popLoopLevel();
					else
					{
						estack.push(this);
						oProc.invoke();
					}
				}
			});
	}

	public static void psyLoop(final PsyProc oProc)
	{
		final var interpreter=PsyContext.psyCurrentContext();

		interpreter.pushLoopLevel();
		interpreter.executionStack().push(new PsyOperator("#loop_continue")
			{
				@Override
				public void action()
					throws PsyException
				{
					PsyContext.psyCurrentContext().executionStack().push(this);
					oProc.invoke();
				}
			});
	}

	public static void psyBegin(final PsyDictlike oDictlike)
	{
		psyCurrentContext().dictStack().begin(oDictlike);
	}

	public static void psyEnd()
		throws PsyException
	{
		psyCurrentContext().dictStack().end();
	}

	public static void psyExch()
		throws PsyException
	{
		final var ostack=psyCurrentContext().operandStackBacked(2);
		ostack.push(ostack.getBacked(1));
		ostack.push(ostack.getBacked(0));
	}

	public static void psyExit()
		throws PsyException
	{
		final var interpreter=psyCurrentContext();
		if(interpreter.currentLoopLevel()==-1)
			throw new PsyInvalidExitException();
		interpreter.executionStack().setSize(interpreter.popLoopLevel());
	}

	public static void psyDup()
		throws PsyException
	{
		final var ostack=psyCurrentContext().operandStack();
		ostack.ensureSize(1);
		ostack.push(ostack.peek());
	}

	public static void psyRoll(final PsyInteger oN, final PsyInteger oJ)
		throws PsyException
	{
		//final var ostack=psyCurrentContext().operandStackBacked(2);
		final var ostack=psyCurrentContext().operandStack();
		//final int n=ostack.<PsyInteger>getBacked(0).intValue();
		final var n=oN.intValue();
		//int j=ostack.<PsyInteger>getBacked(1).intValue();
		var j=oJ.intValue();
		// TODO ensureSize
		final int ostackSize=ostack.size();
		if(n<0)
			throw new PsyRangeCheckException();
		if(n==0)
			return;
		ostack.ensureSize(n);
		while(j<0)
			j+=n;
		j%=n;
		for(int i=0; i<j; i++)
			ostack.add(ostackSize-n, ostack.pop());
	}

	public static PsyObject psyIndex(PsyInteger oIndex)
		throws PsyException
	{
		int index=oIndex.intValue();
		if(index<0)
			throw new PsyRangeCheckException();
		final var ostack=psyCurrentContext().operandStack();
		ostack.ensureSize(index+1);
		return ostack.get(ostack.size()-index-1);
	}

	public static void psyCopy()
		throws PsyException
	{
		final var ostack=PsyContext.psyCurrentContext().operandStackBacked(1);
		final var count=ostack.<PsyInteger>getBacked(0).intValue();
		ostack.ensureSize(count);
		final var opsize=ostack.size();
		for(int j=opsize-count; j<opsize; j++)
			ostack.push(ostack.get(j));
	}

	public static void psyClearToMark()
		throws PsyException
	{
		final var ostack=PsyContext.psyCurrentContext().operandStack();
		ostack.setSize(ostack.findMarkPosition());
	}

	public static PsyInteger psyCountDictStack()
	{
		return PsyInteger.valueOf(psyCurrentContext().dictStack().size());
	}

	public static PsyInteger psyCountExecStack()
	{
		return PsyInteger.valueOf(psyCurrentContext().executionStack().size());
	}

	public static PsyInteger psyCountStack()
	{
		return PsyInteger.valueOf(psyCurrentContext().operandStack().size());
	}

	public static PsyInteger psyCountToMark()
		throws PsyException
	{
		final var ostack=psyCurrentContext().operandStack();
		return PsyInteger.valueOf(ostack.size()-ostack.findMarkPosition()-1);
	}

	public static void psyIf(final PsyBoolean oBoolean, final PsyObject o)
	{
		if(oBoolean.booleanValue())
			o.invoke();
	}

	public static void psyIfElse(final PsyBoolean oBoolean, final PsyObject o1, final PsyObject o2)
	{
		(oBoolean.booleanValue()? o1: o2).invoke();
	}

	public static void psyDef(final PsyStringy oKey, final PsyObject oValue)
	{
		PsyContext.psyCurrentContext().currentDict().psyPut(oKey, oValue);
	}

	public static PsyNamespace psyNamespace(final PsyStringy oPrefix)
	{
		return PsyContext.psyCurrentContext().namespacePool().get(oPrefix.stringValue());
	}

	public OperandStack operandStack();

	public DictStack dictStack();

	public ExecutionStack executionStack();

	public PsyDictlike systemDict();

	public PsyDictlike currentDict();

	public NamespacePool namespacePool();

	public int execLevel();

	public int pushLoopLevel();

	public int popLoopLevel();

	public int currentLoopLevel();

	public int pushStopLevel();

	public int popStopLevel();

	public int currentStopLevel();

	public boolean getStopFlag();

	public void setStopFlag(final boolean stopFlag);

	public void handleExecutionStack();

	public void handleExecutionStack(final int level);

	public void handleError(final PsyException oException);

	public void interpret(final PsyReader oReader)
		throws PsyException;

	public void interpretBraced(final PsyReader oReader)
		throws PsyException;

	public OperandStack operandStackBacked(final int count)
		throws PsyException;

	public PsyDictlike psyWhere(final PsyStringy oKey);

	public <T extends PsyObject> T psyLoad(final PsyStringy oKey)
		throws PsyException;

	public void psyExecutive()
		throws PsyException;

	public void psyRequire(final PsyStringy o)
		throws PsyException;

}
