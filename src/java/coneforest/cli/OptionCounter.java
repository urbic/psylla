package coneforest.cli;

/**
 * Class representing a counter. Integer option value, initially zero,
 * increments every time when option is processed.
 */
public class OptionCounter extends OptionWithoutArg
{
	public OptionCounter(String names)
	{
		super(names);
	}

	/**
	 * Returns current option value.
	 *
	 * @return value
	 */
	public Integer getValue()	{ return counter; }

	/**
	 * Increments option value by one.
	 */
	public void handle()	{ counter++; }

	private int counter=0;
}
