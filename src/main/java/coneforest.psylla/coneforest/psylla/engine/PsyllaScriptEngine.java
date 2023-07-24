package coneforest.psylla.engine;

import coneforest.psylla.*;
import coneforest.psylla.core.*;
import java.io.Reader;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;

/**
*	The Psylla language scripting engine.
*/
public class PsyllaScriptEngine
	extends AbstractScriptEngine
{
	public PsyllaScriptEngine(final ScriptEngineFactory factory)
	{
		this.factory=factory;
		interpreter=new coneforest.psylla.Interpreter();
		interpreter.start();
	}

	@Override
	public ScriptEngineFactory getFactory()
	{
		return factory;
	}

	@Override
	public Bindings createBindings()
	{
		return null;
	}

	@Override
	public Object eval(final Reader reader, final ScriptContext context)
	{
		interpreter.interpret(reader);
		return interpreter;
	}

	@Override
	public Object eval(final String string, final ScriptContext context)
	{
		interpreter.interpret(string);
		return interpreter;
	}

	@Override
	public PsyObject get(final String key)
	{
		try
		{
			return interpreter.systemDict().get(key);
		}
		catch(final PsyErrorException e)
		{
			return null;
		}
	}

	public static final String ARGV="arguments";

	private final ScriptEngineFactory factory;

	private final Interpreter interpreter;
}
