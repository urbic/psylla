package coneforest.psylla.scripting;

import coneforest.psylla.core.*;
import coneforest.psylla.runtime.*;
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
	public static final String ARGV="arguments";
	public static final String FILENAME="script";

	private final ScriptEngineFactory factory;
	private final Interpreter interpreter;

	public PsyllaScriptEngine(final ScriptEngineFactory factory)
	{
		this.factory=factory;
		interpreter=new Interpreter();
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
	public Interpreter eval(final Reader reader, final ScriptContext context)
	{
		interpreter.interpret(reader);
		return interpreter;
	}

	@Override
	public Interpreter eval(final String string, final ScriptContext context)
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

	/*
	@SuppressWarnings("unchecked")
	@Override
	public void put(final String key, final Object obj)
	{
		interpreter.systemDict().put(key, (PsyObject)obj);
	}
	*/

}
