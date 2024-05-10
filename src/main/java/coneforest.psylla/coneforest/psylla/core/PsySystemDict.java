package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

@Type("systemdict")
public class PsySystemDict
	extends PsyModule
{
	public PsySystemDict()
		throws PsyUndefinedException
	{
		importOperators(
				PsyAdditive.class,
				PsyAppendable.class,
				PsyArray.class,
				PsyBitArray.class,
				PsyBitSet.class,
				PsyBitwise.class,
				PsyBlockingQueue.class,
				PsyBounded.class,
				PsyClearable.class,
				PsyCloseable.class,
				PsyComplex.class,
				PsyCondition.class,
				PsyContext.class,
				PsyConvertableToInteger.class,
				PsyConvertableToIntegral.class,
				PsyConvertableToReal.class,
				PsyDict.class,
				PsyEvaluable.class,
				PsyFileReader.class,
				PsyFileSystem.class,
				PsyFileWriter.class,
				PsyFlushable.class,
				PsyFormalArray.class,
				PsyFormalDict.class,
				PsyFormalQueue.class,
				PsyFormalSet.class,
				PsyFormalStream.class,
				PsyIndexed.class,
				PsyIntegral.class,
				PsyLengthy.class,
				PsyLock.class,
				PsyLogical.class,
				PsyMatcher.class,
				PsyMultiplicative.class,
				PsyNumeric.class,
				PsyObject.class,
				PsyProc.class,
				PsyProcess.class,
				PsyRandom.class,
				PsyRange.class,
				PsyRational.class,
				PsyReadable.class,
				PsyReady.class,
				PsyRealNumeric.class,
				PsyRegExp.class,
				PsyResetable.class,
				PsyScalar.class,
				PsySequential.class,
				PsySet.class,
				PsyStream.class,
				PsyStreamable.class,
				PsyString.class,
				PsyStringReader.class,
				PsyStringWriter.class,
				PsyTextual.class,
				PsyTime.class,
				PsyWritable.class
			);

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
        put("stderr", new PsyWriter(new OutputStreamWriter(System.err)));
        put("stdin", new PsyReader(new InputStreamReader(System.in)));
        put("stdout", new PsyWriter(new OutputStreamWriter(System.out)));
        put("stdrandom", new PsyRandom());
        put("true", PsyBoolean.TRUE);
        put("username", new PsyName(System.getProperty("user.name")));
        put("version", new PsyName(Version.getVersion()));
	}
}
