package coneforest.psylla.engine;

import coneforest.psylla.core.*;

/**
*	The Psylla language scripting engine.
*/
public class PsyllaScriptEngine
	extends javax.script.AbstractScriptEngine
{
	public PsyllaScriptEngine(final javax.script.ScriptEngineFactory factory)
	{
		this.factory=factory;
		interpreter=new coneforest.psylla.Interpreter();
		interpreter.start();
	}

	@Override
	public javax.script.ScriptEngineFactory getFactory()
	{
		return factory;
	}

	@Override
	public javax.script.Bindings createBindings()
	{
		return null;
	}

	@Override
	public Object eval(final java.io.Reader reader, final javax.script.ScriptContext context)
	{
		interpreter.interpret(reader);
		return interpreter;
	}

	@Override
	public Object eval(final String string, final javax.script.ScriptContext context)
	{
		interpreter.interpret(string);
		return interpreter;
	}

	@Override
	public coneforest.psylla.core.PsyObject get(final String key)
	{
		try
		{
			return interpreter.systemDict().get(key);
		}
		catch(final coneforest.psylla.core.PsyErrorException e)
		{
			return null;
		}
	}

	public static final String ARGV="arguments";

	private final javax.script.ScriptEngineFactory factory;

	private final coneforest.psylla.Interpreter interpreter;
}
