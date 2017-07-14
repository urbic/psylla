package coneforest.psi.engine;

/**
*	A Ψ programming language scripting engine.
*/
public class PsiScriptEngineFactory
	implements javax.script.ScriptEngineFactory
{
	/**
	*	@return a string {@code "Psylla"}.
	*/
	@Override
	public String getEngineName()
	{
		return "Psylla";
	}

	/**
	*	Returns an engine.
	*
	*	@return an engine.
	*/
	@Override
	public javax.script.ScriptEngine getScriptEngine()
	{
		return new PsiScriptEngine(this);
	}

	/**
	*	@return a list consisting of single string {@code "psi"}.
	*/
	@Override
	public java.util.List<String> getNames()
	{
		return java.util.Collections.unmodifiableList(java.util.Arrays.asList("psi"));
	}

	@Override
	public String getProgram(final String... statements)
	{
		final StringBuilder sb=new StringBuilder();
		for(String statement: statements)
		{
			sb.append(statement);
			sb.append('\n');
		}
		return sb.toString();
	}

	@Override
	public String getOutputStatement(final String toDisplay)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(getClass().getName()+".getOutputStatement not supported");
	}

	@Override
	public String getMethodCallSyntax(final String obj, final String m, final String... args)
	{
		throw new UnsupportedOperationException(getClass().getName()+".getMethodCalSyntax not supported");
	}

	@Override
	public String getParameter(final String key)
	{
		if(key.equals(javax.script.ScriptEngine.ENGINE))
			return getEngineName();
		if(key.equals(javax.script.ScriptEngine.ENGINE_VERSION))
			return getEngineVersion();
		if(key.equals(javax.script.ScriptEngine.NAME))
			return getEngineName();
		if(key.equals(javax.script.ScriptEngine.LANGUAGE))
			return getLanguageName();
		if(key.equals(javax.script.ScriptEngine.LANGUAGE_VERSION))
			return getLanguageVersion();
		if(key.equals("THREADING"))
			return "MULTITHREADED";	// TODO
		return null;
	}

	@Override
	public String getLanguageVersion()
	{
		return coneforest.psi.Version.getVersion();
	}

	@Override
	public String getEngineVersion()
	{
		return coneforest.psi.Version.getVersion();
	}

	/**
	*	Returns a name of a language.
	*
	*	@return a name of a language.
	*/
	@Override
	public String getLanguageName()
	{
		return "Psi";
	}

	/**
	*	@return a list consisting of single string {@code "application/x-psi"}.
	*/
	@Override
	public java.util.List<String> getMimeTypes()
	{
		if(mimeTypes==null)
			mimeTypes=java.util.Collections.unmodifiableList(java.util.Arrays.asList("application/x-psi"));
		return mimeTypes;
	}

	/**
	*	@return a list consisting of single string {@code "psi"}.
	*/
	@Override
	public java.util.List<String> getExtensions()
	{
		if(extensions==null)
			extensions=java.util.Collections.unmodifiableList(java.util.Arrays.asList("psi"));
		return extensions;
	}

	private java.util.List<String> mimeTypes;
	private java.util.List<String> extensions;
}

