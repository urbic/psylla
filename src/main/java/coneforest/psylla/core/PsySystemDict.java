package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("systemdict")
public class PsySystemDict
	extends PsyModule
{
	public final PsyOperator[] OPERATORS
		={
			// "additive"
			new PsyOperator.Arity21<PsyAdditive, PsyAdditive>("add", PsyAdditive::psyAdd),
			new PsyOperator.Arity11<PsyAdditive>("neg", PsyAdditive::psyNeg),
			new PsyOperator.Arity21<PsyAdditive, PsyAdditive>("sub", PsyAdditive::psySub),

			// "time"
			new PsyOperator.Arity01("time", PsyTime::psyTime),

			// "writable"
			new PsyOperator.Arity20<PsyWritable, PsyInteger>("write", PsyWritable::psyWrite),
			new PsyOperator.Arity20<PsyWritable, PsyStringy>("writestring", PsyWritable::psyWriteString),

			// "stream"
			new PsyOperator.Arity21<PsyObject, PsyProc>("iterate", PsyStream::psyIterate),

			// "string"
			new PsyOperator.Arity01("string", PsyString::new),

			// "stringreader"
			new PsyOperator.Arity11<PsyStringy>("stringreader", PsyStringReader::new),

			// "stringwriter"
			new PsyOperator.Arity11<PsyString>("stringwriter", PsyStringWriter::new),

			// "realnumeric"
			new PsyOperator.Arity11<PsyRealNumeric>("ceiling", PsyRealNumeric::psyCeiling),
			new PsyOperator.Arity11<PsyRealNumeric>("floor", PsyRealNumeric::psyFloor),
			new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>("hypot", PsyRealNumeric::psyHypot),
			new PsyOperator.Arity11<PsyRealNumeric>("round", PsyRealNumeric::psyRound),
			new PsyOperator.Arity11<PsyRealNumeric>("signum", PsyRealNumeric::psySignum),

			// "regexp"
			new PsyOperator.Arity11<PsyStringy>("regexp", PsyRegExp::new),

			// "resetable"
			new PsyOperator.Arity10<PsyResetable>("reset", PsyResetable::psyReset),

			// "setlike"
			new PsyOperator.Arity21<PsySetlike, PsyObject>("contains", PsySetlike::psyContains),
			new PsyOperator.Arity21<PsySetlike, PsySetlike>("intersects", PsySetlike::psyIntersects),
			new PsyOperator.Arity20<PsySetlike, PsyObject>("remove", PsySetlike::psyRemove),
			new PsyOperator.Arity20<PsySetlike, PsyIterable>("removeall", PsySetlike::psyRemoveAll),
			new PsyOperator.Arity20<PsySetlike, PsyIterable>("retainall", PsySetlike::psyRetainAll),

			// "bitset"
			new PsyOperator.Arity01("bitset", PsyBitSet::new),

			// "bitarray"
			new PsyOperator.Arity01("bitarray", PsyBitArray::new),

			// "blockingqueue"
			new PsyOperator.Arity11<PsyInteger>("blockingqueue", PsyBlockingQueue::new),

			// "dictlike"
			new PsyOperator.Arity20<PsyDictlike, PsyStringy>("undef", PsyDictlike::psyUndef),

			// "stringy"
			new PsyOperator.Arity31<PsyStringy, PsyInteger, PsyInteger>("indexofchar", PsyStringy::psyIndexOfChar),
			new PsyOperator.Arity31<PsyStringy, PsyStringy, PsyInteger>("indexofsubstring", PsyStringy::psyIndexOfSubstring),
			new PsyOperator.Arity11<PsyStringy>("lowercase", PsyStringy::psyLowerCase),
			new PsyOperator.Arity21<PsyStringy, PsyRegExp>("split", PsyStringy::psySplit),
			new PsyOperator.Arity11<PsyStringy>("uppercase", PsyStringy::psyUpperCase),

			// "streamable"
			new PsyOperator.Arity11<PsyStreamable>("stream", PsyStreamable::psyStream),
			new PsyOperator.Arity20<PsyStreamable, PsyObject>("forall", PsyStreamable::psyForAll),

			// "random"
			new PsyOperator.Arity01("random", PsyRandom::new),
			new PsyOperator.Arity20<PsyRandom, PsyInteger>("setseed", PsyRandom::psySetSeed),

			// "evaluable"
			new PsyOperator.Arity10<PsyEvaluable>("eval", PsyEvaluable::psyEval),

			// "bounded"
			new PsyOperator.Arity11<PsyBounded>("capacity", PsyBounded::psyCapacity),
			new PsyOperator.Arity11<PsyBounded>("isfull", PsyBounded::psyIsFull),

			// "clearable"
			new PsyOperator.Arity10<PsyClearable>("clear", PsyClearable::psyClear),

			// "closeable"
			new PsyOperator.Arity10<PsyCloseable>("close", PsyCloseable::psyClose),

			// "complex"
			new PsyOperator.Arity11<PsyComplex>("arg", PsyComplex::psyArg),
			new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>("complex", PsyComplex::new),
			new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>("complexpolar", PsyComplex::psyFromPolar),
			new PsyOperator.Arity11<PsyComplex>("conjugate", PsyComplex::psyConjugate),
			new PsyOperator.Arity11<PsyComplex>("imagpart", PsyComplex::psyImagPart),
			new PsyOperator.Arity11<PsyComplex>("realpart", PsyComplex::psyRealPart),

			// "condition"
			new PsyOperator.Arity10<PsyCondition>("notify", PsyCondition::psyNotify),
			new PsyOperator.Arity10<PsyCondition>("wait", PsyCondition::psyWait),

			// "flushable"
			new PsyOperator.Arity10<PsyFlushable>("flush", PsyFlushable::psyFlush),

			// "filereader"
			new PsyOperator.Arity11<PsyStringy>("filereader", PsyFileReader::new),

			// "filewriter"
			new PsyOperator.Arity11<PsyStringy>("filewriter", PsyFileWriter::new),

			// "lock"
			new PsyOperator.Arity11<PsyLock>("condition", PsyLock::psyCondition),
			new PsyOperator.Arity01("lock", PsyLock::new),

			// "matcher"
			new PsyOperator.Arity21<PsyStringy, PsyRegExp>("matcher", PsyMatcher::new),
			new PsyOperator.Arity11<PsyMatcher>("matches", PsyMatcher::psyMatches),
			new PsyOperator.Arity21<PsyMatcher, PsyStringy>("replaceall", PsyMatcher::psyReplaceAll),

			// "process"
			new PsyOperator.Arity11<PsyDictlike>("process", PsyProcess::new),
			new PsyOperator.Arity11<PsyProcess>("processerror", PsyProcess::psyProcessError),
			new PsyOperator.Arity11<PsyProcess>("processreader", PsyProcess::psyProcessReader),
			new PsyOperator.Arity11<PsyProcess>("processwriter", PsyProcess::psyProcessWriter),
			new PsyOperator.Arity11<PsyProcess>("status", PsyProcess::psyStatus),

			// "queuelike"
			new PsyOperator.Arity11<PsyQueuelike>("dequeue", PsyQueuelike::psyDequeue),
			new PsyOperator.Arity20<PsyQueuelike, PsyObject>("enqueue", PsyQueuelike::psyEnqueue),
			new PsyOperator.Arity20<PsyQueuelike, PsyObject>("give", PsyQueuelike::psyGive),
			new PsyOperator.Arity11<PsyQueuelike>("take", PsyQueuelike::psyTake),

			// "range"
			new PsyOperator.Arity31<PsyRealNumeric, PsyRealNumeric, PsyRealNumeric>("range", PsyRange::new),

			// "readable"
			new PsyOperator.Arity11<PsyReadable>("ready", PsyReadable::psyReady),
			new PsyOperator.Arity21<PsyReadable, PsyInteger>("skip", PsyReadable::psySkip),
			new PsyOperator.Action("read",
				()->
				{
					final var ostack=PsyContext.psyCurrentContext().operandStackBacked(1);
					final PsyInteger oCharacter=ostack.<PsyReadable>getBacked(0).psyRead();
					boolean notEOF=(oCharacter!=PsyInteger.MINUS_ONE);
					if(notEOF)
						ostack.push(oCharacter);
					ostack.push(PsyBoolean.valueOf(notEOF));
				}),

			// "logical"
			new PsyOperator.Arity21<PsyLogical, PsyLogical>("and", PsyLogical::psyAnd),
			new PsyOperator.Arity11<PsyLogical>("not", PsyLogical::psyNot),
			new PsyOperator.Arity21<PsyLogical, PsyLogical>("or", PsyLogical::psyOr),
			new PsyOperator.Arity21<PsyLogical, PsyLogical>("xor", PsyLogical::psyXor),

			// "bitwise"
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>("bitshift", PsyBitwise::psyBitShift),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>("clearbit", PsyBitwise::psyClearBit),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>("flipbit", PsyBitwise::psyFlipBit),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>("setbit", PsyBitwise::psySetBit),
			new PsyOperator.Arity21<PsyBitwise, PsyInteger>("testbit", PsyBitwise::psyTestBit),

			// "lengthy"
			new PsyOperator.Arity11<PsyLengthy>("isempty", PsyLengthy::psyIsEmpty),
			new PsyOperator.Arity11<PsyLengthy>("length", PsyLengthy::psyLength),

			// "filesystem"
			new PsyOperator.Arity20<PsyStringy, PsyInteger>("changefilepermissions", PsyFileSystem::psyChangeFilePermissions),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("copyfile", PsyFileSystem::psyCopyFile),
			new PsyOperator.Arity10<PsyStringy>("createdirectory", PsyFileSystem::psyCreateDirectory),
			new PsyOperator.Arity01("currentdirectory", PsyFileSystem::psyCurrentDirectory),
			new PsyOperator.Arity10<PsyStringy>("deletefile", PsyFileSystem::psyDeleteFile),
			new PsyOperator.Arity11<PsyStringy>("fileaccesstime", PsyFileSystem::psyFileAccessTime),
			new PsyOperator.Arity21<PsyStringy, PsyStringy>("fileattribute", PsyFileSystem::psyFileAttribute),
			new PsyOperator.Arity11<PsyStringy>("filecreationtime", PsyFileSystem::psyFileCreationTime),
			new PsyOperator.Arity11<PsyStringy>("fileexists", PsyFileSystem::psyFileExists),
			new PsyOperator.Arity11<PsyStringy>("filemodifiedtime", PsyFileSystem::psyFileModifiedTime),
			new PsyOperator.Arity11<PsyStringy>("filepermissions", PsyFileSystem::psyFilePermissions),
			new PsyOperator.Arity11<PsyStringy>("files", PsyFileSystem::psyFiles),
			new PsyOperator.Arity11<PsyStringy>("filesize", PsyFileSystem::psyFileSize),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("hardlink", PsyFileSystem::psyHardLink),
			new PsyOperator.Arity11<PsyStringy>("isdirectory", PsyFileSystem::psyIsDirectory),
			new PsyOperator.Arity11<PsyStringy>("isfile", PsyFileSystem::psyIsFile),
			new PsyOperator.Arity21<PsyStringy, PsyStringy>("issamefile", PsyFileSystem::psyIsSameFile),
			new PsyOperator.Arity11<PsyStringy>("issymlink", PsyFileSystem::psyIsSymLink),
			new PsyOperator.Arity11<PsyStringy>("readlink", PsyFileSystem::psyReadLink),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("renamefile", PsyFileSystem::psyRenameFile),
			new PsyOperator.Arity20<PsyStringy, PsyStringy>("symlink", PsyFileSystem::psySymLink),

			// "arraylike"
			new PsyOperator.Arity31<PsyArraylike, PsyInteger, PsyInteger>("extractinterval", PsyArraylike::psyExtractInterval),
			new PsyOperator.Arity31<PsyArraylike, PsyInteger, PsyInteger>("getinterval", PsyArraylike::psyGetInterval),
			new PsyOperator.Arity30<PsyArraylike, PsyInteger, PsyObject>("insert", PsyArraylike::psyInsert),
			new PsyOperator.Arity30<PsyArraylike, PsyInteger, PsyIterable>("insertall", PsyArraylike::psyInsertAll),
			new PsyOperator.Arity11<PsyArraylike>("postchop", PsyArraylike::psyPostChop),
			new PsyOperator.Arity11<PsyArraylike>("prechop", PsyArraylike::psyPreChop),
			new PsyOperator.Arity20<PsyArraylike, PsyObject>("prepend", PsyArraylike::psyPrepend),
			new PsyOperator.Arity20<PsyArraylike, PsyIterable>("prependall", PsyArraylike::psyPrependAll),
			new PsyOperator.Arity30<PsyArraylike, PsyInteger, PsyIterable>("putinterval", PsyArraylike::psyPutInterval),
			new PsyOperator.Arity11<PsyArraylike>("reverse", PsyArraylike::psyReverse),
			new PsyOperator.Arity20<PsyArraylike, PsyInteger>("setlength", PsyArraylike::psySetLength),

			// "proc"
			new PsyOperator.Arity11<PsyProc>("bind", PsyProc::psyBind),

			// "indexed"
			new PsyOperator.Arity20<PsyIndexed, PsyObject>("delete", PsyIndexed::psyDelete),
			new PsyOperator.Arity11<PsyIndexed>("entries", PsyIndexed::psyEntries),
			new PsyOperator.Arity21<PsyIndexed, PsyObject>("extract", PsyIndexed::psyExtract),
			new PsyOperator.Arity21<PsyIndexed, PsyObject>("get", PsyIndexed::psyGet),
			new PsyOperator.Arity21<PsyIndexed, PsyIterable>("getall", PsyIndexed::psyGetAll),
			new PsyOperator.Arity11<PsyIndexed>("keys", PsyIndexed::psyKeys),
			new PsyOperator.Arity21<PsyIndexed, PsyObject>("known", PsyIndexed::psyKnown),
			new PsyOperator.Arity30<PsyIndexed, PsyObject, PsyObject>("put", PsyIndexed::psyPut),
			new PsyOperator.Arity21<PsyIndexed, PsyIterable>("slice", PsyIndexed::psySlice),
			new PsyOperator.Arity11<PsyIndexed>("values", PsyIndexed::psyValues),

			// "streamlike"
			new PsyOperator.Arity11<PsyStreamlike>("count", PsyStreamlike::psyCount),
			new PsyOperator.Arity21<PsyStreamlike, PsyExecutable>("filtered", PsyStreamlike::psyFiltered),
			new PsyOperator.Arity21<PsyStreamlike, PsyInteger>("limited", PsyStreamlike::psyLimited),
			new PsyOperator.Arity21<PsyStreamlike, PsyExecutable>("mapped", PsyStreamlike::psyMapped),
			new PsyOperator.Arity31<PsyStreamlike, PsyObject, PsyExecutable>("reduce", PsyStreamlike::psyReduce),
			new PsyOperator.Arity21<PsyStreamlike, PsyInteger>("skipped", PsyStreamlike::psySkipped),
			new PsyOperator.Arity21<PsyStreamlike, PsyExecutable>("sorted", PsyStreamlike::psySorted),

			// "appendable"
			new PsyOperator.Arity20<PsyAppendable, PsyObject>("append", PsyAppendable::psyAppend),
			new PsyOperator.Arity20<PsyAppendable, PsyIterable>("appendall", PsyAppendable::psyAppendAll),

			// "object"
			new PsyOperator.Arity11<PsyObject>("clone", PsyObject::psyClone),
			new PsyOperator.Arity21<PsyObject, PsyObject>("eq", PsyObject::psyEq),
			new PsyOperator.Arity11("hashcode", PsyObject::psyHashCode),
			new PsyOperator.Arity21<PsyObject, PsyStringy>("instanceof", PsyObject::psyInstanceOf),
			new PsyOperator.Arity21<PsyObject, PsyObject>("ne", PsyObject::psyNe),
			new PsyOperator.Arity11<PsyObject>("toname", PsyObject::psyToName),
			new PsyOperator.Arity11<PsyObject>("tostring", PsyObject::psyToString),
			new PsyOperator.Arity11<PsyObject>("type", PsyObject::psyType),

			// "arithmetic"
			new PsyOperator.Arity21<PsyArithmetic, PsyArithmetic>("div", PsyArithmetic::psyDiv),
			new PsyOperator.Arity21<PsyArithmetic, PsyArithmetic>("mul", PsyArithmetic::psyMul),

			// "scalar"
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("cmp", PsyScalar::psyCmp),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("ge", PsyScalar::psyGe),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("gt", PsyScalar::psyGt),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("le", PsyScalar::psyLe),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("lt", PsyScalar::psyLt),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("max", PsyScalar::psyMax),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("min", PsyScalar::psyMin),

			// "integer"
			new PsyOperator.Arity21<PsyInteger, PsyInteger>("idiv", PsyInteger::psyIdiv),
			new PsyOperator.Arity21<PsyInteger, PsyInteger>("mod", PsyInteger::psyMod),

			// "numeric"
			new PsyOperator.Arity11<PsyNumeric>("abs", PsyNumeric::psyAbs),
			new PsyOperator.Arity11<PsyNumeric>("acos", PsyNumeric::psyAcos),
			new PsyOperator.Arity11<PsyNumeric>("asin", PsyNumeric::psyAsin),
			new PsyOperator.Arity11<PsyNumeric>("atan", PsyNumeric::psyAtan),
			new PsyOperator.Arity11<PsyNumeric>("cbrt", PsyNumeric::psyCbrt),
			new PsyOperator.Arity11<PsyNumeric>("cos", PsyNumeric::psyCos),
			new PsyOperator.Arity11<PsyNumeric>("cosh", PsyNumeric::psyCosh),
			new PsyOperator.Arity11<PsyNumeric>("exp", PsyNumeric::psyExp),
			new PsyOperator.Arity11<PsyNumeric>("iszero", PsyNumeric::psyIsZero),
			new PsyOperator.Arity11<PsyNumeric>("log", PsyNumeric::psyLog),
			new PsyOperator.Arity11<PsyNumeric>("notzero", PsyNumeric::psyNotZero),
			new PsyOperator.Arity21<PsyNumeric, PsyNumeric>("pow", PsyNumeric::psyPow),
			new PsyOperator.Arity11<PsyNumeric>("sin", PsyNumeric::psySin),
			new PsyOperator.Arity11<PsyNumeric>("sinh", PsyNumeric::psySinh),
			new PsyOperator.Arity11<PsyNumeric>("sqrt", PsyNumeric::psySqrt),
			new PsyOperator.Arity11<PsyNumeric>("tan", PsyNumeric::psyTan),
			new PsyOperator.Arity11<PsyNumeric>("tanh", PsyNumeric::psyTanh),

			// "array"
			new PsyOperator.Arity01("array", PsyArray::new),
			new PsyOperator.Action("arraytomark",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStack();
					final var i=ostack.findMarkPosition();
					final var oArray=new PsyArray();
					for(int j=i+1; j<ostack.size(); j++)
						oArray.psyAppend(ostack.get(j));
					ostack.setSize(i);
					ostack.push(oArray);
				}),

			// "dict"
			new PsyOperator.Arity01("dict", PsyDict::new),
			new PsyOperator.Action("dicttomark",
				()->
				{
					final var ostack=PsyContext.psyCurrentContext().operandStack();
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
				}),

			// "set"
			new PsyOperator.Arity01("set", PsySet::new),
			new PsyOperator.Action("settomark",
				()->
				{
					final var ostack=PsyContext.psyCurrentContext().operandStack();
					final int i=ostack.findMarkPosition();
					final PsySet oSet=new PsySet();
					for(int j=ostack.size()-1; j>=i+1; j--)
						oSet.psyAppend(ostack.get(j));
					ostack.setSize(i);
					ostack.push(oSet);
				}),

			// From "context"
			new PsyOperator.Arity10<PsyDictlike>("begin", PsyContext::psyBegin),
			new PsyOperator.Action("cleardictstack",
				()->PsyContext.psyCurrentContext().dictStack().setSize(2)),
			new PsyOperator.Action("clearstack",
				()->PsyContext.psyCurrentContext().operandStack().clear()),
			new PsyOperator.Arity00("cleartomark", PsyContext::psyClearToMark),
			new PsyOperator.Arity00("copy", PsyContext::psyCopy),
			new PsyOperator.Arity01("countdictstack", PsyContext::psyCountDictStack),
			new PsyOperator.Arity01("countexecstack", PsyContext::psyCountExecStack),
			new PsyOperator.Arity01("countstack", PsyContext::psyCountStack),
			new PsyOperator.Arity01("counttomark", PsyContext::psyCountToMark),
			new PsyOperator.Arity01("currentcontext", PsyContext::psyCurrentContext),
			new PsyOperator.Arity01("currentdict", PsyContext::psyCurrentDict),
			new PsyOperator.Arity20<PsyStringy, PsyObject>("def", PsyContext::psyDef),
			new PsyOperator.Action("dictstack",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					interpreter.operandStack()
						.push(new PsyArray((java.util.ArrayList<PsyObject>)interpreter.dictStack().clone()));
				}),
			new PsyOperator.Arity00("dup", PsyContext::psyDup),
			new PsyOperator.Arity00("end", PsyContext::psyEnd),
			new PsyOperator.Arity00("exch", PsyContext::psyExch),
			new PsyOperator.Arity10<PsyObject>("exec", PsyContext::psyExec),
			/*registerOperator(new PsyOperator.Action("exec",
				()->PsyContext.psyCurrentContext().operandStackBacked(1).getBacked(0).invoke()));*/
			new PsyOperator.Action("execstack",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					interpreter.operandStack().push(
						new PsyArray((java.util.ArrayList<PsyObject>)interpreter.executionStack().clone()));
				}),
			new PsyOperator.Action("executive",
				()->PsyContext.psyCurrentContext().psyExecutive()),
			new PsyOperator.Arity00("exit", PsyContext::psyExit),
			new PsyOperator.Action("for",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
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
								public void action()
								{
									if(oCounter.psyGt(oLimit).booleanValue())
										PsyContext.psyCurrentContext().popLoopLevel();
									else
									{
										estack.push(this);
										ostack.push(oCounter);
										oCounter=(PsyRealNumeric)oCounter.psyAdd(oIncrement);
										oProc.invoke();
									}
								}
							}:
						new PsyOperator("#for_continue")
							{
								private PsyRealNumeric oCounter=oInitial;

								@Override
								public void action()
								{
									if(oCounter.psyLt(oLimit).booleanValue())
										PsyContext.psyCurrentContext().popLoopLevel();
									else
									{
										estack.push(this);
										ostack.push(oCounter);
										oCounter=(PsyRealNumeric)oCounter.psyAdd(oIncrement);
										oProc.invoke();
									}
								}
							});
					}),
				new PsyOperator.Action("fork",
					()->
						// TODO; error handling in forked context
					{
						final var interpreter=PsyContext.psyCurrentContext();
						final var ostack=interpreter.operandStackBacked(1);

						//ostack.ensureSize(2);
						final PsyObject o=ostack.getBacked(0);

						final var forkedDtack=(DictStack)interpreter.dictStack().clone();
						final Interpreter forkedInterpreter=new Interpreter()
							{
								private final DictStack dstack=forkedDtack;
								//{
								//	this.dstack=forkedDtack;
								//}

								@Override
								public void run()
								{
									o.invoke();
									handleExecutionStack();
									if(getStopFlag())
									{
										PsyErrorDict.OP_HANDLEERROR.invoke();
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
				()->
				{
					final var ostack=PsyContext.psyCurrentContext().operandStackBacked(1);
					System.exit(ostack.<PsyInteger>getBacked(0).intValue());
				}),
			new PsyOperator.Arity20<PsyBoolean, PsyObject>("if", PsyContext::psyIf),
			new PsyOperator.Arity30<PsyBoolean, PsyObject, PsyObject>("ifelse", PsyContext::psyIfElse),
			new PsyOperator.Arity11<PsyInteger>("index", PsyContext::psyIndex),
			new PsyOperator.Action("join",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					final PsyContext oContext=ostack.getBacked(0);
					oContext.psyJoin();
					final var joinedOstack=((Interpreter)oContext).operandStack();
					ostack.push(PsyMark.MARK);
					for(final PsyObject o: joinedOstack)
						ostack.push(o);
				}),
			new PsyOperator.Action("load",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					ostack.push(interpreter.psyLoad(ostack.getBacked(0)));
				}),
			new PsyOperator.Arity10<PsyProc>("loop", PsyContext::psyLoop),
			new PsyOperator.Arity10<PsyObject>("pop", (a)->{}),
			new PsyOperator.Arity11<PsyStringy>("namespace", PsyContext::psyNamespace),
			new PsyOperator.Action("prettyprint",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					final var oStdWriter=(PsyWriter)interpreter.dictStack().load("stdout");
					oStdWriter.psyWriteString(ostack.getBacked(0).psySyntax());
					oStdWriter.psyWriteString(interpreter.dictStack().load("eol"));
					oStdWriter.psyFlush();
				}),
			new PsyOperator.Action("print",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					interpreter.dictStack().<PsyWriter>load("stdout")
						.psyWriteString(ostack.getBacked(0));
				}),
			new PsyOperator.Action("quit", PsyContext::psyQuit),
			new PsyOperator.Arity20<PsyInteger, PsyObject>("repeat", PsyContext::psyRepeat),
			new PsyOperator.Action("require",
				()->
				{
					// TODO
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					interpreter.psyRequire(ostack.getBacked(0));
				}),
			new PsyOperator.Arity20<PsyInteger, PsyInteger>("roll", PsyContext::psyRoll),
			new PsyOperator.Arity10<PsyInteger>("sleep", PsyContext::psySleep),
			new PsyOperator.Action("stack",
				()->
				{
					final var ostack=PsyContext.psyCurrentContext().operandStack();
					ostack.push(new PsyArray((java.util.ArrayList<PsyObject>)ostack.clone()));
				}),
			new PsyOperator.Action("stop", PsyContext::psyStop),
			new PsyOperator.Action("stopped",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					final PsyObject oProc=ostack.getBacked(0);

					final int stopLevel=interpreter.pushStopLevel();
					oProc.invoke();
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
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(2);
					interpreter.dictStack().psyStore(ostack.getBacked(0), ostack.getBacked(1));
				}),
			new PsyOperator.Action("tokens",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					interpreter.interpretBraced(new PsyStringReader(ostack.<PsyStringy>getBacked(0)));
				}),
			new PsyOperator.Arity11<PsyRandom>("uniformboolean", PsyRandom::psyUniformBoolean),
			new PsyOperator.Arity21<PsyRandom, PsyRealNumeric>("uniformdeviate", PsyRandom::psyUniformDeviate),
			new PsyOperator.Action("warn",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					final PsyWriter stderror=interpreter.dictStack().load("stderr");
					stderror.psyWriteString(ostack.getBacked(0));
					stderror.psyFlush();
				}),
			new PsyOperator.Action("where",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					final PsyDictlike dict=interpreter.psyWhere(ostack.<PsyStringy>getBacked(0));
					if(dict!=null)
						ostack.push(dict);
					ostack.push(PsyBoolean.valueOf(dict!=null));
				}),
			new PsyOperator.Action("yield", Thread::yield),


			// ?
			new PsyOperator.Arity11<PsyConvertableToInteger>("tointeger", PsyConvertableToInteger::psyToInteger),
			new PsyOperator.Action("say",
				()->
				{
					final var interpreter=PsyContext.psyCurrentContext();
					final var ostack=interpreter.operandStackBacked(1);
					final var stdwriter=(PsyWriter)interpreter.dictStack().load("stdout");
					stdwriter.psyWriteString(ostack.getBacked(0));
					stdwriter.psyWriteString((PsyStringy)interpreter.dictStack().load("eol"));
					stdwriter.psyFlush();
				}),
		};

	public PsySystemDict()
		throws PsyException
	{
		for(var oOperator: OPERATORS)
			registerOperator(oOperator);

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
        put("librarypath", new PsyArray());
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

	private void registerOperator(final PsyOperator oOperator)
	{
		final var name=oOperator.getName();
		final var simpleName=name.substring(name.indexOf('@')+1);
		//System.out.println(simpleName);
		put(simpleName, oOperator);
	}
}
