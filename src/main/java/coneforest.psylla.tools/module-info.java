/**
*	The Psylla language tools.
*
*	@author Anton Shvetz ‹shvetz.anton@gmail.com›
*	@provides java.util.spi.ToolProvider
*/
module coneforest.psylla.tools
{
	exports coneforest.psylla.tools.ant;
	exports coneforest.psylla.tools.processors;
	exports coneforest.psylla.tools.toolprovider;

	requires coneforest.clianthus;
	requires coneforest.psylla;
	requires java.compiler;

	provides java.util.spi.ToolProvider
		with coneforest.psylla.tools.toolprovider.PsyllaToolProvider;
}
