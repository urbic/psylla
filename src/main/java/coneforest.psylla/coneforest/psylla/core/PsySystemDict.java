package coneforest.psylla.core;
import coneforest.psylla.*;

@Type("systemdict")
public class PsySystemDict
	extends PsyModule
{
	public final PsyOperator[] OPERATORS
		={
			// "stream"
			//XXX new PsyOperator.Arity21<PsyObject, PsyProc>("iterate", PsyStream::psyIterate),

			// "random"
			new PsyOperator.Arity01("random", PsyRandom::new),
			new PsyOperator.Arity20<PsyRandom, PsyInteger>("setseed", PsyRandom::psySetSeed),
			new PsyOperator.Arity11<PsyRandom>("uniformboolean", PsyRandom::psyUniformBoolean),
			new PsyOperator.Arity21<PsyRandom, PsyRealNumeric>("uniformdeviate", PsyRandom::psyUniformDeviate),

			// "bounded"
			new PsyOperator.Arity11<PsyBounded>("capacity", PsyBounded::psyCapacity),
			new PsyOperator.Arity11<PsyBounded>("isfull", PsyBounded::psyIsFull),

			// ?
			new PsyOperator.Action("warn", oContext->
				{
					final var ostack=oContext.operandStackBacked(1);
					final PsyWriter stderror=oContext.dictStack().load("stderr");
					stderror.psyWriteString(ostack.getBacked(0));
					stderror.psyFlush();
				}),
			new PsyOperator.Action("say", oContext->
				{
					final var ostack=oContext.operandStackBacked(1);
					final var stdwriter=(PsyWriter)oContext.dictStack().load("stdout");
					stdwriter.psyWriteString(ostack.getBacked(0));
					stdwriter.psyWriteString((PsyTextual)oContext.dictStack().load("eol"));
					stdwriter.psyFlush();
				}),
			new PsyOperator.Action("editline", interpreter->
					{
						try
						{
							final var ostack=interpreter.operandStack();
							final jline.ConsoleReader consoleReader
								=new jline.ConsoleReader();
							final var line=consoleReader.readLine();
							if(line!=null)
								ostack.push(new PsyString(line+"\n"));
							ostack.push(PsyBoolean.of(line!=null));
						}
						catch(final java.io.IOException e)
						{
							throw new PsyIOErrorException();
						}
					}
				),
		};

	public PsySystemDict()
		throws PsyErrorException
	{
		for(var oOperator: PsyProc.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyBlockingQueue.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyLock.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyLengthy.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyAdditive.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyMultiplicative.OPERATORS)
			registerOperator(oOperator);
		//for(var oOperator: PsyArithmetic.OPERATORS)
		//	registerOperator(oOperator);
		for(var oOperator: PsyNumeric.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyTime.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyContext.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyScalar.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyObject.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyIndexed.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyWritable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFileSystem.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyBitwise.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFormalArray.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyLogical.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyReady.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyArray.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyDict.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsySet.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyComplex.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyClearable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyCloseable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFormalSet.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyRealNumeric.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyBitArray.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyBitSet.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyResetable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyTextual.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyEvaluable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFlushable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyRange.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFormalQueue.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFormalDict.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyRegExp.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyReadable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyCondition.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyString.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyStringReader.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyStringWriter.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFileReader.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFileWriter.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyAppendable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyRational.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyIntegral.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyFormalStream.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyStream.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsySequential.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyStreamable.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyProcess.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyMatcher.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyConvertableToReal.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyConvertableToInteger.OPERATORS)
			registerOperator(oOperator);
		for(var oOperator: PsyConvertableToIntegral.OPERATORS)
			registerOperator(oOperator);

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
        put("processors", PsyInteger.of(Runtime.getRuntime().availableProcessors()));
        put("product", new PsyName("Psylla"));
        put("stderr", new PsyWriter(new java.io.OutputStreamWriter(System.err)));
        put("stdin", new PsyReader(new java.io.InputStreamReader(System.in)));
        put("stdout", new PsyWriter(new java.io.OutputStreamWriter(System.out)));
        put("stdrandom", new PsyRandom());
        put("true", PsyBoolean.TRUE);
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
