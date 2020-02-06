package coneforest.psylla.engine;

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
	}

	@Override
	public javax.script.ScriptEngineFactory getFactory()
	{
		return factory;
	}

	@Override
	public javax.script.Bindings createBindings()
	{
		/*
		SimpleEnvironment env = new SimpleEnvironment();
		Bindings bindings = new KawaScriptBindings(env);
		return bindings;
		*/
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
		catch(final coneforest.psylla.core.PsyException e)
		{
			return null;
		}
	}

	public static final String ARGV="arguments";

	private final javax.script.ScriptEngineFactory factory;

	private final coneforest.psylla.Interpreter interpreter;
}
