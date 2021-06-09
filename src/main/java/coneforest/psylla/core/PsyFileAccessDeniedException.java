package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("fileaccessdenied")
public class PsyFileAccessDeniedException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "fileaccessdenied";
	}
}
