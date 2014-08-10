package coneforest.cli;

public class OptionPath
	extends OptionHolder<String[]>
{
	public OptionPath(String... names)
	{
		super(names);
	}

	@Override
	public String[] parseArg(String path)
	{
		return path.split(java.io.File.pathSeparator);
	}
}
