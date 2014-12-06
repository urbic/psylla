package coneforest.psi.engine;

/**
 *	A Ψ programming language scripting engine.
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
		interpreter.eval(reader);
		return interpreter;
	}

	@Override
	public Object eval(String string, javax.script.ScriptContext context)
	{
		interpreter.eval(string);
		return interpreter;
	}

	@Override
	public coneforest.psi.PsiObject get(String key)
	{
		try
		{
			return interpreter.getSystemDictionary().psiGet(key);
		}
		catch(coneforest.psi.PsiException e)
		{
			return null;
		}
	}

	public static final String ARGV="arguments";

	private javax.script.ScriptEngineFactory factory;

	private coneforest.psi.Interpreter interpreter;
}
