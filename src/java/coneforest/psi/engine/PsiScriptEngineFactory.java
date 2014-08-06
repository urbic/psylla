package coneforest.psi.engine;

public class PsiScriptEngineFactory
	implements javax.script.ScriptEngineFactory
{
	@Override
	public String getEngineName()
	{
		return "Psi";
	}

	@Override
	public javax.script.ScriptEngine getScriptEngine()
	{
		return new PsiScriptEngine(this);
	}

	@Override
	public java.util.List<String> getNames()
	{
		java.util.ArrayList<String> names=new java.util.ArrayList<String>();
		names.add("psi");
		return names;
	}

	@Override
	public String getProgram(String... statements)
	{
		throw new UnsupportedOperationException(getClass().getName()+".getProgram not supported");
	}

	@Override
	public String getOutputStatement(String toDisplay)
	{
		throw new UnsupportedOperationException(getClass().getName()+".getOutputStatement not supported");
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args)
	{
		throw new UnsupportedOperationException(getClass().getName()+".getMethodCalSyntax not supported");
	}

	@Override
	public String getParameter(String key)
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
			return "MULTITHREADED";
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

	@Override
	public String getLanguageName()
	{
		return "Psi";
	}

	@Override
	public java.util.List<String> getMimeTypes()
	{
		if(mimeTypes==null)
		{
			java.util.ArrayList<String> mimeTypesList=new java.util.ArrayList<String>(1);
			mimeTypesList.add("application/psi");
			mimeTypes=java.util.Collections.unmodifiableList(mimeTypesList);
		}
		return mimeTypes;
	}

	@Override
	public java.util.List<String> getExtensions()
	{
		if(extensions==null)
		{
			java.util.ArrayList<String> extensionsList=new java.util.ArrayList<String>(1);
			extensionsList.add("psi");
			extensions=java.util.Collections.unmodifiableList(extensions);
		}
		return extensions;
	}

	private java.util.List<String> mimeTypes;
	private java.util.List<String> extensions;
}

