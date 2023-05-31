module coneforest.psylla
{
	exports coneforest.psylla.core;
	exports coneforest.psylla.tools.ant;
	exports coneforest.psylla.tools.processors;
	exports coneforest.psylla.tools.toolprovider;
	exports coneforest.psylla.tools;
	exports coneforest.psylla;

	requires java.scripting;
	requires java.compiler;
	//requires ant.junit;
	requires junit;
	requires coneforest.cli;
	//requires jline;
	//requires javacc;
	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
	provides java.util.spi.ToolProvider
		with coneforest.psylla.tools.toolprovider.PsyllaToolProvider;
}
