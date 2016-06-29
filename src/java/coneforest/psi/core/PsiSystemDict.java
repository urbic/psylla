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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("abs", PsiComplexNumeric::psiAbs),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("acos", PsiComplexNumeric::psiAcos),
				new PsiOperator.Arity21<PsiAdditive, PsiAdditive>
					("add", PsiAdditive::psiAdd),
				new PsiOperator.Arity21<PsiLogical, PsiLogical>
					("and", PsiLogical::psiAnd),
				new PsiOperator.Arity20<PsiAppendable, PsiObject>
					("append", PsiAppendable::psiAppend),
				new PsiOperator.Arity20<PsiAppendable, PsiIterable>
					("appendall", PsiAppendable::psiAppendAll),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("arg", PsiComplexNumeric::psiArg),
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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("asin", PsiComplexNumeric::psiAsin),
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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("atan", PsiComplexNumeric::psiAtan),
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
				new PsiOperator.Arity11<PsiProc>
					("bind", PsiProc::psiBind),
				new PsiOperator.Arity01
					("bitset", PsiBitSet::new),
				new PsiOperator.Arity01
					("bitvector", PsiBitVector::new),
				new PsiOperator.Arity21<PsiBitwise, PsiInteger>
					("bitshift", PsiBitwise::psiBitShift),
				new PsiOperator.Arity11<PsiInteger>
					("blockingqueue", PsiBlockingQueue::new),
				new PsiOperator.Arity11<PsiInteger>
					("calendar", Time::psiCalendar),
				new PsiOperator.Arity11<PsiDictlike>
					("calendartime", Time::psiCalendarTime),
				new PsiOperator.Arity11<PsiBounded>
					("capacity", PsiBounded::psiCapacity),
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
				new PsiOperator.Arity11<PsiMatcher>
					("capturegroupcount", PsiMatcher::psiCaptureGroupCount),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("cbrt", PsiComplexNumeric::psiCbrt),
				new PsiOperator.Arity11<PsiNumeric>
					("ceiling", PsiNumeric::psiCeiling),
				new PsiOperator.Arity10<PsiClearable>
					("clear", PsiClearable::psiClear),
				new PsiOperator.Arity21<PsiBitwise, PsiInteger>
					("clearbit", PsiBitwise::psiClearBit),
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
				new PsiOperator.Arity11<PsiObject>
					("clone", PsiObject::psiClone),
				new PsiOperator.Arity10<PsiCloseable>
					("close", PsiCloseable::psiClose),
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("cmp", PsiScalar::psiCmp),
				new PsiOperator.Arity21<PsiNumeric, PsiNumeric>
					("complex", PsiComplex::new),
				new PsiOperator.Arity21<PsiNumeric, PsiNumeric>
					("complexpolar", PsiComplex::psiFromPolar),
				new PsiOperator.Arity11<PsiLock>
					("condition", PsiLock::psiCondition),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("conjugate", PsiComplexNumeric::psiConjugate),
				new PsiOperator.Arity21<PsiSetlike, PsiObject>
					("contains", PsiSetlike::psiContains),
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
				new PsiOperator.Arity20<PsiStringy, PsiStringy>
					("copyfile", FileSystem::psiCopyFile),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("cos", PsiComplexNumeric::psiCos),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("cosh", PsiComplexNumeric::psiCosh),
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
				new PsiOperator.Arity10<PsiStringy>
					("createdirectory", FileSystem::psiCreateDirectory),
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
				new PsiOperator.Arity20<PsiIndexed, PsiObject>
					("delete", PsiIndexed::psiDelete),
				new PsiOperator.Arity10<PsiStringy>
					("deletefile", FileSystem::psiDeleteFile),
				new PsiOperator.Arity11<PsiQueuelike>
					("dequeue", PsiQueuelike::psiDequeue),
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
				new PsiOperator.Arity21<PsiArithmetic, PsiArithmetic>
					("div", PsiArithmetic::psiDiv),
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
				new PsiOperator.Arity20<PsiQueuelike, PsiObject>
					("enqueue", PsiQueuelike::psiEnqueue),
				new PsiOperator.Arity11<PsiIndexed>
					("entries", PsiIndexed::psiEntries),
				new PsiOperator.Arity21<PsiObject, PsiObject>
					("eq", PsiObject::psiEq),
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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("exp", PsiComplexNumeric::psiExp),
				new PsiOperator.Action
					("external",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(((PsiClassLoader)interpreter.getSystemDict().get("classpath"))
									.psiExternal(((PsiStringy)ostack.popOperands(1)[0])));
						}
					),
				new PsiOperator.Arity21<PsiIndexed, PsiObject>
					("extract", PsiIndexed::psiExtract),
				new PsiOperator.Arity31<PsiArraylike, PsiInteger, PsiInteger>
					("extractinterval", PsiArraylike::psiExtractInterval),
				new PsiOperator.Arity11<PsiStringy>
					("fileaccesstime", FileSystem::psiFileAccessTime),
				new PsiOperator.Arity11<PsiStringy>
					("filecreationtime", FileSystem::psiFileCreationTime),
				new PsiOperator.Arity11<PsiStringy>
					("fileexists", FileSystem::psiFileExists),
				new PsiOperator.Arity11<PsiStringy>
					("filemodifiedtime", FileSystem::psiFileModifiedTime),
				new PsiOperator.Arity11<PsiStringy>
					("filereader", PsiFileReader::new),
				new PsiOperator.Arity11<PsiStringy>
					("filewriter", PsiFileWriter::new),
				new PsiOperator.Arity11<PsiStringy>
					("files", FileSystem::psiFiles),
				new PsiOperator.Arity11<PsiStringy>
					("filesize", FileSystem::psiFileSize),
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
				new PsiOperator.Arity21<PsiBitwise, PsiInteger>
					("flipbit", PsiBitwise::psiFlipBit),
				new PsiOperator.Arity11<PsiNumeric>
					("floor", PsiNumeric::psiFloor),
				new PsiOperator.Arity10<PsiFlushable>
					("flush", PsiFlushable::psiFlush),
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
							estack.push(forward?
									new PsiOperator("#for_continue")
									{
										private PsiNumeric oCounter=oInitial;

										@Override
										public void action(final Interpreter interpreter1)
										{
											if(oCounter.psiGt(oLimit).booleanValue())
												interpreter1.popLoopLevel();
											else
											{
												estack.push(this);
												ostack.push(oCounter);
												oCounter=(PsiNumeric)oCounter.psiAdd(oIncrement);
												oProc.invoke(interpreter1);
											}
										}
									}:
									new PsiOperator("#for_continue")
									{
										private PsiNumeric oCounter=oInitial;

										@Override
										public void action(final Interpreter interpreter1)
										{
											if(oCounter.psiLt(oLimit).booleanValue())
												interpreter1.popLoopLevel();
											else
											{
												estack.push(this);
												ostack.push(oCounter);
												oCounter=(PsiNumeric)oCounter.psiAdd(oIncrement);
												oProc.invoke(interpreter1);
											}
										}
									});
						}
					),
				new PsiOperator.Arity20<PsiIterable, PsiObject>
					("forall", PsiIterable::psiForAll),
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
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("ge", PsiScalar::psiGe),
				new PsiOperator.Arity21<PsiIndexed, PsiObject>
					("get", PsiIndexed::psiGet),
				new PsiOperator.Arity21<PsiIndexed, PsiIterable>
					("getall", PsiIndexed::psiGetAll),
				new PsiOperator.Arity31<PsiArraylike, PsiInteger, PsiInteger>
					("getinterval", PsiArraylike::psiGetInterval),
				new PsiOperator.Arity20<PsiQueuelike, PsiObject>
					("give", PsiQueuelike::psiGive),
				new PsiOperator.Arity21<PsiIterable, PsiProc>
					("grep", PsiIterable::psiGrep),
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("gt", PsiScalar::psiGt),
				new PsiOperator.Action
					("halt",
						(interpreter)->
						System.exit(((PsiInteger)interpreter.operandStack().popOperands(1)[0]).intValue())
					),
				new PsiOperator.Arity20<PsiStringy, PsiStringy>
					("hardlink", FileSystem::psiHardLink),
				new PsiOperator.Arity11<PsiObject>
					("hashcode", PsiObject::psiHashCode),
				new PsiOperator.Arity21<PsiNumeric, PsiNumeric>
					("hypot", PsiNumeric::psiHypot),
				new PsiOperator.Arity21<PsiInteger, PsiInteger>
					("idiv", PsiInteger::psiIdiv),
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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("imagpart", PsiComplexNumeric::psiImagPart),
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
				new PsiOperator.Arity30<PsiArraylike, PsiInteger, PsiObject>
					("insert", PsiArraylike::psiInsert),
				new PsiOperator.Arity30<PsiArraylike, PsiInteger, PsiIterable>
					("insertall", PsiArraylike::psiInsertAll),
				new PsiOperator.Arity21<PsiObject, PsiStringy>
					("instanceof", PsiObject::psiInstanceOf),
				new PsiOperator.Arity21<PsiSetlike, PsiSetlike>
					("intersects", PsiSetlike::psiIntersects),
				new PsiOperator.Arity21<PsiInteger, PsiStringy>
					("inunicodeblock", PsiInteger::psiInUnicodeBlock),
				new PsiOperator.Arity11<PsiStringy>
					("isdirectory", FileSystem::psiIsDirectory),
				new PsiOperator.Arity11<PsiLengthy>
					("isempty", PsiLengthy::psiIsEmpty),
				new PsiOperator.Arity11<PsiStringy>
					("isfile", FileSystem::psiIsFile),
				new PsiOperator.Arity11<PsiBounded>
					("isfull", PsiBounded::psiIsFull),
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
				new PsiOperator.Arity21<PsiStringy, PsiStringy>
					("issamefile", FileSystem::psiIsSameFile),
				new PsiOperator.Arity11<PsiStringy>
					("issymlink", FileSystem::psiIsSymLink),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("iszero", PsiComplexNumeric::psiIsZero),
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
				new PsiOperator.Arity11<PsiIndexed>
					("keys", PsiIndexed::psiKeys),
				new PsiOperator.Arity21<PsiIndexed, PsiObject>
					("known", PsiIndexed::psiKnown),
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("le", PsiScalar::psiLe),
				new PsiOperator.Arity11<PsiLengthy>
					("length", PsiLengthy::psiLength),
				new PsiOperator.Arity11<PsiStringy>
					("listdirectory", FileSystem::psiListDirectory),
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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("log", PsiComplexNumeric::psiLog),
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
				new PsiOperator.Arity11<PsiStringy>
					("lowercase", PsiStringy::psiLowerCase),
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("lt", PsiScalar::psiLt),
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
				new PsiOperator.Arity21<PsiStringy, PsiRegExp>
					("matcher", PsiMatcher::new),
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("max", PsiScalar::psiMax),
				new PsiOperator.Arity21<PsiScalar, PsiScalar>
					("min", PsiScalar::psiMin),
				new PsiOperator.Arity21<PsiInteger, PsiInteger>
					("mod", PsiInteger::psiMod),
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
				new PsiOperator.Arity21<PsiArithmetic, PsiArithmetic>
					("mul", PsiArithmetic::psiMul),
				new PsiOperator.Arity21<PsiObject, PsiObject>
					("ne", PsiObject::psiNe),
				new PsiOperator.Arity11<PsiAdditive>
					("neg", PsiAdditive::psiNeg),
				new PsiOperator.Arity21<PsiRandom, PsiNumeric>
					("normaldeviate", PsiRandom::psiNormalDeviate),
				new PsiOperator.Arity11<PsiLogical>
					("not", PsiLogical::psiNot),
				new PsiOperator.Arity10<PsiCondition>
					("notify", PsiCondition::psiNotify),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("notzero", PsiComplexNumeric::psiNotZero),
				new PsiOperator.Arity21<PsiLogical, PsiLogical>
					("or", PsiLogical::psiOr),
				new PsiOperator.Arity10<PsiObject>
					("pop", (a)->{}),
				new PsiOperator.Arity11<PsiArraylike>
					("postchop", PsiArraylike::psiPostChop),
				new PsiOperator.Arity21<PsiComplexNumeric, PsiComplexNumeric>
					("pow", PsiComplexNumeric::psiPow),
				new PsiOperator.Arity11<PsiArraylike>
					("prechop", PsiArraylike::psiPreChop),
				new PsiOperator.Arity20<PsiArraylike, PsiObject>
					("prepend", PsiArraylike::psiPrepend),
				new PsiOperator.Arity20<PsiArraylike, PsiIterable>
					("prependall", PsiArraylike::psiPrependAll),
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
				new PsiOperator.Arity11<PsiDictlike>
					("process", PsiProcess::new),
				new PsiOperator.Arity11<PsiProcess>
					("processerror", PsiProcess::psiProcessError),
				new PsiOperator.Arity11<PsiProcess>
					("processreader", PsiProcess::psiProcessReader),
				new PsiOperator.Arity11<PsiProcess>
					("processwriter", PsiProcess::psiProcessWriter),
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
				new PsiOperator.Arity30<PsiIndexed, PsiObject, PsiObject>
					("put", PsiIndexed::psiPut),
				new PsiOperator.Arity30<PsiArraylike, PsiInteger, PsiIterable>
					("putinterval", PsiArraylike::psiPutInterval),
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
				new PsiOperator.Arity11<PsiStringy>
					("readlink", FileSystem::psiReadLink),
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
				new PsiOperator.Arity11<PsiReadable>
					("ready", PsiReadable::psiReady),
				new PsiOperator.Arity11<PsiStringy>
					("regexp", PsiRegExp::new),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("realpart", PsiComplexNumeric::psiRealPart),
				new PsiOperator.Arity20<PsiSetlike, PsiObject>
					("remove", PsiSetlike::psiRemove),
				new PsiOperator.Arity20<PsiSetlike, PsiIterable>
					("removeall", PsiSetlike::psiRemoveAll),
				new PsiOperator.Arity20<PsiStringy, PsiStringy>
					("renamefile", FileSystem::psiRenameFile),
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
				new PsiOperator.Arity21<PsiAppendable, PsiInteger>
					("replicate", PsiAppendable::psiReplicate),
				new PsiOperator.Arity10<PsiResettable>
					("reset", PsiResettable::psiReset),
				new PsiOperator.Arity20<PsiSetlike, PsiIterable>
					("retainall", PsiSetlike::psiRetainAll),
				new PsiOperator.Arity11<PsiArraylike>
					("reverse", PsiArraylike::psiReverse),
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
				new PsiOperator.Arity11<PsiNumeric>
					("round", PsiNumeric::psiRound),
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
				new PsiOperator.Arity21<PsiBitwise, PsiInteger>
					("setbit", PsiBitwise::psiSetBit),
				new PsiOperator.Arity20<PsiArraylike, PsiInteger>
					("setlength", PsiArraylike::psiSetLength),
				new PsiOperator.Arity20<PsiRandom, PsiInteger>
					("setseed", PsiRandom::psiSetSeed),
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
				new PsiOperator.Arity11<PsiComplexNumeric>
					("signum", PsiComplexNumeric::psiSignum),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("sin", PsiComplexNumeric::psiSin),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("sinh", PsiComplexNumeric::psiSinh),
				new PsiOperator.Arity21<PsiReadable, PsiInteger>
					("skip", PsiReadable::psiSkip),
				new PsiOperator.Arity10<PsiInteger>
					("sleep", PsiContext::psiSleep),
				new PsiOperator.Arity21<PsiIndexed, PsiIterable>
					("slice", PsiIndexed::psiSlice),
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
				new PsiOperator.Arity21<PsiStringy, PsiRegExp>
					("split", PsiStringy::psiSplit),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("sqrt", PsiComplexNumeric::psiSqrt),
				new PsiOperator.Action
					("stack",
						(interpreter)->
						{
							final OperandStack ostack=interpreter.operandStack();
							ostack.push(new PsiArray((java.util.ArrayList<PsiObject>)ostack.clone()));
						}
					),
				new PsiOperator.Arity11<PsiProcess>
					("status", PsiProcess::psiStatus),
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
				new PsiOperator.Arity11<PsiStringy>
					("stringreader", PsiStringReader::new),
				new PsiOperator.Arity11<PsiString>
					("stringwriter", PsiStringWriter::new),
				new PsiOperator.Arity21<PsiAdditive, PsiAdditive>
					("sub", PsiAdditive::psiSub),
				new PsiOperator.Arity20<PsiStringy, PsiStringy>
					("symlink", FileSystem::psiSymLink),
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
				new PsiOperator.Arity11<PsiQueuelike>
					("take", PsiQueuelike::psiTake),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("tan", PsiComplexNumeric::psiTan),
				new PsiOperator.Arity11<PsiComplexNumeric>
					("tanh", PsiComplexNumeric::psiTanh),
				new PsiOperator.Arity21<PsiBitwise, PsiInteger>
					("testbit", PsiBitwise::psiTestBit),
				new PsiOperator.Arity01
					("time", Time::psiTime),
				new PsiOperator.Arity11<PsiConvertableToInteger>
					//("tointeger", (a)->((PsiConvertableToInteger)a).psiToInteger()),
					("tointeger", PsiConvertableToInteger::psiToInteger),
				new PsiOperator.Action
					("tokens",
						(interpreter)->
						{
							interpreter.interpretBraced(new PsiStringReader(
									(PsiStringy)interpreter.operandStack().popOperands(1)[0]));
						}
					),
				new PsiOperator.Arity11<PsiObject>
					("toname", PsiObject::psiToName),
				new PsiOperator.Arity11<PsiConvertableToReal>
					("toreal", PsiConvertableToReal::psiToReal),
				new PsiOperator.Arity11<PsiObject>
					("tostring", PsiObject::psiToString),
				new PsiOperator.Arity11<PsiObject>
					("type", PsiObject::psiType),
				new PsiOperator.Arity20<PsiDictlike, PsiStringy>
					("undef", PsiDictlike::psiUndef),
				new PsiOperator.Arity11<PsiRandom>
					("uniformboolean", PsiRandom::psiUniformBoolean),
				new PsiOperator.Arity21<PsiRandom, PsiNumeric>
					("uniformdeviate", PsiRandom::psiUniformDeviate),
				new PsiOperator.Arity21<PsiIterable, PsiStringy>
					("unite", PsiIterable::psiUnite),
				new PsiOperator.Arity11<PsiStringy>
					("uppercase", PsiStringy::psiUpperCase),
				new PsiOperator.Arity11<PsiIndexed>
					("values", PsiIndexed::psiValues),
				new PsiOperator.Arity10<PsiCondition>
					("wait", PsiCondition::psiWait),
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
				new PsiOperator.Arity20<PsiWritable, PsiInteger>
					("write", PsiWritable::psiWrite),
				new PsiOperator.Arity20
					("writestring", (a, b)->((PsiWritable)a).psiWriteString((PsiStringy)b)),
				new PsiOperator.Arity21<PsiLogical, PsiLogical>
					("xor", PsiLogical::psiXor),
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
