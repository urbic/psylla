package coneforest.psylla.core;
import coneforest.psylla.*;

public class PsySystemDict
	extends PsyModule
{
	public PsySystemDict()
		throws PsyException
	{
		//PsyInteger.register((Interpreter)PsyContext.psyCurrentContext());

		registerOperators
			(
				new PsyOperator.Arity11<PsyNumeric>
					("abs", PsyNumeric::psyAbs),
				new PsyOperator.Arity11<PsyNumeric>
					("acos", PsyNumeric::psyAcos),
				new PsyOperator.Arity21<PsyAdditive, PsyAdditive>
					("add", PsyAdditive::psyAdd),
				new PsyOperator.Arity21<PsyLogical, PsyLogical>
					("and", PsyLogical::psyAnd),
				new PsyOperator.Arity20<PsyAppendable, PsyObject>
					("append", PsyAppendable::psyAppend),
				new PsyOperator.Arity20<PsyAppendable, PsyIterable>
					("appendall", PsyAppendable::psyAppendAll),
				new PsyOperator.Arity11<PsyComplex>
					("arg", PsyComplex::psyArg),
				new PsyOperator.Arity01
					("array", PsyArray::new),
				new PsyOperator.Action
					("arraytomark",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							final var i=ostack.findMarkPosition();
							final var oArray=new PsyArray();
							for(int j=i+1; j<ostack.size(); j++)
								oArray.psyAppend(ostack.get(j));
							ostack.setSize(i);
							ostack.push(oArray);
						}
					),
				new PsyOperator.Arity11<PsyNumeric>
					("asin", PsyNumeric::psyAsin),
				new PsyOperator.Action
					("astore",
						(interpreter)->
						// TODO
						{
							final var ostack=interpreter.operandStackBacked(1);
							int count=ostack.<PsyInteger>getBacked(0).intValue();
							ostack.ensureSize(count);
							final var oArray=new PsyArray();
							while(--count>=0)
								oArray.psyAppend(ostack.pop());
							ostack.push(oArray);
						}
					),
				new PsyOperator.Arity11<PsyNumeric>
					("atan", PsyNumeric::psyAtan),
				new PsyOperator.Action
					("begin",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							interpreter.dictStack().psyBegin(ostack.getBacked(0));
						}
					),
				new PsyOperator.Action
					("binarysearch",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(3);
							final PsyArray oArray=ostack.getBacked(0);
							final PsyObject oKey=ostack.getBacked(1);
							final PsyProc oComparator=ostack.getBacked(2);

							final PsyInteger oIndex=oArray.psyBinarySearch(oKey, oComparator);
							final var index=oIndex.intValue();
							if(index>=0)
							{
								ostack.push(oIndex);
								ostack.push(PsyBoolean.TRUE);
							}
							else
							{
								ostack.push(PsyInteger.valueOf(-index-1));
								ostack.push(PsyBoolean.FALSE);
							}
						}
					),
				new PsyOperator.Arity11<PsyProc>
					("bind", PsyProc::psyBind),
				new PsyOperator.Arity01
					("bitarray", PsyBitArray::new),
				new PsyOperator.Arity01
					("bitset", PsyBitSet::new),
				new PsyOperator.Arity21<PsyBitwise, PsyInteger>
					("bitshift", PsyBitwise::psyBitShift),
				new PsyOperator.Arity11<PsyInteger>
					("blockingqueue", PsyBlockingQueue::new),
				new PsyOperator.Arity11<PsyInteger>
					("calendar", Time::psyCalendar),
				new PsyOperator.Arity11<PsyDictlike>
					("calendartime", Time::psyCalendarTime),
				new PsyOperator.Arity11<PsyBounded>
					("capacity", PsyBounded::psyCapacity),
				new PsyOperator.Action
					("capturegroup",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final var oGroup=ostack.<PsyMatcher>getBacked(0).psyCaptureGroup(ostack.getBacked(1));
							if(oGroup!=null)
								ostack.push(oGroup);
							ostack.push(PsyBoolean.valueOf(oGroup!=null));
						}
					),
				new PsyOperator.Action
					("capturegroupend",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final var oGroupEnd=ostack.<PsyMatcher>getBacked(0).psyCaptureGroupEnd(ostack.getBacked(1));
							if(oGroupEnd!=null)
								ostack.push(oGroupEnd);
							ostack.push(PsyBoolean.valueOf(oGroupEnd!=null));
						}
					),
				new PsyOperator.Action
					("capturegroupstart",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final var oGroupStart=ostack.<PsyMatcher>getBacked(0).psyCaptureGroupStart(ostack.getBacked(1));
							if(oGroupStart!=null)
								ostack.push(oGroupStart);
							ostack.push(PsyBoolean.valueOf(oGroupStart!=null));
						}
					),
				new PsyOperator.Arity11<PsyMatcher>
					("capturegroupcount", PsyMatcher::psyCaptureGroupCount),
				new PsyOperator.Arity11<PsyNumeric>
					("cbrt", PsyNumeric::psyCbrt),
				new PsyOperator.Arity11<PsyRealNumeric>
					("ceiling", PsyRealNumeric::psyCeiling),
				new PsyOperator.Arity20<PsyStringy, PsyInteger>
					("changefilepermissions", FileSystem::psyChangeFilePermissions),
				new PsyOperator.Arity10<PsyClearable>
					("clear", PsyClearable::psyClear),
				new PsyOperator.Arity21<PsyBitwise, PsyInteger>
					("clearbit", PsyBitwise::psyClearBit),
				new PsyOperator.Action
					("cleardictstack", (interpreter)->interpreter.dictStack().setSize(2)),
				new PsyOperator.Action
					("clearstack", (interpreter)->interpreter.operandStack().clear()),
				new PsyOperator.Action
					("cleartomark",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							ostack.setSize(ostack.findMarkPosition());
						}
					),
				new PsyOperator.Arity11<PsyObject>
					("clone", PsyObject::psyClone),
				new PsyOperator.Arity10<PsyCloseable>
					("close", PsyCloseable::psyClose),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("cmp", PsyScalar::psyCmp),
				new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>
					("complex", PsyComplex::new),
				new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>
					("complexpolar", PsyComplex::psyFromPolar),
				new PsyOperator.Arity11<PsyLock>
					("condition", PsyLock::psyCondition),
				new PsyOperator.Arity11<PsyComplex>
					("conjugate", PsyComplex::psyConjugate),
				new PsyOperator.Arity21<PsySetlike, PsyObject>
					("contains", PsySetlike::psyContains),
				//new PsyOperator.Arity21
				//	("convert", (a, b)->a.psyConvert((PsyType)b)),
				new PsyOperator.Action
					("copy",
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
						}
					),
				new PsyOperator.Arity20<PsyStringy, PsyStringy>
					("copyfile", FileSystem::psyCopyFile),
				new PsyOperator.Arity11<PsyNumeric>
					("cos", PsyNumeric::psyCos),
				new PsyOperator.Arity11<PsyNumeric>
					("cosh", PsyNumeric::psyCosh),
				new PsyOperator.Action
					("countdictstack",
						(interpreter)->
						interpreter.operandStack().push(PsyInteger.valueOf(interpreter.dictStack().size()))),
				new PsyOperator.Action
					("countexecstack",
						(interpreter)->
						interpreter.operandStack().push(PsyInteger.valueOf(interpreter.executionStack().size()))),
				new PsyOperator.Action
					("countstack",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							ostack.push(PsyInteger.valueOf(ostack.size()));
						}
					),
				new PsyOperator.Action
					("counttomark",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							ostack.push(PsyInteger.valueOf(-ostack.findMarkPosition()-1+ostack.size()));
						}
					),
				new PsyOperator.Arity10<PsyStringy>
					("createdirectory", FileSystem::psyCreateDirectory),
				new PsyOperator.Action
					("currentcontext", (interpreter)->interpreter.operandStack().push(interpreter)),
				new PsyOperator.Action
					("currentdict", (interpreter)->interpreter.operandStack().push(interpreter.currentDict())),
				new PsyOperator.Arity01
					("currentdirectory", FileSystem::psyCurrentDirectory),
				new PsyOperator.Action
					("currentnamespace",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							ostack.push(interpreter.currentNamespace());
						}
					),
				new PsyOperator.Action
					("def",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							interpreter.currentDict().psyPut(ostack.getBacked(0), ostack.getBacked(1));
						}
					),
				new PsyOperator.Arity20<PsyIndexed, PsyObject>
					("delete", PsyIndexed::psyDelete),
				new PsyOperator.Arity10<PsyStringy>
					("deletefile", FileSystem::psyDeleteFile),
				new PsyOperator.Arity11<PsyQueuelike>
					("dequeue", PsyQueuelike::psyDequeue),
				new PsyOperator.Arity01
					("dict", PsyDict::new),
				new PsyOperator.Action
					("dictstack",
						(interpreter)->
						interpreter.operandStack()
								.push(new PsyArray((java.util.ArrayList<PsyObject>)interpreter.dictStack().clone()))
					),
				new PsyOperator.Action
					("dicttomark",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							final var i=ostack.findMarkPosition();
							final var ostackSize=ostack.size();
							if((ostackSize-i) % 2==0)
								throw new PsyRangeCheckException();

							final var oDict=new PsyDict();
							for(int j=i+1; j<ostackSize; j++)
							{
								final var oKey=(PsyStringy)ostack.get(j++);
								final var o=ostack.get(j);
								oDict.psyPut(oKey, o);
							}
							ostack.setSize(i);
							ostack.push(oDict);
						}
					),
				new PsyOperator.Arity21<PsyArithmetic, PsyArithmetic>
					("div", PsyArithmetic::psyDiv),
				new PsyOperator.Action
					("dup",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							ostack.ensureSize(1);
							ostack.push(ostack.peek());
						}
					),
				new PsyOperator.Action
					("editline",
						(interpreter)->
						{
							try
							{
								final var ostack=interpreter.operandStack();
								final jline.ConsoleReader consoleReader
									=new jline.ConsoleReader();
								final var line=consoleReader.readLine();
								if(line!=null)
									ostack.push(new PsyString(line+"\n"));
								ostack.push(PsyBoolean.valueOf(line!=null));
							}
							catch(final java.io.IOException e)
							{
								throw new PsyIOErrorException();
							}
						}
					),
				new PsyOperator.Action
					("end",
						(interpreter)->
						{
							interpreter.dictStack().psyEnd();
						}
					),
				new PsyOperator.Arity20<PsyQueuelike, PsyObject>
					("enqueue", PsyQueuelike::psyEnqueue),
				new PsyOperator.Arity11<PsyIndexed>
					("entries", PsyIndexed::psyEntries),
				new PsyOperator.Arity21<PsyObject, PsyObject>
					("eq", PsyObject::psyEq),
				new PsyOperator.Action
					("eval",
						(interpreter)->
						{
							interpreter.operandStackBacked(1)
								.<PsyEvaluable>getBacked(0).eval(interpreter);
						}
					),
				new PsyOperator.Action
					("exch",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							ostack.push(ostack.getBacked(1));
							ostack.push(ostack.getBacked(0));
						}
					),
				new PsyOperator.Action
					("exec",
						(interpreter)->
						{
							interpreter.operandStackBacked(1)
								.getBacked(0).invoke(interpreter);
						}
					),
				new PsyOperator.Action
					("execstack",
						(interpreter)->
						interpreter.operandStack().push(
							new PsyArray((java.util.ArrayList<PsyObject>)interpreter.executionStack().clone()))),
				new PsyOperator.Action
					("executive", (interpreter)->interpreter.repl()),
				new PsyOperator.Action
					("exit",
						(interpreter)->
						{
							if(interpreter.currentLoopLevel()==-1)
								throw new PsyInvalidExitException();
							interpreter.executionStack().setSize(interpreter.popLoopLevel());
						}
					),
				new PsyOperator.Arity11<PsyNumeric>
					("exp", PsyNumeric::psyExp),
				new PsyOperator.Action
					("external",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							//ostack.push(((PsyClassLoader)interpreter.systemDict().get("classpath"))
							//		.psyExternal((PsyStringy)ostack.getBacked(0)));
							interpreter.classLoader().psyExternal(ostack.getBacked(0));
						}
					),
				new PsyOperator.Arity21<PsyIndexed, PsyObject>
					("extract", PsyIndexed::psyExtract),
				new PsyOperator.Arity31<PsyArraylike, PsyInteger, PsyInteger>
					("extractinterval", PsyArraylike::psyExtractInterval),
				new PsyOperator.Arity11<PsyStringy>
					("fileaccesstime", FileSystem::psyFileAccessTime),
				new PsyOperator.Arity21<PsyStringy, PsyStringy>
					("fileattribute", FileSystem::psyFileAttribute),
				new PsyOperator.Arity11<PsyStringy>
					("filecreationtime", FileSystem::psyFileCreationTime),
				new PsyOperator.Arity11<PsyStringy>
					("fileexists", FileSystem::psyFileExists),
				new PsyOperator.Arity11<PsyStringy>
					("filemodifiedtime", FileSystem::psyFileModifiedTime),
				new PsyOperator.Arity11<PsyStringy>
					("filepermissions", FileSystem::psyFilePermissions),
				new PsyOperator.Arity11<PsyStringy>
					("filereader", PsyFileReader::new),
				new PsyOperator.Arity11<PsyStringy>
					("files", FileSystem::psyFiles),
				new PsyOperator.Arity11<PsyStringy>
					("filesize", FileSystem::psyFileSize),
				new PsyOperator.Arity11<PsyStringy>
					("filewriter", PsyFileWriter::new),
				new PsyOperator.Action
					("find",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyMatcher matcher=ostack.getBacked(0);
							boolean resultValue=matcher.psyFind().booleanValue();
							if(resultValue)
								ostack.push(matcher);
							ostack.push(PsyBoolean.valueOf(resultValue));
						}
					),
				new PsyOperator.Action
					("findall",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(3);
							final var oMatcher=new PsyMatcher(ostack.getBacked(0), ostack.getBacked(1));
							final var o=ostack.getBacked(2);

							final var loopLevel=interpreter.pushLoopLevel();
							while(true)
							{
								if(oMatcher.psyFind().booleanValue())
								{
									ostack.push(oMatcher);
									o.invoke(interpreter);
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
				new PsyOperator.Arity21<PsyBitwise, PsyInteger>
					("flipbit", PsyBitwise::psyFlipBit),
				new PsyOperator.Arity11<PsyRealNumeric>
					("floor", PsyRealNumeric::psyFloor),
				new PsyOperator.Arity10<PsyFlushable>
					("flush", PsyFlushable::psyFlush),
				new PsyOperator.Action
					("for",
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
						}
					),
				new PsyOperator.Arity20<PsyIterable, PsyObject>
					("forall", PsyIterable::psyForAll),
				new PsyOperator.Action
					("fork",
						(interpreter)->
						// TODO; error handling in forked context
						{
							final var ostack=interpreter.operandStackBacked(1);

							//ostack.ensureSize(2);
							final PsyObject o=ostack.getBacked(0);

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
						}
					),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("ge", PsyScalar::psyGe),
				new PsyOperator.Arity21<PsyIndexed, PsyObject>
					("get", PsyIndexed::psyGet),
				new PsyOperator.Arity21<PsyIndexed, PsyIterable>
					("getall", PsyIndexed::psyGetAll),
				new PsyOperator.Arity31<PsyArraylike, PsyInteger, PsyInteger>
					("getinterval", PsyArraylike::psyGetInterval),
				new PsyOperator.Arity20<PsyQueuelike, PsyObject>
					("give", PsyQueuelike::psyGive),
				new PsyOperator.Arity21<PsyIterable, PsyProc>
					("filter", PsyIterable::psyFilter),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("gt", PsyScalar::psyGt),
				new PsyOperator.Action
					("halt",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							System.exit(ostack.<PsyInteger>getBacked(0).intValue());
						}
					),
				new PsyOperator.Arity20<PsyStringy, PsyStringy>
					("hardlink", FileSystem::psyHardLink),
				new PsyOperator.Arity11<PsyObject>
					("hashcode", PsyObject::psyHashCode),
				new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>
					("hypot", PsyRealNumeric::psyHypot),
				new PsyOperator.Arity21<PsyInteger, PsyInteger>
					("idiv", PsyInteger::psyIdiv),
				new PsyOperator.Action
					("if",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							if(ostack.<PsyBoolean>getBacked(0).booleanValue())
								ostack.getBacked(1).invoke(interpreter);
						}
					),
				new PsyOperator.Action
					("ifelse",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(3);
							ostack.getBacked(ostack.<PsyBoolean>getBacked(0).booleanValue()? 1: 2)
									.invoke(interpreter);
						}
					),
				new PsyOperator.Arity11<PsyComplex>
					("imagpart", PsyComplex::psyImagPart),
				new PsyOperator.Action
					("index",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							int index=ostack.<PsyInteger>getBacked(0).intValue();
							if(index<0)
								throw new PsyRangeCheckException();
							ostack.ensureSize(index+1);
							ostack.push(ostack.get(ostack.size()-index-1));
						}
					),
				new PsyOperator.Arity30<PsyArraylike, PsyInteger, PsyObject>
					("insert", PsyArraylike::psyInsert),
				new PsyOperator.Arity30<PsyArraylike, PsyInteger, PsyIterable>
					("insertall", PsyArraylike::psyInsertAll),
				new PsyOperator.Arity21<PsyObject, PsyStringy>
					("instanceof", PsyObject::psyInstanceOf),
				new PsyOperator.Arity21<PsySetlike, PsySetlike>
					("intersects", PsySetlike::psyIntersects),
				new PsyOperator.Arity21<PsyInteger, PsyStringy>
					("inunicodeblock", PsyInteger::psyInUnicodeBlock),
				new PsyOperator.Arity11<PsyStringy>
					("isdirectory", FileSystem::psyIsDirectory),
				new PsyOperator.Arity11<PsyLengthy>
					("isempty", PsyLengthy::psyIsEmpty),
				new PsyOperator.Arity11<PsyStringy>
					("isfile", FileSystem::psyIsFile),
				new PsyOperator.Arity11<PsyBounded>
					("isfull", PsyBounded::psyIsFull),
				//new PsyOperator.Arity21
				//	("isinstance", (a, b)->((PsyType)a).psyIsInstance(b)),
				new PsyOperator.Action
					("isinstance",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							ostack.push(PsyBoolean.valueOf(
									interpreter.resolveType(ostack.<PsyStringy>getBacked(0).stringValue())
										.isInstance(ostack.getBacked(1))));
						}
					),
				new PsyOperator.Arity21<PsyStringy, PsyStringy>
					("issamefile", FileSystem::psyIsSameFile),
				new PsyOperator.Arity11<PsyStringy>
					("issymlink", FileSystem::psyIsSymLink),
				new PsyOperator.Arity11<PsyNumeric>
					("iszero", PsyNumeric::psyIsZero),
				new PsyOperator.Action
					("join",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyContext oContext=ostack.getBacked(0);
							oContext.psyJoin();
							final var joinedOstack=((Interpreter)oContext).operandStack();
							ostack.push(PsyMark.MARK);
							for(PsyObject o: joinedOstack)
								ostack.push(o);
						}
					),
				new PsyOperator.Arity11<PsyIndexed>
					("keys", PsyIndexed::psyKeys),
				new PsyOperator.Arity21<PsyIndexed, PsyObject>
					("known", PsyIndexed::psyKnown),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("le", PsyScalar::psyLe),
				new PsyOperator.Arity11<PsyLengthy>
					("length", PsyLengthy::psyLength),
				//new PsyOperator.Arity11<PsyStringy>
				//	("listdirectory", FileSystem::psyListDirectory),
				new PsyOperator.Action
					("load",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							ostack.push(interpreter.psyLoad(ostack.getBacked(0)));
						}
					),
				new PsyOperator.Arity01
					("lock", PsyLock::new),
				new PsyOperator.Arity11<PsyNumeric>
					("log", PsyNumeric::psyLog),
				new PsyOperator.Action
					("loop",
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
						}
					),
				new PsyOperator.Arity11<PsyStringy>
					("lowercase", PsyStringy::psyLowerCase),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("lt", PsyScalar::psyLt),
				new PsyOperator.Action
					("map",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final PsyContainer oContainer=ostack.getBacked(0);
							final PsyProc oProc=ostack.getBacked(1);
							final PsyAppendable oResult=(PsyAppendable)oContainer.psyNewEmpty();

							final int loopLevel=interpreter.pushLoopLevel();
							for(PsyObject o: (PsyContainer<? extends PsyObject>)oContainer)
							{
								ostack.push(o);
								oProc.invoke(interpreter);
								interpreter.handleExecutionStack(loopLevel);
								// TODO safe pop
								oResult.psyAppend(ostack.pop());
								if(interpreter.getStopFlag())
									break;
							}
							interpreter.popLoopLevel();
							ostack.push(oResult);
						}
					),
				new PsyOperator.Arity21<PsyStringy, PsyRegExp>
					("matcher", PsyMatcher::new),
				new PsyOperator.Action
					("matches",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyMatcher matcher=ostack.getBacked(0);
							boolean resultValue=matcher.psyMatches().booleanValue();
							if(resultValue)
								ostack.push(matcher);
							ostack.push(PsyBoolean.valueOf(resultValue));
						}
					),
				new PsyOperator.Action
					("matchesforall",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(4);
							final var estack=interpreter.executionStack();
							final PsyMatcher oMatcher=new PsyMatcher(ostack.getBacked(0), ostack.getBacked(1));
							final PsyObject oProc1=ostack.getBacked(2);
							final PsyObject oProc2=ostack.getBacked(3);

							interpreter.pushLoopLevel();
							estack.push(new PsyOperator("#matchesforall_continue")
								{
									@Override
									public void action(final Interpreter interpreter1)
										throws PsyException
									{
										boolean result=oMatcher.psyFind().booleanValue();
										ostack.push(oMatcher);
										if(result)
										{
											// HANDLE
											estack.push(this);

										}
										else
										{
											interpreter1.popLoopLevel();
										}
										(result? oProc1: oProc2).invoke(interpreter1);
									}
								});
						}
					),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("max", PsyScalar::psyMax),
				new PsyOperator.Arity21<PsyScalar, PsyScalar>
					("min", PsyScalar::psyMin),
				new PsyOperator.Arity21<PsyInteger, PsyInteger>
					("mod", PsyInteger::psyMod),
				new PsyOperator.Action
					("monitor",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final PsyLock oLock=ostack.getBacked(0);
							final PsyObject oProc=ostack.getBacked(1);

							if(oLock.isHeldByCurrentThread())
								throw new PsyInvalidContextException();
							oLock.lock();
							interpreter.executionStack().push(new PsyOperator.Action
								("#monitor_continue", (interpreter1)->oLock.unlock()));
							oProc.invoke(interpreter);
						}
					),
				new PsyOperator.Arity21<PsyArithmetic, PsyArithmetic>
					("mul", PsyArithmetic::psyMul),
				//new PsyOperator.Arity11<PsyStringy>
				//	("namespace", PsyNamespace::psyNamespace),
				new PsyOperator.Action
					("namespace",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							ostack.push(interpreter.namespacePool().psyNamespace(ostack.getBacked(0)));
						}
					),
				new PsyOperator.Arity21<PsyObject, PsyObject>
					("ne", PsyObject::psyNe),
				new PsyOperator.Arity11<PsyAdditive>
					("neg", PsyAdditive::psyNeg),
				new PsyOperator.Arity21<PsyRandom, PsyRealNumeric>
					("normaldeviate", PsyRandom::psyNormalDeviate),
				new PsyOperator.Arity11<PsyLogical>
					("not", PsyLogical::psyNot),
				new PsyOperator.Arity10<PsyCondition>
					("notify", PsyCondition::psyNotify),
				new PsyOperator.Arity11<PsyNumeric>
					("notzero", PsyNumeric::psyNotZero),
				new PsyOperator.Arity21<PsyLogical, PsyLogical>
					("or", PsyLogical::psyOr),
				new PsyOperator.Arity10<PsyObject>
					("pop", (a)->{}),
				new PsyOperator.Arity11<PsyArraylike>
					("postchop", PsyArraylike::psyPostChop),
				new PsyOperator.Arity21<PsyNumeric, PsyNumeric>
					("pow", PsyNumeric::psyPow),
				new PsyOperator.Arity11<PsyArraylike>
					("prechop", PsyArraylike::psyPreChop),
				new PsyOperator.Arity20<PsyArraylike, PsyObject>
					("prepend", PsyArraylike::psyPrepend),
				new PsyOperator.Arity20<PsyArraylike, PsyIterable>
					("prependall", PsyArraylike::psyPrependAll),
				new PsyOperator.Action
					("prettyprint",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyWriter stdwriter=interpreter.dictStack().load("stdout");
							stdwriter.psyWriteString(ostack.getBacked(0).psySyntax());
							stdwriter.psyWriteString(interpreter.dictStack().load("eol"));
							stdwriter.psyFlush();
						}
					),
				new PsyOperator.Action
					("print",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							interpreter.dictStack().<PsyWriter>load("stdout")
								.psyWriteString(ostack.getBacked(0));
						}
					),
				new PsyOperator.Action
					("println",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyWriter stdwriter=interpreter.dictStack().load("stdout");
							stdwriter.psyWriteString(ostack.getBacked(0));
							stdwriter.psyWriteString(interpreter.dictStack().load("eol"));
						}
					),
				new PsyOperator.Arity11<PsyDictlike>
					("process", PsyProcess::new),
				new PsyOperator.Arity11<PsyProcess>
					("processerror", PsyProcess::psyProcessError),
				new PsyOperator.Arity11<PsyProcess>
					("processreader", PsyProcess::psyProcessReader),
				new PsyOperator.Arity11<PsyProcess>
					("processwriter", PsyProcess::psyProcessWriter),
				new PsyOperator.Action
					("pstack",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							// TODO reverse order
							System.out.print("OPSTACK> ");
							for(PsyObject o: ostack)
								System.out.print(o.toSyntaxString()+" ");
							System.out.println();
						}
					),
				new PsyOperator.Arity30<PsyIndexed, PsyObject, PsyObject>
					("put", PsyIndexed::psyPut),
				new PsyOperator.Arity30<PsyArraylike, PsyInteger, PsyIterable>
					("putinterval", PsyArraylike::psyPutInterval),
				new PsyOperator.Action
					("quit", (interpreter)->interpreter.quit()),
				new PsyOperator.Arity01
					("random", PsyRandom::new),
				new PsyOperator.Action
					("read",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyInteger oCharacter=ostack.<PsyReadable>getBacked(0).psyRead();
							boolean notEOF=(oCharacter!=PsyInteger.MINUS_ONE);
							if(notEOF)
								ostack.push(oCharacter);
							ostack.push(PsyBoolean.valueOf(notEOF));
						}
					),
				new PsyOperator.Action
					("readline",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyString oString=ostack.<PsyReadable>getBacked(0).psyReadLine();
							if(oString.length()>0)
								ostack.push(oString);
							ostack.push(PsyBoolean.valueOf(oString.length()>0));
						}
					),
				new PsyOperator.Arity11<PsyStringy>
					("readlink", FileSystem::psyReadLink),
				new PsyOperator.Action
					("readstring",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final PsyInteger oCount=ostack.getBacked(1);
							final PsyString oString=ostack.<PsyReadable>getBacked(0).psyReadString(oCount);
							ostack.push(oString);
							ostack.push(oString.psyLength().psyEq(oCount));
							//ostack.push(oString.psyLength().psyEq(PsyInteger.ZERO));
						}
					),
				new PsyOperator.Arity11<PsyReadable>
					("ready", PsyReadable::psyReady),
				new PsyOperator.Arity11<PsyStringy>
					("regexp", PsyRegExp::new),
				new PsyOperator.Arity11<PsyComplex>
					("realpart", PsyComplex::psyRealPart),
				new PsyOperator.Arity20<PsySetlike, PsyObject>
					("remove", PsySetlike::psyRemove),
				new PsyOperator.Arity20<PsySetlike, PsyIterable>
					("removeall", PsySetlike::psyRemoveAll),
				new PsyOperator.Arity20<PsyStringy, PsyStringy>
					("renamefile", FileSystem::psyRenameFile),
				new PsyOperator.Action
					("repeat",
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
				new PsyOperator.Arity21<PsyMatcher, PsyStringy>
					("replaceall", PsyMatcher::psyReplaceAll),
				new PsyOperator.Arity21<PsyAppendable, PsyInteger>
					("replicate", PsyAppendable::psyReplicate),
				new PsyOperator.Action
					("require",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							interpreter.psyRequire(ostack.getBacked(0));
						}
					),
				new PsyOperator.Arity10<PsyResetable>
					("reset", PsyResetable::psyReset),
				new PsyOperator.Arity20<PsySetlike, PsyIterable>
					("retainall", PsySetlike::psyRetainAll),
				new PsyOperator.Arity11<PsyArraylike>
					("reverse", PsyArraylike::psyReverse),
				new PsyOperator.Action
					("roll",
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
						}
					),
				new PsyOperator.Arity11<PsyRealNumeric>
					("round", PsyRealNumeric::psyRound),
				new PsyOperator.Action
					("say",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyWriter stdwriter=interpreter.dictStack().load("stdout");
							stdwriter.psyWriteString(ostack.getBacked(0));
							stdwriter.psyWriteString(interpreter.dictStack().load("eol"));
							stdwriter.psyFlush();
						}
					),
				new PsyOperator.Arity01
					("set", PsySet::new),
				new PsyOperator.Arity21<PsyBitwise, PsyInteger>
					("setbit", PsyBitwise::psySetBit),
				new PsyOperator.Arity20<PsyArraylike, PsyInteger>
					("setlength", PsyArraylike::psySetLength),
				new PsyOperator.Arity20<PsyRandom, PsyInteger>
					("setseed", PsyRandom::psySetSeed),
				new PsyOperator.Action
					("settomark",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							final int i=ostack.findMarkPosition();
							final PsySet oSet=new PsySet();
							for(int j=ostack.size()-1; j>=i+1; j--)
								oSet.psyAppend(ostack.get(j));
							ostack.setSize(i);
							ostack.push(oSet);
						}
					),
				new PsyOperator.Action
					("signalerror",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							interpreter.handleError(
									new PsyException(ostack.getBacked(0))
									{
										@Override
										public String getName()
										{
											return ostack.<PsyStringy>getBacked(1).stringValue();
										}
									}
								);
						}
					),
				new PsyOperator.Arity11<PsyRealNumeric>
					("signum", PsyRealNumeric::psySignum),
				new PsyOperator.Arity11<PsyNumeric>
					("sin", PsyNumeric::psySin),
				new PsyOperator.Arity11<PsyNumeric>
					("sinh", PsyNumeric::psySinh),
				new PsyOperator.Arity21<PsyReadable, PsyInteger>
					("skip", PsyReadable::psySkip),
				new PsyOperator.Arity10<PsyInteger>
					("sleep", PsyContext::psySleep),
				new PsyOperator.Arity21<PsyIndexed, PsyIterable>
					("slice", PsyIndexed::psySlice),
				new PsyOperator.Action
					("sort",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final PsyArray oArray=ostack.getBacked(0);
							final PsyObject oComparator=ostack.getBacked(1);

							ostack.push(oArray.psySort(new java.util.Comparator<PsyObject>()
									{
										@Override
										public int compare(final PsyObject o1, final PsyObject o2)
										{
											ostack.push(o1);
											ostack.push(o2);
											final int execLevel=interpreter.execLevel();
											oComparator.invoke(interpreter);
											interpreter.handleExecutionStack(execLevel);
											// TODO: ensure stack size
											return ((PsyInteger)ostack.pop()).intValue();
										}
									}));
						}
					),
				new PsyOperator.Arity21<PsyStringy, PsyRegExp>
					("split", PsyStringy::psySplit),
				new PsyOperator.Arity11<PsyNumeric>
					("sqrt", PsyNumeric::psySqrt),
				new PsyOperator.Action
					("stack",
						(interpreter)->
						{
							final var ostack=interpreter.operandStack();
							ostack.push(new PsyArray((java.util.ArrayList<PsyObject>)ostack.clone()));
						}
					),
				new PsyOperator.Arity11<PsyProcess>
					("status", PsyProcess::psyStatus),
				new PsyOperator.Action
					("stop", (interpreter)->interpreter.psyStop()),
				new PsyOperator.Action
					("stopped",
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
						}
					),
				new PsyOperator.Action
					("store",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							interpreter.dictStack().psyStore(ostack.getBacked(0), ostack.getBacked(1));
						}
					),
				new PsyOperator.Arity01
					("string", PsyString::new),
				new PsyOperator.Arity11<PsyStringy>
					("stringreader", PsyStringReader::new),
				new PsyOperator.Arity11<PsyString>
					("stringwriter", PsyStringWriter::new),
				new PsyOperator.Arity21<PsyAdditive, PsyAdditive>
					("sub", PsyAdditive::psySub),
				new PsyOperator.Arity20<PsyStringy, PsyStringy>
					("symlink", FileSystem::psySymLink),
				new PsyOperator.Action
					("synchronized",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(2);
							final PsyObject o=ostack.getBacked(0);
							final PsyObject oProc=ostack.getBacked(1);
							synchronized(o)
							{
								final int loopLevel=interpreter.pushLoopLevel();
								oProc.invoke(interpreter);
								interpreter.handleExecutionStack(loopLevel);
							}
						}
					),
				new PsyOperator.Arity11<PsyQueuelike>
					("take", PsyQueuelike::psyTake),
				new PsyOperator.Arity11<PsyNumeric>
					("tan", PsyNumeric::psyTan),
				new PsyOperator.Arity11<PsyNumeric>
					("tanh", PsyNumeric::psyTanh),
				new PsyOperator.Arity21<PsyBitwise, PsyInteger>
					("testbit", PsyBitwise::psyTestBit),
				new PsyOperator.Arity01
					("time", Time::psyTime),
				new PsyOperator.Arity11<PsyConvertableToInteger>
					("tointeger", PsyConvertableToInteger::psyToInteger),
				new PsyOperator.Action
					("tokens",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							interpreter.interpretBraced(new PsyStringReader(ostack.<PsyStringy>getBacked(0)));
						}
					),
				new PsyOperator.Arity11<PsyObject>
					("toname", PsyObject::psyToName),
				new PsyOperator.Arity11<PsyConvertableToReal>
					("toreal", PsyConvertableToReal::psyToReal),
				new PsyOperator.Arity11<PsyObject>
					("tostring", PsyObject::psyToString),
				new PsyOperator.Arity11<PsyObject>
					("type", PsyObject::psyType),
				new PsyOperator.Arity20<PsyDictlike, PsyStringy>
					("undef", PsyDictlike::psyUndef),
				new PsyOperator.Arity11<PsyRandom>
					("uniformboolean", PsyRandom::psyUniformBoolean),
				new PsyOperator.Arity21<PsyRandom, PsyRealNumeric>
					("uniformdeviate", PsyRandom::psyUniformDeviate),
				new PsyOperator.Arity21<PsyIterable, PsyStringy>
					("unite", PsyIterable::psyUnite),
				new PsyOperator.Arity11<PsyStringy>
					("uppercase", PsyStringy::psyUpperCase),
				new PsyOperator.Arity11<PsyIndexed>
					("values", PsyIndexed::psyValues),
				new PsyOperator.Arity10<PsyCondition>
					("wait", PsyCondition::psyWait),
				new PsyOperator.Action
					("warn",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyWriter stderror=interpreter.dictStack().load("stderr");
							stderror.psyWriteString(ostack.getBacked(0));
							stderror.psyFlush();
						}
					),
				new PsyOperator.Action
					("where",
						(interpreter)->
						{
							final var ostack=interpreter.operandStackBacked(1);
							final PsyDictlike dict=interpreter.psyWhere(ostack.<PsyStringy>getBacked(0));
							if(dict!=null)
								ostack.push(dict);
							ostack.push(PsyBoolean.valueOf(dict!=null));
						}
					),
				new PsyOperator.Arity20<PsyWritable, PsyInteger>
					("write", PsyWritable::psyWrite),
				new PsyOperator.Arity20<PsyWritable, PsyStringy>
					("writestring", PsyWritable::psyWriteString),
				new PsyOperator.Arity21<PsyLogical, PsyLogical>
					("xor", PsyLogical::psyXor),
				new PsyOperator.Action
					("yield", (interpreter)->Thread.yield())

			);

		final PsyArray oLibraryPath=new PsyArray();
		for(var pathItem:
				Config.getProperty("config.library.path").split(java.io.File.pathSeparator))
			oLibraryPath.psyAppend(new PsyName(pathItem));

		put("]", get("arraytomark"));
		put(">", get("dicttomark"));
		put(")", get("settomark"));
		put("?", get("prettyprint"));
		put("$error", new PsyDict());
		put("(", PsyMark.MARK);
		put("<", PsyMark.MARK);
		put("[", PsyMark.MARK);
		put("arguments", new PsyArray());
		put("classpath", new PsyArray());
		put("configdict", new PsyConfigDict());
		put("eol", new PsyName(System.getProperty("line.separator")));
		put("errordict", new PsyErrorDict());
		put("false", PsyBoolean.FALSE);
		put("librarypath", oLibraryPath);
		put("mark", PsyMark.MARK);
		put("mathE", PsyReal.E);
		put("mathPI", PsyReal.PI);
		put("mathI", PsyComplex.I);
		put("maxinteger", PsyInteger.MAX_VALUE);
		put("maxreal", PsyReal.MAX_VALUE);
		put("mininteger", PsyInteger.MIN_VALUE);
		put("minreal", PsyReal.MIN_VALUE);
		put("nan", PsyReal.NAN);
		put("null", PsyNull.NULL);
		put("osarch", new PsyName(System.getProperty("os.arch")));
		put("osname", new PsyName(System.getProperty("os.name")));
		put("osversion", new PsyName(System.getProperty("os.version")));
		put("processors", PsyInteger.valueOf(Runtime.getRuntime().availableProcessors()));
		put("product", new PsyName("Psylla"));
		put("stderr", new PsyWriter(new java.io.OutputStreamWriter(System.err)));
		put("stdin", new PsyReader(new java.io.InputStreamReader(System.in)));
		put("stdout", new PsyWriter(new java.io.OutputStreamWriter(System.out)));
		put("stdrandom", new PsyRandom());
		put("systemdict", this);
		put("true", PsyBoolean.TRUE);
		put("userdict", new PsyDict());
		put("username", new PsyName(System.getProperty("user.name")));
		put("version", new PsyName(Version.getVersion()));

	}

	public static void register(final Interpreter interpreter)
	{
		//PsyObject.register(interpreter);
		//PsyInteger.register(interpreter);
		//System.out.println(PsyObject.classTypeName());
		//System.out.println(PsyInteger.classTypeName());
		//interpreter.registerType(PsyObject.class);
		//interpreter.registerType(PsyInteger.class);
		//interpreter.registerType(PsySystemDict.class);
		//System.out.println(interpreter.resolveType("object"));
		//System.out.println(interpreter.resolveType("integer"));
		//System.out.println(interpreter.resolveType("module"));
	}
}
