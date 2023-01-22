package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code context}, an execution context.
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
		throws PsyErrorException;

	public void quit();

	public void stop_();

	public OperandStack operandStack();

	public DictStack dictStack();

	public ExecutionStack executionStack();

	public PsyFormalDict systemDict();

	public PsyFormalDict currentDict();

	public NamespacePool namespacePool();

	public int execLevel();

	public int pushLoopLevel();

	public int popLoopLevel();

	public int currentLoopLevel();

	public int pushStopLevel();

	public int popStopLevel();

	public int currentStopLevel();

	public boolean getStopFlag();

	public void repl()
		throws PsyErrorException;

	public void setStopFlag(final boolean stopFlag);

	public void handleExecutionStack();

	public void handleExecutionStack(final int level);

	//public void handleError(final PsyErrorException oException);

	public void interpret(final PsyReader oReader)
		throws PsyErrorException;

	public void interpretBraced(final PsyReader oReader)
		throws PsyErrorException;

	public OperandStack operandStackBacked(final int count)
		throws PsyErrorException;

	public PsyFormalDict psyWhere(final PsyTextual oKey);

	public <T extends PsyObject> T psyLoad(final PsyTextual oKey)
		throws PsyErrorException;

	public void psyRequire(final PsyTextual o)
		throws PsyErrorException;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Action
				("begin", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						oContext.dictStack().begin(ostack.getBacked(0));
					}),
			new PsyOperator.Action
				("cleardictstack", oContext->oContext.dictStack().setSize(2)),
			new PsyOperator.Action
				("clearstack", oContext->oContext.operandStack().clear()),
			new PsyOperator.Action
				("cleartomark", oContext->
					{
						final var ostack=oContext.operandStack();
						ostack.setSize(ostack.findMarkPosition());
					}),
			new PsyOperator.Action
				("copy", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						final int count=ostack.<PsyInteger>getBacked(0).intValue();
						if(count<0)
							throw new PsyRangeCheckException();
						ostack.ensureSize(count);
						final int opsize=ostack.size();
						for(int j=opsize-count; j<opsize; j++)
							ostack.push(ostack.get(j));
					}),
			new PsyOperator.Action
				("countdictstack", oContext->
					oContext.operandStack().push(PsyInteger.of(oContext.dictStack().size()))),
			new PsyOperator.Action
				("countexecstack", oContext->
					oContext.operandStack().push(PsyInteger.of(oContext.executionStack().size()))),
			new PsyOperator.Action
				("countstack", oContext->
					{
						final var ostack=oContext.operandStack();
						ostack.push(PsyInteger.of(ostack.size()));
					}),
			new PsyOperator.Action
				("counttomark", oContext->
					{
						final var ostack=oContext.operandStack();
						ostack.push(PsyInteger.of(-ostack.findMarkPosition()-1+ostack.size()));
					}),
			new PsyOperator.Action
				("currentcontext", oContext->oContext.operandStack().push(oContext)),
			new PsyOperator.Action
				("currentdict", oContext->
					{
						final var ostack=oContext.operandStack();
						ostack.push(oContext.currentDict());
					}),
			new PsyOperator.Action
				("def", oContext->
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
					}),
			new PsyOperator.Action
				("dictstack", oContext->
					{
						oContext.operandStack()
							.push(new PsyArray(
								(java.util.ArrayList<PsyObject>)oContext.dictStack().clone()));
					}),
			new PsyOperator.Action
				("dup", oContext->
					{
						final var ostack=oContext.operandStack();
						ostack.ensureSize(1);
						ostack.push(ostack.peek());
					}),
			new PsyOperator.Action
				("end", oContext->oContext.dictStack().end()),
			new PsyOperator.Action
				("exch", oContext->
					{
						final var ostack=oContext.operandStackBacked(2);
						ostack.push(ostack.getBacked(1));
						ostack.push(ostack.getBacked(0));
					}),
			new PsyOperator.Action
				("exec", oContext->oContext.operandStackBacked(1).getBacked(0).invoke(oContext)),
			new PsyOperator.Action
				("execstack", oContext->
					{
						oContext.operandStack().push(
							new PsyArray((java.util.ArrayList<PsyObject>)oContext.executionStack().clone()));
					}),
			new PsyOperator.Action
				("executive", oContext->oContext.repl()),
			new PsyOperator.Action
				("exit", oContext->
					{
						if(oContext.currentLoopLevel()==-1)
							throw new PsyInvalidExitException();
						oContext.executionStack().setSize(oContext.popLoopLevel());
					}),
			new PsyOperator.Action
				("for", oContext->
					{
						final var ostack=oContext.operandStackBacked(4);
						final var estack=oContext.executionStack();
						final PsyRealNumeric oInitial=ostack.getBacked(0);
						final PsyRealNumeric oIncrement=ostack.getBacked(1);
						final PsyRealNumeric oLimit=ostack.getBacked(2);
						final PsyObject oProc=ostack.getBacked(3);

						oContext.pushLoopLevel();
						//final boolean forward=oIncrement.psyGt(PsyInteger.ZERO).booleanValue();
						//estack.push(forward?
						estack.push(oIncrement.psyGt(PsyInteger.ZERO).booleanValue()?
							new PsyOperator("#for_continue")
								{
									private PsyRealNumeric oCounter=oInitial;

									@Override
									public void action(final PsyContext oContext)
									{
										if(oCounter.psyGt(oLimit).booleanValue())
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
									public void action(final PsyContext oContext)
									{
										if(oCounter.psyLt(oLimit).booleanValue())
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
						}),
			new PsyOperator.Action("fork", oContext->oContext.fork()),
			new PsyOperator.Action
				("halt", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						System.exit(ostack.<PsyInteger>getBacked(0).intValue());
					}),
			new PsyOperator.Action
				("if", oContext->
					{
						final var ostack=oContext.operandStackBacked(2);
						if(ostack.<PsyBoolean>getBacked(0).booleanValue())
							ostack.getBacked(1).invoke(oContext);
					}),
			new PsyOperator.Action
				("ifelse", oContext->
					{
						final var ostack=oContext.operandStackBacked(3);
						ostack.getBacked(ostack.<PsyBoolean>getBacked(0).booleanValue()? 1: 2)
							.invoke(oContext);
					}),
			new PsyOperator.Action
				("index", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						int index=ostack.<PsyInteger>getBacked(0).intValue();
						if(index<0)
							throw new PsyRangeCheckException();
						ostack.ensureSize(index+1);
						ostack.push(ostack.get(ostack.size()-index-1));
					}),
			new PsyOperator.Action
				("join", oContext->
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
					}),
			new PsyOperator.Action
				("load", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						ostack.push(oContext.psyLoad(ostack.getBacked(0)));
					}),
			new PsyOperator.Action
				("loop", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						final var oProc=ostack.getBacked(0);

						oContext.pushLoopLevel();
						oContext.executionStack().push(new PsyOperator("#loop_continue")
							{
								@Override
								public void action(final PsyContext oContext1)
									throws PsyErrorException
								{
									oContext1.executionStack().push(this);
									oProc.invoke(oContext1);
								}
							});
					}),
			new PsyOperator.Action
				("namespace", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						ostack.push(oContext.namespacePool().get(ostack.<PsyTextual>getBacked(0).stringValue()));
					}),
			new PsyOperator.Arity10<PsyObject>
				("pop", o->{}),
			new PsyOperator.Action
				("prettyprint", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						final var oStdWriter=(PsyWriter)oContext.dictStack().load("stdout");
						oStdWriter.psyWriteString(ostack.getBacked(0).psySyntax());
						oStdWriter.psyWriteString(oContext.dictStack().load("eol"));
						oStdWriter.psyFlush();
					}),
			new PsyOperator.Action
				("print", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						oContext.dictStack().<PsyWriter>load("stdout")
							.psyWriteString(ostack.getBacked(0));
					}),
			new PsyOperator.Action
				("quit", oContext->oContext.quit()),
			new PsyOperator.Action
				("repeat", oContext->
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
								public void action(final PsyContext oContext1)
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
					}),
			new PsyOperator.Action
				("require", oContext->
					{
						// TODO
						final var ostack=oContext.operandStackBacked(1);
						oContext.psyRequire(ostack.getBacked(0));
					}),
			new PsyOperator.Action
				("roll", oContext->
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
					}),
			new PsyOperator.Action
				("sleep", oContext->
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
					}),
			new PsyOperator.Action
				("stack", oContext->
					{
						final var ostack=oContext.operandStack();
						ostack.push(new PsyArray((java.util.ArrayList<PsyObject>)ostack.clone()));
					}),
			new PsyOperator.Action
				("stop", PsyContext::stop_),
			new PsyOperator.Action
				("stopped", oContext->
					{
						final var stopLevel=oContext.pushStopLevel();
						final var ostack=oContext.operandStackBacked(1);
						ostack.getBacked(0).invoke(oContext);
						oContext.handleExecutionStack(stopLevel);
						ostack.push(PsyBoolean.of(oContext.getStopFlag()));
						oContext.setStopFlag(false);
						oContext.popStopLevel();

						/*
						interpreter.pushStopLevel();
						interpreter.executionStack().push(new PsyOperator.Action
						("#stopped_continue", (interpreter1)->
						{
							ostack.push(PsyBoolean.of(interpreter1.getStopFlag()));
							interpreter1.setStopFlag(false);
							interpreter1.popStopLevel();
						}));
						oProc.invoke(interpreter);
						*/
					}),
			new PsyOperator.Action
				("store", oContext->
					{
						final var ostack=oContext.operandStackBacked(2);
						oContext.dictStack().psyStore(ostack.getBacked(0), ostack.getBacked(1));
					}),
			new PsyOperator.Action
				("tokens", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						oContext.interpretBraced(new PsyStringReader(ostack.<PsyTextual>getBacked(0)));
					}),
			new PsyOperator.Action
				("where", oContext->
					{
						final var ostack=oContext.operandStackBacked(1);
						final PsyFormalDict oDict=oContext.psyWhere(ostack.<PsyTextual>getBacked(0));
						if(oDict!=null)
							ostack.push(oDict);
						ostack.push(PsyBoolean.of(oDict!=null));
					}),
			new PsyOperator.Action
				("yield", oContext->((Thread)oContext).yield()),

		};
}
