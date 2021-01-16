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

	public PsyDictlike psyWhere(final PsyStringy oKey);
	
	public <T extends PsyObject> T psyLoad(final PsyStringy oKey)
		throws PsyException;

	public void psyRequire(final PsyStringy o)
		throws PsyException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Action("begin",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					interpreter.dictStack().psyBegin(ostack.getBacked(0));
				}),
			new PsyOperator.Action("cleardictstack", (interpreter)->interpreter.dictStack().setSize(2)),
			new PsyOperator.Action("clearstack", (interpreter)->interpreter.operandStack().clear()),
			new PsyOperator.Action("cleartomark",
				(interpreter)->
				{
					final var ostack=interpreter.operandStack();
					ostack.setSize(ostack.findMarkPosition());
				}),
			new PsyOperator.Action("copy",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					final var count=ostack.<PsyInteger>getBacked(0).intValue();
					if(count<0)
						throw new PsyRangeCheckException();
					ostack.ensureSize(count);
					final var opsize=ostack.size();
					for(int j=opsize-count; j<opsize; j++)
						ostack.push(ostack.get(j));
				}),
			new PsyOperator.Action("countdictstack",
				(interpreter)->
					interpreter.operandStack().push(PsyInteger.valueOf(interpreter.dictStack().size()))),
			new PsyOperator.Action("countexecstack",
				(interpreter)->
					interpreter.operandStack().push(PsyInteger.valueOf(interpreter.executionStack().size()))),
			new PsyOperator.Action("countstack",
				(interpreter)->
				{
					final var ostack=interpreter.operandStack();
					ostack.push(PsyInteger.valueOf(ostack.size()));
				}),	
			new PsyOperator.Action("counttomark",
				(interpreter)->
				{
					final var ostack=interpreter.operandStack();
					ostack.push(PsyInteger.valueOf(-ostack.findMarkPosition()-1+ostack.size()));
				}),
			new PsyOperator.Action("currentcontext", (interpreter)->interpreter.operandStack().push(interpreter)),
			new PsyOperator.Action("def",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(2);
					interpreter.currentDict().psyPut(ostack.getBacked(0), ostack.getBacked(1));
				}),
			new PsyOperator.Action("dictstack",
				(interpreter)->
					interpreter.operandStack()
						.push(new PsyArray((java.util.ArrayList<PsyObject>)interpreter.dictStack().clone()))),
			new PsyOperator.Action("dup",
				(interpreter)->
				{
					final var ostack=interpreter.operandStack();
					ostack.ensureSize(1);
					ostack.push(ostack.peek());
				}),
			new PsyOperator.Action("end", (interpreter)->interpreter.dictStack().psyEnd()),
			new PsyOperator.Action("exch",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(2);
					ostack.push(ostack.getBacked(1));
					ostack.push(ostack.getBacked(0));
				}),
			new PsyOperator.Action("exec",
				(interpreter)->interpreter.operandStackBacked(1).getBacked(0).invoke(interpreter)),
			new PsyOperator.Action("execstack",
				(interpreter)->
					interpreter.operandStack().push(
						new PsyArray((java.util.ArrayList<PsyObject>)interpreter.executionStack().clone()))),
			new PsyOperator.Action("executive", (interpreter)->interpreter.repl()),
			new PsyOperator.Action("exit",
				(interpreter)->
				{
					if(interpreter.currentLoopLevel()==-1)
						throw new PsyInvalidExitException();
					interpreter.executionStack().setSize(interpreter.popLoopLevel());
				}),
			new PsyOperator.Action("for",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(4);
					final var estack=interpreter.executionStack();
					final PsyRealNumeric oInitial=ostack.getBacked(0);
					final PsyRealNumeric oIncrement=ostack.getBacked(1);
					final PsyRealNumeric oLimit=ostack.getBacked(2);
					final PsyObject oProc=ostack.getBacked(3);
				
					interpreter.pushLoopLevel();
					final boolean forward=oIncrement.psyGt(PsyInteger.ZERO).booleanValue();
					estack.push(forward?
						new PsyOperator("#for_continue")
							{
								private PsyRealNumeric oCounter=oInitial;
								
								@Override
								public void action(final Interpreter interpreter1)
								{
									if(oCounter.psyGt(oLimit).booleanValue())
										interpreter1.popLoopLevel();
									else
									{
										estack.push(this);
										ostack.push(oCounter);
										oCounter=(PsyRealNumeric)oCounter.psyAdd(oIncrement);
										oProc.invoke(interpreter1);
									}
								}
							}:
						new PsyOperator("#for_continue")
							{
								private PsyRealNumeric oCounter=oInitial;
									
								@Override
								public void action(final Interpreter interpreter1)
								{
									if(oCounter.psyLt(oLimit).booleanValue())
										interpreter1.popLoopLevel();
									else
									{
										estack.push(this);
										ostack.push(oCounter);
										oCounter=(PsyRealNumeric)oCounter.psyAdd(oIncrement);
										oProc.invoke(interpreter1);
									}
								}
							});
					}),
			new PsyOperator.Action("fork",
				(interpreter)->
					// TODO; error handling in forked context
				{
					final var ostack=interpreter.operandStackBacked(1);
					
					//ostack.ensureSize(2);
					final PsyObject o=ostack.getBacked(0);
					
					final Interpreter forkedInterpreter=new Interpreter(interpreter.dictStack())
						{
							@Override
							public void run()
							{
								o.invoke(this);
								handleExecutionStack();
								if(getStopFlag())
								{
									PsyErrorDict.OP_HANDLEERROR.invoke(this);
									return;
								}
							}
						};
					final int i=ostack.findMarkPosition();
					final int ostackSize=ostack.size();
					final var forkedOstack=forkedInterpreter.operandStack();
					for(int j=i+1; j<ostackSize; j++)
						forkedOstack.push(ostack.get(j));
					ostack.setSize(i);
					ostack.push(forkedInterpreter);
					forkedInterpreter.start();
				}),
			new PsyOperator.Action("halt",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					System.exit(ostack.<PsyInteger>getBacked(0).intValue());
				}),
			new PsyOperator.Action("if",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(2);
					if(ostack.<PsyBoolean>getBacked(0).booleanValue())
						ostack.getBacked(1).invoke(interpreter);
				}),
			new PsyOperator.Action("ifelse",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(3);
					ostack.getBacked(ostack.<PsyBoolean>getBacked(0).booleanValue()? 1: 2)
							.invoke(interpreter);
				}),
			new PsyOperator.Action("index",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					int index=ostack.<PsyInteger>getBacked(0).intValue();
					if(index<0)
						throw new PsyRangeCheckException();
					ostack.ensureSize(index+1);
					ostack.push(ostack.get(ostack.size()-index-1));
				}),
			new PsyOperator.Action("join",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					final PsyContext oContext=ostack.getBacked(0);
					oContext.psyJoin();
					final var joinedOstack=((Interpreter)oContext).operandStack();
					ostack.push(PsyMark.MARK);
					for(final PsyObject o: joinedOstack)
						ostack.push(o);
				}),
			new PsyOperator.Action("load",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					ostack.push(interpreter.psyLoad(ostack.getBacked(0)));
				}),
			new PsyOperator.Action("loop",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					final PsyObject oProc=ostack.getBacked(0);
					
					interpreter.pushLoopLevel();
					interpreter.executionStack().push(new PsyOperator("#loop_continue")
					{
						@Override
						public void action(final Interpreter interpreter1)
							throws PsyException
						{
							interpreter1.executionStack().push(this);
							oProc.invoke(interpreter1);
						}
					});
				}),
			new PsyOperator.Arity10<PsyObject>("pop", (a)->{}),
			new PsyOperator.Action("quit", (interpreter)->interpreter.quit()),
			new PsyOperator.Action("repeat",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(2);
					final var estack=interpreter.executionStack();
					final PsyInteger oCount=ostack.getBacked(0);
					final PsyObject oProc=ostack.getBacked(1);
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
							public void action(final Interpreter interpreter1)
							{
								if(count1--==0)
									interpreter1.popLoopLevel();
								else
								{
									estack.push(this);
									oProc.invoke(interpreter1);
								}
							}
						});
				}),
			new PsyOperator.Action("require",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					interpreter.psyRequire(ostack.getBacked(0));
				}),
			new PsyOperator.Action("roll",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(2);
					final int n=ostack.<PsyInteger>getBacked(0).intValue();
					int j=ostack.<PsyInteger>getBacked(1).intValue();
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
				}),
			new PsyOperator.Arity10<PsyInteger>("sleep", PsyContext::psySleep),
			new PsyOperator.Action("stack",
				(interpreter)->
				{
					final var ostack=interpreter.operandStack();
					ostack.push(new PsyArray((java.util.ArrayList<PsyObject>)ostack.clone()));
				}),
			new PsyOperator.Action("stop", (interpreter)->interpreter.psyStop()),
			new PsyOperator.Action("stopped",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					final PsyObject oProc=ostack.getBacked(0);
					
					final int stopLevel=interpreter.pushStopLevel();
					oProc.invoke(interpreter);
					interpreter.handleExecutionStack(stopLevel);
					ostack.push(PsyBoolean.valueOf(interpreter.getStopFlag()));
					interpreter.setStopFlag(false);
					interpreter.popStopLevel();
					
					/*
					interpreter.pushStopLevel();
					interpreter.executionStack().push(new PsyOperator.Action
					("#stopped_continue", (interpreter1)->
					{
						ostack.push(PsyBoolean.valueOf(interpreter1.getStopFlag()));
						interpreter1.setStopFlag(false);
						interpreter1.popStopLevel();
					}));
					oProc.invoke(interpreter);
					*/
				}),
			new PsyOperator.Action("store",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(2);
					interpreter.dictStack().psyStore(ostack.getBacked(0), ostack.getBacked(1));
				}),
			new PsyOperator.Action("tokens",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					interpreter.interpretBraced(new PsyStringReader(ostack.<PsyStringy>getBacked(0)));
				}),
			new PsyOperator.Action("where",
				(interpreter)->
				{
					final var ostack=interpreter.operandStackBacked(1);
					final PsyDictlike dict=interpreter.psyWhere(ostack.<PsyStringy>getBacked(0));
					if(dict!=null)
						ostack.push(dict);
					ostack.push(PsyBoolean.valueOf(dict!=null));
				}),
			new PsyOperator.Action("yield", (interpreter)->Thread.yield()),
		};

}
