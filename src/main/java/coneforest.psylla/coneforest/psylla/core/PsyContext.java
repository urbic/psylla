package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
*	The representation of {@code context}, an execution context.
*/
@Type("context")
public interface PsyContext
	extends PsyObject
{

	public long getId();

	public void join()
		throws InterruptedException;

	@Override
	default public String toSyntaxString()
	{
		return "%context="+getId()+"%";
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

	public void fork()
		throws PsyStackUnderflowException, PsyUnmatchedMarkException;

	public void quit();

	public void stop_();

	/**
	*	Returns the operand stack.
	*
	*	@return the operand stack.
	*/
	public OperandStack operandStack();

	/**
	*	Returns the dictionary stack.
	*
	*	@return the dictionary stack.
	*/
	public DictStack dictStack();

	/**
	*	Returns the execution stack.
	*
	*	@return the execution stack.
	*/
	public ExecutionStack executionStack();

	/**
	*	Returns the system dictionary.
	*
	*	@return the system dictionary.
	*/
	public PsyFormalDict systemDict();

	/**
	*	Returns the user dictionary.
	*
	*	@return the user dictionary.
	*/
	public PsyFormalDict userDict();

	/**
	*	Returns the current dictionary (the topmost on the dictionary stack).
	*
	*	@return the current dictionary.
	*/
	public PsyFormalDict currentDict();

	/**
	*	Returns the namespace pool.
	*
	*	@return the namespace pool.
	*/
	public NamespacePool namespacePool();

	/**
	*	Returns the size of the execution stack.
	*
	*	@return the size of the execution stack.
	*/
	public int execLevel();

	/**
	*	Pushes the current execution level onto the loop stack and returns this level.
	*
	*	@return the current execution level.
	*/
	public int pushLoopLevel();

	/**
	*	Removes the execution level from the loop stack and returns this level or {@code -1} if the
	*	loop stack is empty.
	*
	*	@return the execution level on the top of the loop stack or {@code -1} if the loop stack is
	*	empty.
	*/
	public int popLoopLevel();

	/**
	*	Returns the topmost execution level at the loop stack or {@code -1} if the loop stack is
	*	empty.
	*
	*	@return the topmost execution level at the loop stack or {@code -1} if the loop stack is
	*	empty.
	*/
	public int currentLoopLevel();

	/**
	*	Pushes the current execution level onto the stop stack and returns this level.
	*
	*	@return the current execution level.
	*/
	public int pushStopLevel();

	/**
	*	Removes the execution level from the stop stack and returns this level or {@code -1} if the
	*	stop stack is empty.
	*
	*	@return the execution level on the top of the stop stack or {@code -1} if the stop stack is
	*	empty.
	*/
	public int popStopLevel();

	/**
	*	Returns the topmost execution level at the stop stack or {@code -1} if the stop stack is
	*	empty.
	*
	*	@return the topmost execution level at the stop stack or {@code -1} if the stop stack is
	*	empty.
	*/
	public int currentStopLevel();

	public void repl()
		throws PsyErrorException;

	/**
	*	Gets the stopped flag to the specified value.
	*
	*	@return the value of the stopped flag.
	*/
	public boolean getStopped();

	/**
	*	Sets the stopped flag to the specified value.
	*
	*	@param stopFlag the value of the stopped flag.
	*/
	public void setStopped(final boolean stopFlag);

	public void handleExecutionStack(final int level);

	public void interpret(final PsyReader oReader);

	public void interpretBraced(final PsyReader oReader)
		throws PsyErrorException;

	public OperandStack operandStackBacked(final int count)
		throws PsyStackUnderflowException;

	default public Optional<PsyFormalDict> psyWhere(final PsyTextual oKey)
	{
		return dictStack().where(oKey.stringValue());
	}

	public <T extends PsyObject> T psyLoad(final PsyTextual oKey)
		throws PsyUndefinedException;

	public void psyRequire(final PsyTextual o)
		throws PsyErrorException;

	/**
	*	Context action of the {@code begin} operator.
	*/
	@OperatorType("begin")
	public static final ContextAction PSY_BEGIN=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			oContext.dictStack().begin(ostack.getBacked(0));
		};

	/**
	*	Context action of the {@code cleardictstack} operator.
	*/
	@OperatorType("cleardictstack")
	public static final ContextAction PSY_CLEARDICTSTACK=oContext->oContext.dictStack().setSize(2);

	/**
	*	Context action of the {@code clearstack} operator.
	*/
	@OperatorType("clearstack")
	public static final ContextAction PSY_CLEARSTACK=oContext->oContext.operandStack().clear();

	/**
	*	Context action of the {@code cleartomark} operator.
	*/
	@OperatorType("cleartomark")
	public static final ContextAction PSY_CLEARTOMARK=oContext->
		{
			final var ostack=oContext.operandStack();
			ostack.setSize(ostack.findMarkPosition());
		};

	/**
	*	Context action of the {@code copy} operator.
	*/
	@OperatorType("copy")
	public static final ContextAction PSY_COPY=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			final int count=ostack.<PsyInteger>getBacked(0).intValue();
			if(count<0)
				throw new PsyRangeCheckException();
			ostack.ensureSize(count);
			final int opsize=ostack.size();
			for(int j=opsize-count; j<opsize; j++)
				ostack.push(ostack.get(j));
		};

	/**
	*	Context action of the {@code countdictstack} operator.
	*/
	@OperatorType("countdictstack")
	public static final ContextAction PSY_COUNTDICTSTACK=oContext->
		oContext.operandStack().push(PsyInteger.of(oContext.dictStack().size()));

	/**
	*	Context action of the {@code countexecstack} operator.
	*/
	@OperatorType("countexecstack")
	public static final ContextAction PSY_COUNTEXECSTACK=oContext->
		oContext.operandStack().push(PsyInteger.of(oContext.executionStack().size()));

	/**
	*	Context action of the {@code countstack} operator.
	*/
	@OperatorType("countstack")
	public static final ContextAction PSY_COUNTSTACK=oContext->
		{
			final var ostack=oContext.operandStack();
			ostack.push(PsyInteger.of(ostack.size()));
		};

	/**
	*	Context action of the {@code counttomark} operator.
	*/
	@OperatorType("counttomark")
	public static final ContextAction PSY_COUNTTOMARK=oContext->
		{
			final var ostack=oContext.operandStack();
			ostack.push(PsyInteger.of(-ostack.findMarkPosition()-1+ostack.size()));
		};

	/**
	*	Context action of the {@code currentcontext} operator.
	*/
	@OperatorType("currentcontext")
	public static final ContextAction PSY_CURRENTCONTEXT=oContext->
		oContext.operandStack().push(oContext);

	/**
	*	Context action of the {@code currentdict} operator.
	*/
	@OperatorType("currentdict")
	public static final ContextAction PSY_CURRENTDICT=oContext->
		{
			final var ostack=oContext.operandStack();
			ostack.push(oContext.currentDict());
		};

	/**
	*	Context action of the {@code def} operator.
	*/
	@OperatorType("def")
	public static final ContextAction PSY_DEF=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			final var oName=ostack.<PsyTextual>getBacked(0);
			final var name=oName.stringValue();
			final var o=ostack.getBacked(1);
			final var prefixOffset=name.indexOf('@');
			if(prefixOffset==-1)
				oContext.currentDict().psyPut(oName, o);
			else
				oContext.namespacePool().get(name.substring(0, prefixOffset))
					.put(name.substring(prefixOffset+1), o);
		};

	/**
	*	Context action of the {@code dictstack} operator.
	*/
	@OperatorType("dictstack")
	public static final ContextAction PSY_DICTSTACK=oContext->
		oContext.operandStack().push(new PsyArray((ArrayList<PsyObject>)oContext.dictStack().clone()));

	/**
	*	Context action of the {@code dup} operator.
	*/
	@OperatorType("dup")
	public static final ContextAction PSY_DUP=oContext->
		{
			final var ostack=oContext.operandStack();
			ostack.ensureSize(1);
			ostack.push(ostack.peek());
		};

	/**
	*	Context action of the {@code editline} operator.
	*/
	@OperatorType("edtline")
	public static final ContextAction PSY_EDITLINE=oContext->
		{
			try
			{
				final var ostack=oContext.operandStack();
				final jline.ConsoleReader consoleReader=new jline.ConsoleReader();
				final var line=consoleReader.readLine();
				if(line!=null)
					ostack.push(new PsyString(line+"\n"));
				ostack.push(PsyBoolean.of(line!=null));
			}
			catch(final IOException e)
			{
				throw new PsyIOErrorException();
			}
		};

	/**
	*	Context action of the {@code end} operator.
	*/
	@OperatorType("end")
	public static final ContextAction PSY_END=oContext->oContext.dictStack().end();

	/**
	*	Context action of the {@code exch} operator.
	*/
	@OperatorType("exch")
	public static final ContextAction PSY_EXCH=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			ostack.push(ostack.getBacked(1));
			ostack.push(ostack.getBacked(0));
		};

	/**
	*	Context action of the {@code exec} operator.
	*/
	@OperatorType("exec")
	public static final ContextAction PSY_EXEC=oContext->
		oContext.operandStackBacked(1).getBacked(0).invoke(oContext);

	/**
	*	Context action of the {@code execstack} operator.
	*/
	@OperatorType("execstack")
	public static final ContextAction PSY_EXECSTACK=oContext->
		oContext.operandStack().push(new PsyArray((ArrayList<PsyObject>)oContext.executionStack().clone()));

	/**
	*	Context action of the {@code executive} operator.
	*/
	@OperatorType("executive")
	public static final ContextAction PSY_EXECUTIVE=oContext->oContext.repl();

	/**
	*	Context action of the {@code exit} operator.
	*/
	@OperatorType("exit")
	public static final ContextAction PSY_EXIT=oContext->
		{
			if(oContext.currentLoopLevel()==-1)
				throw new PsyInvalidExitException();
			oContext.executionStack().setSize(oContext.popLoopLevel());
		};

	/**
	*	Context action of the {@code for} operator.
	*/
	@OperatorType("for")
	public static final ContextAction PSY_FOR=oContext->
		{
			final var ostack=oContext.operandStackBacked(4);
			final var estack=oContext.executionStack();
			final PsyRealNumeric oInitial=ostack.getBacked(0);
			final PsyRealNumeric oIncrement=ostack.getBacked(1);
			final PsyRealNumeric oLimit=ostack.getBacked(2);
			final PsyObject oProc=ostack.getBacked(3);

			oContext.pushLoopLevel();
			estack.push(oIncrement.compareTo(PsyInteger.ZERO)>0?
				new PsyOperator("#for_continue")
					{
						private PsyRealNumeric oCounter=oInitial;

						@Override
						public void perform(final PsyContext oContext)
						{
							if(oCounter.compareTo(oLimit)>0)
								oContext.popLoopLevel();
							else
							{
								estack.push(this);
								ostack.push(oCounter);
								oCounter=(PsyRealNumeric)oCounter.psyAdd(oIncrement);
								oProc.invoke(oContext);
							}
						}
					}:
				new PsyOperator("#for_continue")
					{
						private PsyRealNumeric oCounter=oInitial;

						@Override
						public void perform(final PsyContext oContext)
						{
							if(oCounter.compareTo(oLimit)<0)
								oContext.popLoopLevel();
							else
							{
								estack.push(this);
								ostack.push(oCounter);
								oCounter=(PsyRealNumeric)oCounter.psyAdd(oIncrement);
								oProc.invoke(oContext);
							}
						}
					});
			};

	/**
	*	Context action of the {@code fork} operator.
	*/
	@OperatorType("fork")
	public static final ContextAction PSY_FORK=oContext->oContext.fork();

	/**
	*	Context action of the {@code halt} operator.
	*/
	@OperatorType("halt")
	public static final ContextAction PSY_HALT=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			System.exit(ostack.<PsyInteger>getBacked(0).intValue());
		};

	/**
	*	Context action of the {@code if} operator.
	*/
	@OperatorType("if")
	public static final ContextAction PSY_IF=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			if(ostack.<PsyBoolean>getBacked(0).booleanValue())
				ostack.getBacked(1).invoke(oContext);
		};

	/**
	*	Context action of the {@code ifelse} operator.
	*/
	@OperatorType("ifelse")
	public static final ContextAction PSY_IFELSE=oContext->
		{
			final var ostack=oContext.operandStackBacked(3);
			ostack.getBacked(ostack.<PsyBoolean>getBacked(0).booleanValue()? 1: 2)
				.invoke(oContext);
		};

	/**
	*	Context action of the {@code index} operator.
	*/
	@OperatorType("index")
	public static final ContextAction PSY_INDEX=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			int index=ostack.<PsyInteger>getBacked(0).intValue();
			if(index<0)
				throw new PsyRangeCheckException();
			ostack.ensureSize(index+1);
			ostack.push(ostack.get(ostack.size()-index-1));
		};

	/**
	*	Context action of the {@code join} operator.
	*/
	@OperatorType("join")
	public static final ContextAction PSY_JOIN=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			final var oContextJoining=ostack.<PsyContext>getBacked(0);
			if(oContextJoining==oContext)
				throw new PsyInvalidContextException();
			try
			{
				oContextJoining.join();
			}
			catch(final InterruptedException ex)
			{
				throw new PsyInterruptException();
			}
			final var ostackJoining=oContextJoining.operandStack();
			ostack.push(PsyMark.MARK);
			for(final PsyObject o: ostackJoining)
				ostack.push(o);
		};

	/**
	*	Context action of the {@code load} operator.
	*/
	@OperatorType("load")
	public static final ContextAction PSY_LOAD=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			ostack.push(oContext.psyLoad(ostack.getBacked(0)));
		};

	/**
	*	Context action of the {@code loop} operator.
	*/
	@OperatorType("loop")
	public static final ContextAction PSY_LOOP=oContext->
		{
			final var oProc=oContext.operandStackBacked(1).getBacked(0);

			oContext.pushLoopLevel();
			oContext.executionStack().push(new PsyOperator("#loop_continue")
				{
					@Override
					public void perform(final PsyContext oContext1)
						throws PsyErrorException
					{
						oContext1.executionStack().push(this);
						oProc.invoke(oContext1);
					}
				});
		};

	/**
	*	Context action of the {@code namespace} operator.
	*/
	@OperatorType("namespace")
	public static final ContextAction PSY_NAMESPACE=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			ostack.push(oContext.namespacePool().get(ostack.<PsyTextual>getBacked(0).stringValue()));
		};

	/**
	*	Context action of the {@code pop} operator.
	*/
	@OperatorType("pop")
	public static final ContextAction PSY_POP=
		ContextAction.<PsyObject>ofConsumer(o->{});

	/**
	*	Context action of the {@code prettyprint} operator.
	*/
	@OperatorType("prettyprint")
	public static final ContextAction PSY_PRETTYPRINT=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			final var oStdWriter=(PsyWriter)oContext.dictStack().load("stdout");
			oStdWriter.psyWriteString(ostack.getBacked(0).psySyntax());
			oStdWriter.psyWriteString(oContext.dictStack().load("eol"));
			oStdWriter.psyFlush();
		};

	/**
	*	Context action of the {@code print} operator.
	*/
	@OperatorType("print")
	public static final ContextAction PSY_PRINT=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			oContext.dictStack().<PsyWriter>load("stdout").psyWriteString(ostack.getBacked(0));
		};

	/**
	*	Context action of the {@code quit} operator.
	*/
	@OperatorType("quit")
	public static final ContextAction PSY_QUIT=oContext->oContext.quit();

	/**
	*	Context action of the {@code repeat} operator.
	*/
	@OperatorType("repeat")
	public static final ContextAction PSY_REPEAT=oContext->
		{
			final OperandStack ostack=oContext.operandStackBacked(2);
			final ExecutionStack estack=oContext.executionStack();
			final PsyInteger oCount=ostack.getBacked(0);
			final PsyObject oProc=ostack.getBacked(1);
			final long count=oCount.longValue();

			if(count<0)
				throw new PsyRangeCheckException();
			if(count==0)
				return;

			oContext.pushLoopLevel();
			oContext.executionStack().push(new PsyOperator("#repeat_continue")
				{
					private long count1=count;

					@Override
					public void perform(final PsyContext oContext1)
					{
						if(count1--==0)
							oContext1.popLoopLevel();
						else
						{
							estack.push(this);
							oProc.invoke(oContext1);
						}
					}
				});
		};

	/**
	*	Context action of the {@code require} operator.
	*/
	@OperatorType("require")
	public static final ContextAction PSY_REQUIRE=oContext->
		{
			// TODO
			final var ostack=oContext.operandStackBacked(1);
			oContext.psyRequire(ostack.getBacked(0));
		};

	/**
	*	Context action of the {@code roll} operator.
	*/
	@OperatorType("roll")
	public static final ContextAction PSY_ROLL=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			final var n=ostack.<PsyInteger>getBacked(0).intValue();
			int j=ostack.<PsyInteger>getBacked(1).intValue();
			final var ostackSize=ostack.size();
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
		};

	/**
	*	Context action of the {@code say} operator.
	*/
	@OperatorType("say")
	public static final ContextAction PSY_SAY=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			final var stdwriter=(PsyWriter)oContext.dictStack().load("stdout");
			stdwriter.psyWriteString(ostack.getBacked(0));
			stdwriter.psyWriteString((PsyTextual)oContext.dictStack().load("eol"));
			stdwriter.psyFlush();
		};

	/**
	*	Context action of the {@code sleep} operator.
	*/
	@OperatorType("sleep")
	public static final ContextAction PSY_SLEEP=oContext->
		{
			try
			{
				Thread.sleep(oContext.operandStackBacked(1).<PsyInteger>getBacked(0).longValue());
			}
			catch(final IllegalArgumentException ex)
			{
				throw new PsyRangeCheckException();
			}
			catch(final InterruptedException ex)
			{
				throw new PsyInterruptException();
			}
		};

	/**
	*	Context action of the {@code stack} operator.
	*/
	@OperatorType("stack")
	public static final ContextAction PSY_STACK=oContext->
		{
			final var ostack=oContext.operandStack();
			ostack.push(new PsyArray((ArrayList<PsyObject>)ostack.clone()));
		};

	/**
	*	Context action of the {@code stop} operator.
	*/
	@OperatorType("stop")
	public static final ContextAction PSY_STOP=PsyContext::stop_;

	/**
	*	Context action of the {@code stopped} operator.
	*/
	@OperatorType("stopped")
	public static final ContextAction PSY_STOPPED=oContext->
		{
			final var stopLevel=oContext.pushStopLevel();
			final var ostack=oContext.operandStackBacked(1);
			ostack.getBacked(0).invoke(oContext);
			oContext.handleExecutionStack(stopLevel);
			ostack.push(PsyBoolean.of(oContext.getStopped()));
			oContext.setStopped(false);
			oContext.popStopLevel();

			/*
			interpreter.pushStopLevel();
			interpreter.executionStack().push(new PsyOperator.Action
			("#stopped_continue", (interpreter1)->
			{
				ostack.push(PsyBoolean.of(interpreter1.getStopped()));
				interpreter1.setStopped(false);
				interpreter1.popStopLevel();
			}));
			oProc.invoke(interpreter);
			*/
		};

	/**
	*	Context action of the {@code store} operator.
	*/
	@OperatorType("store")
	public static final ContextAction PSY_STORE=oContext->
		{
			final var ostack=oContext.operandStackBacked(2);
			oContext.dictStack().store(ostack.getBacked(0), ostack.getBacked(1));
		};

	/**
	*	Context action of the {@code tokens} operator.
	*/
	@OperatorType("tokens")
	public static final ContextAction PSY_TOKENS=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			oContext.interpretBraced(new PsyStringReader(ostack.<PsyTextual>getBacked(0)));
		};

	/**
	*	Context action of the {@code warn} operator.
	*/
	@OperatorType("warn")
	public static final ContextAction PSY_WARN=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			final PsyWriter stderror=oContext.dictStack().load("stderr");
			stderror.psyWriteString(ostack.getBacked(0));
			stderror.psyFlush();
		};

	/**
	*	Context action of the {@code where} operator.
	*/
	@OperatorType("where")
	public static final ContextAction PSY_WHERE=oContext->
		{
			final var ostack=oContext.operandStackBacked(1);
			ostack.pushOptional(oContext.psyWhere(ostack.<PsyTextual>getBacked(0)));
		};

	/**
	*	Context action of the {@code yield} operator.
	*/
	@OperatorType("yield")
	public static final ContextAction PSY_YIELD=oContext->((Thread)oContext).yield();
}
