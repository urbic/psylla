package coneforest.psylla.engine;

/**
*	The Psylla language scripting engine factory.
*/
public class PsyllaScriptEngineFactory
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
		return new PsyllaScriptEngine(this);
	}

	/**
	*	@return a list consisting of single string {@code "psylla"}.
	*/
	@Override
	public java.util.List<String> getNames()
	{
		return java.util.Collections.unmodifiableList(java.util.Arrays.asList("psylla"));
	}

	@Override
	public String getProgram(final String... statements)
	{
		final var sb=new StringBuilder();
		for(final var statement: statements)
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
		return coneforest.psylla.Version.getVersion();
	}

	@Override
	public String getEngineVersion()
	{
		return coneforest.psylla.Version.getVersion();
	}

	/**
	*	Returns a name of a language.
	*
	*	@return the string {@code "Psylla"}.
	*/
	@Override
	public String getLanguageName()
	{
		return "Psylla";
	}

	/**
	*	@return a list consisting of single string {@code "application/x-psylla"}.
	*/
	@Override
	public java.util.List<String> getMimeTypes()
	{
		if(mimeTypes==null)
			mimeTypes=java.util.Collections.unmodifiableList(java.util.Arrays.asList("application/x-psylla"));
		return mimeTypes;
	}

	/**
	*	@return a list consisting of single string {@code "psy"}.
	*/
	@Override
	public java.util.List<String> getExtensions()
	{
		if(extensions==null)
			extensions=java.util.Collections.unmodifiableList(java.util.Arrays.asList("psy"));
		return extensions;
	}

	private java.util.List<String> mimeTypes;
	private java.util.List<String> extensions;
}
