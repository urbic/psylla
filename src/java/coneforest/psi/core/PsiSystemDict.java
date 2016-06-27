package coneforest.psi.core;
import coneforest.psi.*;

public class PsiSystemDict
	extends PsiModule
{
	public PsiSystemDict()
		throws PsiException
	{
		registerOperators
			(
				new PsiOperator.Arity11
					("abs", (a)->((PsiComplexNumeric)a).psiAbs()),
				new PsiOperator.Arity11
					("acos", (a)->((PsiComplexNumeric)a).psiAcos()),
				new PsiOperator.Arity21
					("add", (a, b)->((PsiAdditive)a).psiAdd((PsiAdditive)b)),
				new PsiOperator.Arity21
					("and", (a, b)->((PsiLogical)a).psiAnd((PsiLogical)b)),
				new PsiOperator.Arity20
					("append", (a, b)->((PsiAppendable)a).psiAppend(b)),
				new PsiOperator.Arity20
					("appendall", (a, b)->((PsiAppendable)a).psiAppendAll((PsiIterable)b)),
				new PsiOperator.Arity11
					("arg", (a)->((PsiComplexNumeric)a).psiArg()),
				new PsiOperator.Arity01
					("array", PsiArray::new),
				new PsiOperator.Action
					("arraytomark",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final int i=ostack.findMarkPosition();
							final PsiArray oArray=new PsiArray();
							for(int j=i+1; j<ostack.size(); j++)
								oArray.psiAppend(ostack.get(j));
							ostack.setSize(i);
							ostack.push(oArray);
						}
					),
				new PsiOperator.Arity11
					("asin", (a)->((PsiComplexNumeric)a).psiAsin()),
				new PsiOperator.Action
					("astore",
						(interpreter)->
						// TODO
						{
							final OperandStack ostack=interpreter.operandStack();
							int countValue=((PsiInteger)ostack.popOperands(1)[0]).intValue();
							ostack.ensureSize(countValue);
							PsiArray array=new PsiArray();
							while(--countValue>=0)
								((PsiArray)array).psiAppend(ostack.pop());
							ostack.push(array);
						}
					),
				new PsiOperator.Arity11
					("atan", (a)->((PsiComplexNumeric)a).psiAtan()),
				new PsiOperator.Action
					("begin",
						(interpreter)->
						interpreter.dictStack().push((PsiDictlike)interpreter.operandStack().popOperands(1)[0])),
				new PsiOperator.Action
					("binarysearch",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(3);
							final PsiArray array=(PsiArray)ops[0];
							final PsiObject key=ops[1];
							final PsiProc comparator=(PsiProc)ops[2];

							final PsiInteger index=array.psiBinarySearch(key, comparator);
							final int indexValue=index.intValue();
							if(indexValue>=0)
							{
								ostack.push(index);
								ostack.push(PsiBoolean.TRUE);
							}
							else
							{
								ostack.push(PsiInteger.valueOf(-indexValue-1));
								ostack.push(PsiBoolean.FALSE);
							}
						}
					),
				new PsiOperator.Arity11
					("bind", (a)->((PsiProc)a).psiBind()),
				new PsiOperator.Arity01
					("bitset", PsiBitSet::new),
				new PsiOperator.Arity01
					("bitvector", PsiBitVector::new),
				new PsiOperator.Arity21
					("bitshift", (a, b)->((PsiBitwise)a).psiBitShift((PsiInteger)b)),
				new PsiOperator.Arity11
					("blockingqueue", (a)->new PsiBlockingQueue((PsiInteger)a)),
				new PsiOperator.Arity11
					("calendar", (a)->Time.psiCalendar((PsiInteger)a)),
				new PsiOperator.Arity11
					("calendartime", (a)->Time.psiCalendarTime((PsiDictlike)a)),
				new PsiOperator.Arity11
					("capacity", (a)->((PsiBounded)a).psiCapacity()),
				new PsiOperator.Action
					("capturegroup",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final PsiString oGroup=((PsiMatcher)ops[0]).psiCaptureGroup(ops[1]);
							if(oGroup!=null)
								ostack.push(oGroup);
							ostack.push(PsiBoolean.valueOf(oGroup!=null));
						}
					),
				new PsiOperator.Action
					("capturegroupend",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final PsiInteger oGroupEnd=((PsiMatcher)ops[0]).psiCaptureGroupEnd((PsiInteger)ops[1]);
							if(oGroupEnd!=null)
								ostack.push(oGroupEnd);
							ostack.push(PsiBoolean.valueOf(oGroupEnd!=null));
						}
					),
				new PsiOperator.Action
					("capturegroupstart",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final PsiInteger oGroupStart=((PsiMatcher)ops[0]).psiCaptureGroupStart((PsiInteger)ops[1]);
							if(oGroupStart!=null)
								ostack.push(oGroupStart);
							ostack.push(PsiBoolean.valueOf(oGroupStart!=null));
						}
					),
				new PsiOperator.Arity11
					("capturegroupcount", (a)->((PsiMatcher)a).psiCaptureGroupCount()),
				new PsiOperator.Arity11
					("cbrt", (a)->((PsiComplexNumeric)a).psiCbrt()),
				new PsiOperator.Arity11
					("ceiling", (a)->((PsiNumeric)a).psiCeiling()),
				new PsiOperator.Arity10
					("clear", (a)->((PsiClearable)a).psiClear()),
				new PsiOperator.Arity21
					("clearbit", (a, b)->((PsiBitwise)a).psiClearBit((PsiInteger)b)),
				new PsiOperator.Action
					("cleardictstack", (interpreter)->interpreter.dictStack().setSize(2)),
				new PsiOperator.Action
					("clearstack", (interpreter)->interpreter.operandStack().clear()),
				new PsiOperator.Action
					("cleartomark",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.setSize(ostack.findMarkPosition());
						}
					),
				new PsiOperator.Arity11
					("clone", (a)->a.psiClone()),
				new PsiOperator.Arity10
					("close", (a)->((PsiCloseable)a).psiClose()),
				new PsiOperator.Arity21
					("cmp", (a, b)->((PsiScalar)a).psiCmp((PsiScalar)b)),
				new PsiOperator.Arity21
					("complex", (a, b)->new PsiComplex((PsiNumeric)a, (PsiNumeric)b)),
				new PsiOperator.Arity21
					("complexpolar", (a, b)->PsiComplex.psiFromPolar((PsiNumeric)a, (PsiNumeric)b)),
				new PsiOperator.Arity11
					("condition", (a)->((PsiLock)a).psiCondition()),
				new PsiOperator.Arity11
					("conjugate", (a)->((PsiComplexNumeric)a).psiConjugate()),
				new PsiOperator.Arity21
					("contains", (a, b)->((PsiSetlike)a).psiContains(b)),
				//new PsiOperator.Arity21
				//	("convert", (a, b)->a.psiConvert((PsiType)b)),
				new PsiOperator.Action
					("copy",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final int n=((PsiInteger)ostack.popOperands(1)[0]).intValue();
							if(n<0)
								throw new PsiRangeCheckException();
							ostack.ensureSize(n);
							final int opsize=ostack.size();
							for(int j=opsize-n; j<opsize; j++)
								ostack.push(ostack.get(j));
						}
					),
				new PsiOperator.Arity20
					("copyfile", (a, b)->FileSystem.psiCopyFile((PsiStringy)a, (PsiStringy)b)),
				new PsiOperator.Arity11
					("cos", (a)->((PsiComplexNumeric)a).psiCos()),
				new PsiOperator.Arity11
					("cosh", (a)->((PsiComplexNumeric)a).psiCosh()),
				new PsiOperator.Action
					("countdictstack",
						(interpreter)->
						interpreter.operandStack().push(PsiInteger.valueOf(interpreter.dictStack().size()))),
				new PsiOperator.Action
					("countexecstack",
						(interpreter)->
						interpreter.operandStack().push(PsiInteger.valueOf(interpreter.executionStack().size()))),
				new PsiOperator.Action
					("countstack",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(PsiInteger.valueOf(ostack.size()));
						}
					),
				new PsiOperator.Action
					("counttomark",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(PsiInteger.valueOf(-ostack.findMarkPosition()-1+ostack.size()));
						}
					),
				new PsiOperator.Arity10
					("createdirectory", (a)->FileSystem.psiCreateDirectory((PsiStringy)a)),
				new PsiOperator.Action
					("currentcontext", (interpreter)->interpreter.operandStack().push(interpreter)),
				new PsiOperator.Action
					("currentdict", (interpreter)->interpreter.operandStack().push(interpreter.getCurrentDict())),
				new PsiOperator.Arity01
					("currentdirectory", FileSystem::psiCurrentDirectory),
				new PsiOperator.Action
					("def",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(2);
							interpreter.getCurrentDict().psiPut((PsiStringy)ops[0], ops[1]);
						}
					),
				new PsiOperator.Arity20
					("delete", (a, b)->((PsiIndexed)a).psiDelete(b)),
				new PsiOperator.Arity10
					("deletefile", (a)->FileSystem.psiDeleteFile((PsiStringy)a)),
				new PsiOperator.Arity11
					("dequeue", (a)->((PsiQueuelike)a).psiDequeue()),
				new PsiOperator.Arity01
					("dict", PsiDict::new),
				new PsiOperator.Action
					("dictstack",
						(interpreter)->
						interpreter.operandStack()
								.push(new PsiArray((java.util.ArrayList<PsiObject>)interpreter.dictStack().clone()))
					),
				new PsiOperator.Action
					("dicttomark",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final int i=ostack.findMarkPosition();
							final int ostackSize=ostack.size();
							if((ostackSize-i) % 2==0)
								throw new PsiRangeCheckException();

							final PsiDict oDict=new PsiDict();
							for(int j=i+1; j<ostackSize; j++)
							{
								final PsiStringy oKey=(PsiStringy)ostack.get(j++);
								final PsiObject o=ostack.get(j);
								oDict.psiPut(oKey, o);
							}
							ostack.setSize(i);
							ostack.push(oDict);
						}
					),
				new PsiOperator.Arity21
					("div", (a, b)->((PsiArithmetic)a).psiDiv((PsiArithmetic)b)),
				new PsiOperator.Action
					("dup",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.ensureSize(1);
							ostack.push(ostack.peek());
						}
					),
				new PsiOperator.Action
					("editline",
						(interpreter)->
						{
							try
							{
								final OperandStack ostack=interpreter.operandStack();
								final jline.ConsoleReader consoleReader
									=new jline.ConsoleReader();
								final String line=consoleReader.readLine();
								if(line!=null)
									ostack.push(new PsiString(line+"\n"));
								ostack.push(PsiBoolean.valueOf(line!=null));
							}
							catch(java.io.IOException e)
							{
								throw new PsiIOErrorException();
							}
						}
					),
				new PsiOperator.Action
					("end",
						(interpreter)->
						{
							DictStack dictstack=interpreter.dictStack();
							if(dictstack.size()<=2)
								throw new PsiDictStackUnderflowException();
							dictstack.pop();
						}
					),
				new PsiOperator.Arity20
					("enqueue", (a, b)->((PsiQueuelike)a).psiEnqueue(b)),
				new PsiOperator.Arity11
					("entries", (a)->((PsiIndexed)a).psiEntries()),
				new PsiOperator.Arity21
					("eq", (a, b)->a.psiEq(b)),
				new PsiOperator.Action
					("eval",
						(interpreter)->
						((PsiEvaluable)interpreter.operandStack().popOperands(1)[0]).eval(interpreter)),
				new PsiOperator.Action
					("exch",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							ostack.push(ops[1]);
							ostack.push(ops[0]);
						}
					),
				new PsiOperator.Action
					("exec",
						(interpreter)->
						interpreter.operandStack().popOperands(1)[0].invoke(interpreter)),
				new PsiOperator.Action
					("execstack",
						(interpreter)->
						interpreter.operandStack().push(
							new PsiArray((java.util.ArrayList<PsiObject>)interpreter.executionStack().clone()))),
				new PsiOperator.Action
					("executive", (interpreter)->interpreter.repl()),
				new PsiOperator.Action
					("exit",
						(interpreter)->
						{
							if(interpreter.currentLoopLevel()==-1)
								throw new PsiInvalidExitException();
							interpreter.executionStack().setSize(interpreter.popLoopLevel());
						}
					),
				new PsiOperator.Arity11
					("exp", (a)->((PsiComplexNumeric)a).psiExp()),
				new PsiOperator.Action
					("external",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(((PsiClassLoader)interpreter.getSystemDict().get("classpath"))
									.psiExternal(((PsiStringy)ostack.popOperands(1)[0])));
						}
					),
				new PsiOperator.Arity21
					("extract", (a, b)->((PsiIndexed)a).psiExtract(b)),
				new PsiOperator.Arity31
					("extractinterval", (a, b, c)->((PsiArraylike)a).psiExtractInterval((PsiInteger)b, (PsiInteger)c)),
				new PsiOperator.Arity11
					("fileaccesstime", (a)->FileSystem.psiFileAccessTime((PsiStringy)a)),
				new PsiOperator.Arity11
					("filecreationtime", (a)->FileSystem.psiFileCreationTime((PsiStringy)a)),
				new PsiOperator.Arity11
					("fileexists", (a)->FileSystem.psiFileExists((PsiStringy)a)),
				new PsiOperator.Arity11
					("filemodifiedtime", (a)->FileSystem.psiFileModifiedTime((PsiStringy)a)),
				new PsiOperator.Arity11
					("filereader", (a)->new PsiFileReader((PsiStringy)a)),
				new PsiOperator.Arity11
					("filewriter", (a)->new PsiFileWriter((PsiStringy)a)),
				new PsiOperator.Arity11
					("files", (a)->FileSystem.psiFiles((PsiStringy)a)),
				new PsiOperator.Arity11
					("filesize", (a)->FileSystem.psiFileSize((PsiStringy)a)),
				new PsiOperator.Action
					("find",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							/*
							final PsiObject[] ops=ostack.popOperands(2);
							PsiMatcher matcher=new PsiMatcher((PsiStringy)ops[0], (PsiRegExp)ops[1]);
							boolean resultValue=matcher.psiFind().booleanValue();
							if(resultValue)
								ostack.push(matcher);
							ostack.push(PsiBoolean.valueOf(resultValue));
							*/
							PsiMatcher matcher=(PsiMatcher)ostack.popOperands(1)[0];
							boolean resultValue=matcher.psiFind().booleanValue();
							if(resultValue)
								ostack.push(matcher);
							ostack.push(PsiBoolean.valueOf(resultValue));
						}
					),
				new PsiOperator.Action
					("findall",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(3);
							final PsiMatcher matcher=new PsiMatcher((PsiStringy)ops[0], (PsiRegExp)ops[1]);
							final PsiObject obj=ops[2];

							final int loopLevel=interpreter.pushLoopLevel();
							while(true)
							{
								if(matcher.psiFind().booleanValue())
								{
									ostack.push(matcher);
									obj.invoke(interpreter);
									interpreter.handleExecutionStack(loopLevel);
									if(interpreter.getStopFlag())
										break;
								}
								else
									break;
							}
							interpreter.popLoopLevel();
						}
					),
				new PsiOperator.Arity21
					("flipbit", (a, b)->((PsiBitwise)a).psiFlipBit((PsiInteger)b)),
				new PsiOperator.Arity11
					("floor", (a)->((PsiNumeric)a).psiFloor()),
				new PsiOperator.Arity10
					("flush", (a)->((PsiFlushable)a).psiFlush()),
				new PsiOperator.Action
					("for",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final ExecutionStack estack=interpreter.executionStack();
							final PsiObject[] ops=ostack.popOperands(4);
							final PsiNumeric oInitial=(PsiNumeric)ops[0];
							final PsiNumeric oIncrement=(PsiNumeric)ops[1];
							final PsiNumeric oLimit=(PsiNumeric)ops[2];
							final PsiObject oProc=ops[3];

							interpreter.pushLoopLevel();
							final boolean forward=oIncrement.psiGt(PsiInteger.ZERO).booleanValue();
							estack.push(new PsiOperator("#for_continue")
								{
									private PsiNumeric oCounter=oInitial;

									@Override
									public void action(final Interpreter interpreter1)
									{
										if(forward && oCounter.psiGt(oLimit).booleanValue()
												|| !forward && oCounter.psiLt(oLimit).booleanValue())
										{
											interpreter1.popLoopLevel();
											return;
										}
										estack.push(this);
										ostack.push(oCounter);
										oCounter=(PsiNumeric)oCounter.psiAdd(oIncrement);
										oProc.invoke(interpreter1);
									}
								});
						}
					),
				new PsiOperator.Arity20
					("forall", (a, b)->((PsiIterable)a).psiForAll(b)),
				new PsiOperator.Action
					("fork",
						(interpreter)->
						// TODO; error handling in forked context
						{
							final OperandStack ostack=interpreter.operandStack();

							ostack.ensureSize(2);
							final PsiObject o=ostack.popOperands(1)[0];

							final Interpreter forkedInterpreter
								=new Interpreter(interpreter.dictStack())
									{
										@Override
										public void run()
										{
											o.invoke(this);
											handleExecutionStack();
											if(getStopFlag())
											{
												PsiErrorDict.OP_HANDLEERROR.invoke(this);
												return;
											}
										}
									};
							final int i=ostack.findMarkPosition();
							final int ostackSize=ostack.size();
							final OperandStack forkedOpstack=forkedInterpreter.operandStack();
							for(int j=i+1; j<ostackSize; j++)
								forkedOpstack.push(ostack.get(j));
							ostack.setSize(i);
							ostack.push(forkedInterpreter);
							forkedInterpreter.start();
						}
					),
				new PsiOperator.Arity21
					("ge", (a, b)->((PsiScalar)a).psiGe((PsiScalar)b)),
				new PsiOperator.Arity21
					("get", (a, b)->((PsiIndexed)a).psiGet(b)),
				new PsiOperator.Arity21
					("getall", (a, b)->((PsiIndexed)a).psiGetAll((PsiIterable)b)),
				new PsiOperator.Arity31
					("getinterval", (a, b, c)->((PsiArraylike)a).psiGetInterval((PsiInteger)b, (PsiInteger)c)),
				new PsiOperator.Arity20
					("give", (a, b)->((PsiQueuelike)a).psiGive(b)),
				new PsiOperator.Arity21
					("grep", (a, b)->((PsiIterable)a).psiGrep((PsiProc)b)),
				new PsiOperator.Arity21
					("gt", (a, b)->((PsiScalar)a).psiGt((PsiScalar)b)),
				new PsiOperator.Action
					("halt",
						(interpreter)->
						System.exit(((PsiInteger)interpreter.operandStack().popOperands(1)[0]).intValue())
					),
				new PsiOperator.Arity20
					("hardlink", (a, b)->FileSystem.psiHardLink((PsiStringy)a, (PsiStringy)b)),
				new PsiOperator.Arity11
					("hashcode", (a)->a.psiHashCode()),
				new PsiOperator.Arity21
					("hypot", (a, b)->((PsiNumeric)a).psiHypot((PsiNumeric)b)),
				new PsiOperator.Arity21
					("idiv", (a, b)->((PsiInteger)a).psiIdiv((PsiInteger)b)),
				new PsiOperator.Action
					("if",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(2);
							if(((PsiBoolean)ops[0]).booleanValue())
								ops[1].invoke(interpreter);
						}
					),
				new PsiOperator.Action
					("ifelse",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(3);
							ops[((PsiBoolean)ops[0]).booleanValue()? 1: 2].invoke(interpreter);
						}
					),
				new PsiOperator.Arity11
					("imagpart", (a)->((PsiComplexNumeric)a).psiImagPart()),
				new PsiOperator.Action
					("index",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							int nValue=((PsiInteger)ostack.popOperands(1)[0]).intValue();
							if(nValue<0)
								throw new PsiRangeCheckException();
							ostack.ensureSize(nValue+1);
							ostack.push(ostack.get(ostack.size()-nValue-1));
						}
					),
				new PsiOperator.Arity30
					("insert", (a, b, c)->((PsiArraylike)a).psiInsert((PsiInteger)b, c)),
				new PsiOperator.Arity30
					("insertall", (a, b, c)->((PsiArraylike)a).psiInsertAll((PsiInteger)b, (PsiIterable)c)),
				new PsiOperator.Arity21
					("instanceof", (a, b)->a.psiInstanceOf((PsiStringy)b)),
				new PsiOperator.Arity21
					("intersects", (a, b)->((PsiSetlike)a).psiIntersects((PsiSetlike)b)),
				new PsiOperator.Arity21
					("inunicodeblock", (a, b)->((PsiInteger)a).psiInUnicodeBlock((PsiStringy)b)),
				new PsiOperator.Arity11
					("isdirectory", (a)->FileSystem.psiIsDirectory((PsiStringy)a)),
				new PsiOperator.Arity11
					("isempty", (a)->((PsiLengthy)a).psiIsEmpty()),
				new PsiOperator.Arity11
					("isfile", (a)->FileSystem.psiIsFile((PsiStringy)a)),
				new PsiOperator.Arity11
					("isfull", (a)->((PsiBounded)a).psiIsFull()),
				//new PsiOperator.Arity21
				//	("isinstance", (a, b)->((PsiType)a).psiIsInstance(b)),
				new PsiOperator.Action
					("isinstance",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							ostack.push(PsiBoolean.valueOf(
									interpreter.resolveType(((PsiStringy)ops[0]).stringValue()).isInstance(ops[1])));
						}),
				new PsiOperator.Arity21
					("issamefile", (a, b)->FileSystem.psiIsSameFile((PsiStringy)a, (PsiStringy)b)),
				new PsiOperator.Arity11
					("issymlink", (a)->FileSystem.psiIsSymLink((PsiStringy)a)),
				new PsiOperator.Arity11
					("iszero", (a)->((PsiComplexNumeric)a).psiIsZero()),
				new PsiOperator.Action
					("join",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(1);
							final PsiContext context=(PsiContext)ops[0];
							context.psiJoin();
							OperandStack joinedOpstack=((Interpreter)context).operandStack();
							ostack.push(PsiMark.MARK);
							for(PsiObject obj: joinedOpstack)
								ostack.push(obj);
						}
					),
				new PsiOperator.Arity11
					("keys", (a)->((PsiIndexed)a).psiKeys()),
				new PsiOperator.Arity21
					("known", (a, b)->((PsiIndexed)a).psiKnown(b)),
				new PsiOperator.Arity21
					("le", (a, b)->((PsiScalar)a).psiLe((PsiScalar)b)),
				new PsiOperator.Arity11
					("length", (a)->((PsiLengthy)a).psiLength()),
				new PsiOperator.Arity11
					("listdirectory", (a)->FileSystem.psiListDirectory((PsiStringy)a)),
				new PsiOperator.Action
					("load",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(interpreter.dictStack().load((PsiStringy)ostack.popOperands(1)[0]));
						}
					),
				new PsiOperator.Arity01
					("lock", PsiLock::new),
				new PsiOperator.Arity11
					("log", (a)->((PsiComplexNumeric)a).psiLog()),
				new PsiOperator.Action
					("loop",
						(interpreter)->
						{
							final PsiObject oProc=interpreter.operandStack().popOperands(1)[0];

							interpreter.pushLoopLevel();
							interpreter.executionStack().push(new PsiOperator("#loop_continue")
								{
									@Override
									public void action(final Interpreter interpreter1)
										throws PsiException
									{
										interpreter1.executionStack().push(this);
										oProc.invoke(interpreter1);
									}
								});
						}
					),
				new PsiOperator.Arity11
					("lowercase", (a)->((PsiStringy)a).psiLowerCase()),
				new PsiOperator.Arity21
					("lt", (a, b)->((PsiScalar)a).psiLt((PsiScalar)b)),
				new PsiOperator.Action
					("map",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final PsiContainer container=(PsiContainer)ops[0];
							final PsiProc proc=(PsiProc)ops[1];
							final PsiAppendable result=(PsiAppendable)container.psiNewEmpty();

							final int loopLevel=interpreter.pushLoopLevel();
							for(PsiObject element: (PsiContainer<? extends PsiObject>)container)
							{
								ostack.push(element);
								proc.invoke(interpreter);
								interpreter.handleExecutionStack(loopLevel);
								result.psiAppend(ostack.pop());
								if(interpreter.getStopFlag())
									break;
							}
							interpreter.popLoopLevel();
							ostack.push(result);
						}
					),
				new PsiOperator.Arity21
					("matcher", (a, b)->new PsiMatcher((PsiStringy)a, (PsiRegExp)b)),
				new PsiOperator.Arity21
					("max", (a, b)->((PsiScalar)a).psiMax((PsiScalar)b)),
				new PsiOperator.Arity21
					("min", (a, b)->((PsiScalar)a).psiMin((PsiScalar)b)),
				new PsiOperator.Arity21
					("mod", (a, b)->((PsiInteger)a).psiMod((PsiInteger)b)),
				new PsiOperator.Action
					("monitor",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(2);
							final PsiLock oLock=(PsiLock)ops[0];
							final PsiObject oProc=ops[1];

							if(oLock.isHeldByCurrentThread())
								throw new PsiInvalidContextException();
							oLock.lock();
							interpreter.executionStack().push(new PsiOperator.Action
								("#monitor_continue", (interpreter1)->oLock.unlock()));
							oProc.invoke(interpreter);
						}
					),
				new PsiOperator.Arity21
					("mul", (a, b)->((PsiArithmetic)a).psiMul((PsiArithmetic)b)),
				new PsiOperator.Arity21
					("ne", (a, b)->a.psiNe(b)),
				new PsiOperator.Arity11
					("neg", (a)->((PsiAdditive)a).psiNeg()),
				new PsiOperator.Arity21
					("normaldeviate", (a, b)->((PsiRandom)a).psiNormalDeviate((PsiNumeric)b)),
				new PsiOperator.Arity11
					("not", (a)->((PsiLogical)a).psiNot()),
				new PsiOperator.Arity10
					("notify", (a)->((PsiCondition)a).psiNotify()),
				new PsiOperator.Arity11
					("notzero", (a)->((PsiComplexNumeric)a).psiNotZero()),
				new PsiOperator.Arity21
					("or", (a, b)->((PsiLogical)a).psiOr((PsiLogical)b)),
				new PsiOperator.Arity10
					("pop", (a)->{}),
				new PsiOperator.Arity11
					("postchop", (a)->((PsiArraylike)a).psiPostChop()),
				new PsiOperator.Arity21
					("pow", (a, b)->((PsiComplexNumeric)a).psiPow((PsiComplexNumeric)b)),
				new PsiOperator.Arity11
					("prechop", (a)->((PsiArraylike)a).psiPreChop()),
				new PsiOperator.Arity20
					("prepend", (a, b)->((PsiArraylike)a).psiPrepend(b)),
				new PsiOperator.Arity20
					("prependall", (a, b)->((PsiArraylike)a).psiPrependAll((PsiIterable)b)),
				new PsiOperator.Action
					("prettyprint",
						(interpreter)->
						System.out.println(interpreter.operandStack().popOperands(1)[0].toSyntaxString())),
				new PsiOperator.Action
					("print",
						(interpreter)->
						((PsiWriter)interpreter.dictStack().load("stdout"))
							.psiWriteString((PsiStringy)interpreter.operandStack().popOperands(1)[0])
					),
				new PsiOperator.Action
					("println",
						(interpreter)->
						{
							final PsiWriter stdwriter=(PsiWriter)interpreter.dictStack().load("stdout");
							stdwriter.psiWriteString((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
							stdwriter.psiWriteString((PsiStringy)interpreter.dictStack().load("eol"));
						}
					),
				new PsiOperator.Arity11
					("process", (a)->new PsiProcess((PsiDictlike)a)),
				new PsiOperator.Arity11
					("processerror", (a)->((PsiProcess)a).psiProcessError()),
				new PsiOperator.Arity11
					("processreader", (a)->((PsiProcess)a).psiProcessReader()),
				new PsiOperator.Arity11
					("processwriter", (a)->((PsiProcess)a).psiProcessWriter()),
				new PsiOperator.Action
					("pstack",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							// TODO reverse order
							System.out.print("OPSTACK> ");
							for(PsiObject o: ostack)
								System.out.print(o.toSyntaxString()+" ");
							System.out.println();
						}
					),
				new PsiOperator.Arity30
					("put", (a, b, c)->((PsiIndexed)a).psiPut(b, c)),
				new PsiOperator.Arity30
					("putinterval", (a, b, c)->((PsiArraylike)a).psiPutInterval((PsiInteger)b, (PsiIterable)c)),
				new PsiOperator.Action
					("quit", (interpreter)->interpreter.quit()),
				new PsiOperator.Arity01
					("random", PsiRandom::new),
				new PsiOperator.Action
					("read",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							PsiInteger character=((PsiReadable)ostack.popOperands(1)[0]).psiRead();
							boolean notEOF=(character!=PsiInteger.MINUS_ONE);
							if(notEOF)
								ostack.push(character);
							ostack.push(PsiBoolean.valueOf(notEOF));
						}
					),
				new PsiOperator.Action
					("readline",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							PsiStringy eol=(PsiStringy)interpreter.dictStack().load("eol");
							PsiString string=((PsiReadable)ostack.popOperands(1)[0]).psiReadLine(eol);
							if(string.length()>0)
								ostack.push(string);
							ostack.push(PsiBoolean.valueOf(string.length()>0));
						}
					),
				new PsiOperator.Arity11
					("readlink", (a)->FileSystem.psiReadLink((PsiStringy)a)),
				new PsiOperator.Action
					("readstring",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							PsiString string=((PsiReadable)ops[0]).psiReadString((PsiInteger)ops[1]);
							ostack.push(string);
							ostack.push(string.psiLength().psiEq((PsiInteger)ops[1]));
						}
					),
				new PsiOperator.Arity11
					("ready", (a)->((PsiReadable)a).psiReady()),
				new PsiOperator.Arity11
					("regexp", (a)->new PsiRegExp((PsiStringy)a)),
				new PsiOperator.Arity11
					("realpart", (a)->((PsiComplexNumeric)a).psiRealPart()),
				new PsiOperator.Arity20
					("remove", (a, b)->((PsiSetlike)a).psiRemove(b)),
				new PsiOperator.Arity20
					("removeall", (a, b)->((PsiSetlike)a).psiRemoveAll((PsiIterable)b)),
				new PsiOperator.Arity20
					("renamefile", (a, b)->FileSystem.psiRenameFile((PsiStringy)a, (PsiStringy)b)),
				new PsiOperator.Action
					("repeat",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final ExecutionStack estack=interpreter.executionStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final PsiInteger oCount=(PsiInteger)ops[0];
							final PsiObject oProc=ops[1];
							final long count=oCount.longValue();

							if(count<0)
								throw new PsiRangeCheckException();
							if(count==0)
								return;

							interpreter.pushLoopLevel();
							interpreter.executionStack().push(new PsiOperator("#repeat_continue")
								{
									private long count1=count;

									@Override
									public void action(final Interpreter interpreter1)
									{
										if(count1--==0)
										{
											interpreter1.popLoopLevel();
										}
										else
										{
											estack.push(this);
											oProc.invoke(interpreter1);
										}
									}
								});
						}
					),
				new PsiOperator.Arity21
					("replicate", (a, b)->((PsiAppendable)a).psiReplicate((PsiInteger)b)),
				new PsiOperator.Arity10
					("reset", (a)->((PsiResettable)a).psiReset()),
				new PsiOperator.Arity20
					("retainall", (a, b)->((PsiSetlike)a).psiRetainAll((PsiIterable)b)),
				new PsiOperator.Arity11
					("reverse", (a)->((PsiArraylike)a).psiReverse()),
				new PsiOperator.Action
					("roll",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final int nValue=((PsiInteger)ops[0]).intValue();
							int jValue=((PsiInteger)ops[1]).intValue();
							final int ostackSize=ostack.size();
							if(nValue<0)
								throw new PsiRangeCheckException();
							if(nValue==0)
								return;
							ostack.ensureSize(nValue);
							while(jValue<0)
								jValue+=nValue;
							jValue%=nValue;
							for(int i=0; i<jValue; i++)
								ostack.add(ostackSize-nValue, ostack.pop());
						}
					),
				new PsiOperator.Arity11
					("round", (a)->((PsiNumeric)a).psiRound()),
				new PsiOperator.Action
					("say",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiWriter stdwriter=(PsiWriter)interpreter.dictStack().load("stdout");
							stdwriter.psiWriteString((PsiStringy)ostack.popOperands(1)[0]);
							stdwriter.psiWriteString((PsiStringy)interpreter.dictStack().load("eol"));
							stdwriter.psiFlush();
						}
					),
				new PsiOperator.Arity01
					("set", PsiSet::new),
				new PsiOperator.Arity21
					("setbit", (a, b)->((PsiBitwise)a).psiSetBit((PsiInteger)b)),
				new PsiOperator.Arity20
					("setlength", (a, b)->((PsiArraylike)a).psiSetLength((PsiInteger)b)),
				new PsiOperator.Arity20
					("setseed", (a, b)->((PsiRandom)a).psiSetSeed((PsiInteger)b)),
				new PsiOperator.Action
					("settomark",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final int i=ostack.findMarkPosition();
							final PsiSet oSet=new PsiSet();
							for(int j=ostack.size()-1; j>=i+1; j--)
								oSet.psiAppend(ostack.get(j));
							ostack.setSize(i);
							ostack.push(oSet);
						}
					),
				new PsiOperator.Action
					("signalerror",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(2);
							interpreter.handleError(
									new PsiException(ops[0])
									{
										@Override
										public String getName()
										{
											return ((PsiStringy)ops[1]).stringValue();
										}
									}
								);
						}
					),
				new PsiOperator.Arity11
					("signum", (a)->((PsiComplexNumeric)a).psiSignum()),
				new PsiOperator.Arity11
					("sin", (a)->((PsiComplexNumeric)a).psiSin()),
				new PsiOperator.Arity11
					("sinh", (a)->((PsiComplexNumeric)a).psiSinh()),
				new PsiOperator.Arity21
					("skip", (a, b)->((PsiReadable)a).psiSkip((PsiInteger)b)),
				new PsiOperator.Arity10
					("sleep", (a)->PsiContext.psiSleep((PsiInteger)a)),
				new PsiOperator.Arity21
					("slice", (a, b)->((PsiIndexed)a).psiSlice((PsiIterable)b)),
				new PsiOperator.Action
					("sort",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject[] ops=ostack.popOperands(2);
							final PsiArray array=(PsiArray)ops[0];
							final PsiObject comparator=ops[1];

							ostack.push(array.psiSort(new java.util.Comparator<PsiObject>()
									{
										@Override
										public int compare(PsiObject o1, PsiObject o2)
										{
											ostack.push(o1);
											ostack.push(o2);
											final int execLevel=interpreter.getExecLevel();
											comparator.invoke(interpreter);
											interpreter.handleExecutionStack(execLevel);
											// TODO: ensure stack size
											return ((PsiInteger)ostack.pop()).intValue();
										}
									}));
						}
					),
				new PsiOperator.Arity21
					("split", (a, b)->((PsiStringy)a).psiSplit((PsiRegExp)b)),
				new PsiOperator.Arity11
					("sqrt", (a)->((PsiComplexNumeric)a).psiSqrt()),
				new PsiOperator.Action
					("stack",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(new PsiArray((java.util.ArrayList<PsiObject>)ostack.clone()));
						}
					),
				new PsiOperator.Arity11
					("status", (a)->((PsiProcess)a).psiStatus()),
				new PsiOperator.Action
					("stop", (interpreter)->interpreter.psiStop()),
				new PsiOperator.Action
					("stopped",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiObject oProc=ostack.popOperands(1)[0];

							final int stopLevel=interpreter.pushStopLevel();
							oProc.invoke(interpreter);
							interpreter.handleExecutionStack(stopLevel);
							ostack.push(PsiBoolean.valueOf(interpreter.getStopFlag()));
							interpreter.setStopFlag(false);
							interpreter.popStopLevel();

							/*
							interpreter.pushStopLevel();
							interpreter.executionStack().push(new PsiOperator.Action
								("#stopped_continue", (interpreter1)->
								{
									ostack.push(PsiBoolean.valueOf(interpreter1.getStopFlag()));
									interpreter1.setStopFlag(false);
									interpreter1.popStopLevel();
								}));
							oProc.invoke(interpreter);
							*/
						}
					),
				new PsiOperator.Action
					("store",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(2);
							interpreter.dictStack().psiStore((PsiStringy)ops[0], ops[1]);
						}
					),
				new PsiOperator.Arity01
					("string", PsiString::new),
				new PsiOperator.Arity11
					("stringreader", (a)->new PsiStringReader((PsiStringy)a)),
				new PsiOperator.Arity11
					("stringwriter", (a)->new PsiStringWriter((PsiString)a)),
				new PsiOperator.Arity21
					("sub", (a, b)->((PsiAdditive)a).psiSub((PsiAdditive)b)),
				new PsiOperator.Arity20
					("symlink", (a, b)->FileSystem.psiSymLink((PsiStringy)a, (PsiStringy)b)),
				new PsiOperator.Action
					("synchronized",
						(interpreter)->
						{
							final PsiObject[] ops=interpreter.operandStack().popOperands(2);
							final PsiObject o=ops[0];
							final PsiObject oProc=ops[1];
							///*
							synchronized(o)
							{
								final int loopLevel=interpreter.pushLoopLevel();
								oProc.invoke(interpreter);
								interpreter.handleExecutionStack(loopLevel);
							}
							//*/
							//final java.util.concurrent.locks.ReentrantLock lock
							//	=new java.util.concurrent.locks.ReentrantLock();
							//lock.lock();
						}
					),
				new PsiOperator.Arity11
					("take", (a)->((PsiQueuelike)a).psiTake()),
				new PsiOperator.Arity11
					("tan", (a)->((PsiComplexNumeric)a).psiTan()),
				new PsiOperator.Arity11
					("tanh", (a)->((PsiComplexNumeric)a).psiTanh()),
				new PsiOperator.Arity21
					("testbit", (a, b)->((PsiBitwise)a).psiTestBit((PsiInteger)b)),
				new PsiOperator.Arity01
					("time", Time::psiTime),
				new PsiOperator.Arity11
					("tointeger", (a)->((PsiConvertableToInteger)a).psiToInteger()),
				new PsiOperator.Action
					("tokens",
						(interpreter)->
						{
							interpreter.interpretBraced(new PsiStringReader((PsiStringy)interpreter.operandStack().popOperands(1)[0]));
						}
					),
				new PsiOperator.Arity11
					("toname", (a)->a.psiToName()),
				new PsiOperator.Arity11
					("toreal", (a)->((PsiConvertableToReal)a).psiToReal()),
				new PsiOperator.Arity11
					("tostring", (a)->a.psiToString()),
				new PsiOperator.Arity11
					("type", PsiObject::psiType),
				new PsiOperator.Arity20
					("undef", (a, b)->((PsiDictlike)a).psiUndef((PsiStringy)b)),
				new PsiOperator.Arity11
					("uniformboolean", (a)->((PsiRandom)a).psiUniformBoolean()),
				new PsiOperator.Arity21
					("uniformdeviate", (a, b)->((PsiRandom)a).psiUniformDeviate((PsiNumeric)b)),
				new PsiOperator.Arity21
					("unite", (a, b)->((PsiIterable)a).psiUnite((PsiStringy)b)),
				new PsiOperator.Arity11
					("uppercase", (a)->((PsiStringy)a).psiUpperCase()),
				new PsiOperator.Arity11
					("values", (a)->((PsiIndexed)a).psiValues()),
				new PsiOperator.Arity10
					("wait", (a)->((PsiCondition)a).psiWait()),
				new PsiOperator.Action
					("warn",
						(interpreter)->
						{
							final PsiWriter stderror=(PsiWriter)interpreter.dictStack().load("stderr");
							stderror.psiWriteString((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
							stderror.psiFlush();
						}
					),
				new PsiOperator.Action
					("where",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							final PsiDictlike dict=interpreter.dictStack().where((PsiStringy)ostack.popOperands(1)[0]);
							if(dict!=null)
								ostack.push(dict);
							ostack.push(PsiBoolean.valueOf(dict!=null));
						}
					),
				new PsiOperator.Arity20
					("write", (a, b)->((PsiWritable)a).psiWrite((PsiInteger)b)),
				new PsiOperator.Arity20
					("writestring", (a, b)->((PsiWritable)a).psiWriteString((PsiStringy)b)),
				new PsiOperator.Arity21
					("xor", (a, b)->((PsiLogical)a).psiXor((PsiLogical)b)),
				new PsiOperator.Action
					("yield", (interpreter)->Thread.yield())

			);

		put("]", get("arraytomark"));
		put(">", get("dicttomark"));
		put(")", get("settomark"));
		put("?", get("prettyprint"));
		put("$error", new PsiDict());
		put("(", PsiMark.MARK);
		put("<", PsiMark.MARK);
		put("[", PsiMark.MARK);
		put("classpath", new PsiClassLoader());
		put("configdict", new PsiConfigDict());
		put("eol", new PsiName(System.getProperty("line.separator")));
		put("errordict", new PsiErrorDict());
		put("false", PsiBoolean.FALSE);
		put("mark", PsiMark.MARK);
		put("mathE", PsiReal.E);
		put("mathPI", PsiReal.PI);
		put("mathI", PsiComplex.I);
		put("maxinteger", PsiInteger.MAX_VALUE);
		put("maxreal", PsiReal.MAX_VALUE);
		put("mininteger", PsiInteger.MIN_VALUE);
		put("minreal", PsiReal.MIN_VALUE);
		put("nan", PsiReal.NAN);
		put("null", PsiNull.NULL);
		put("osarch", new PsiName(System.getProperty("os.arch")));
		put("osname", new PsiName(System.getProperty("os.name")));
		put("osversion", new PsiName(System.getProperty("os.version")));
		put("processors", PsiInteger.valueOf(Runtime.getRuntime().availableProcessors()));
		put("product", new PsiName("Psylla"));
		put("stderr", new PsiWriter(new java.io.OutputStreamWriter(System.err)));
		put("stdin", new PsiReader(new java.io.InputStreamReader(System.in)));
		put("stdout", new PsiWriter(new java.io.OutputStreamWriter(System.out)));
		put("stdrandom", new PsiRandom());
		put("systemdict", this);
		put("true", PsiBoolean.TRUE);
		put("userdict", new PsiDict());
		put("username", new PsiName(System.getProperty("user.name")));
		put("version", new PsiName(""+Version.getVersion()));

	}
}
