package coneforest.cli;

public class OptionPath
	extends OptionHolder<String[]>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionPath(final String names)
	{
		super(names);
	}

	@Override
	public String[] parseArg(final String path)
	{
		return path.split(java.io.File.pathSeparator);
	}
}
