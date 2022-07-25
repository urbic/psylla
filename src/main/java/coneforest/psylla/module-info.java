module coneforest.psylla
{
	exports coneforest.psylla;
	exports coneforest.psylla.tools;
	requires java.scripting;
	requires java.compiler;
	requires ant.junit;
	//requires junit;
	requires jline;
	//requires javacc;
	exports coneforest.psylla.core;
	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
}
