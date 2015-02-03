package coneforest.psi;
import coneforest.psi.errordict.*;

public class PsiErrorDictionary extends PsiModule
{
	public PsiErrorDictionary()
	{
		super();

		try
		{
			registerOperatorClasses
				(
					_handleerror.class
				);
		}
		catch(PsiException e)
		{
			// TODO
		}
	}
}
