package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _add extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		PsiObject arithmetic2=opstack.pop();
		PsiObject arithmetic1=opstack.pop();

		try
		{
			opstack.push((PsiObject)((PsiArithmetic)arithmetic1).psiAdd((PsiArithmetic)arithmetic2));
			//opstack.push((PsiObject)((PsiArithmetic<PsiComplexNumeric>)arithmetic1).add((PsiArithmetic<PsiComplexNumeric>)arithmetic2));
			//opstack.push((PsiObject)((PsiComplexNumeric)arithmetic1).add((PsiComplexNumeric)arithmetic2));
		}
		catch(ClassCastException e)
		{
			opstack.push(arithmetic1);
			opstack.push(arithmetic2);
			interpreter.error("typecheck", this);
		}
	}
}
