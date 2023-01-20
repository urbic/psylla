module coneforest.psylla
{
	exports coneforest.psylla;
	exports coneforest.psylla.core;
	exports coneforest.psylla.tools;
	exports coneforest.psylla.tools.toolprovider;
	requires java.scripting;
	requires java.compiler;
	//requires ant.junit;
	requires junit;
	//requires jline;
	//requires javacc;
	exports coneforest.psylla.core;
	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
	provides java.util.spi.ToolProvider
		with coneforest.psylla.tools.toolprovider.PsyllaToolProvider;
}
