package coneforest.psi.engine;

/**
*	A Ψ language scripting engine.
*/
public class PsiScriptEngine
	extends javax.script.AbstractScriptEngine
{
	public PsiScriptEngine(javax.script.ScriptEngineFactory factory)
	{
		this.factory=factory;
		interpreter=new coneforest.psi.Interpreter();
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
	public Object eval(java.io.Reader reader, javax.script.ScriptContext context)
	{
		interpreter.interpret(reader);
		return interpreter;
	}

	@Override
	public Object eval(String string, javax.script.ScriptContext context)
	{
		interpreter.interpret(string);
		return interpreter;
	}

	@Override
	public coneforest.psi.core.PsiObject get(String key)
	{
		try
		{
			return interpreter.getSystemDict().get(key);
		}
		catch(coneforest.psi.core.PsiException e)
		{
			return null;
		}
	}

	public static final String ARGV="arguments";

	private javax.script.ScriptEngineFactory factory;

	private coneforest.psi.Interpreter interpreter;
}
