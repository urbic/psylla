/**
*	The Psylla language interpreter and core.
*
*	@author Anton Shvetz ‹shvetz.anton@gmail.com›
*	@provides javax.script.ScriptEngineFactory
*/
module coneforest.psylla
{
	exports coneforest.psylla.core;
	exports coneforest.psylla.engine;
	exports coneforest.psylla.runtime;

	requires coneforest.clianthus;
	requires java.scripting;

	provides javax.script.ScriptEngineFactory
		with coneforest.psylla.engine.PsyllaScriptEngineFactory;
}
