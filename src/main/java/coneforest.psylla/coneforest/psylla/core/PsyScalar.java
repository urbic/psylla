package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code scalar}, a type bringing total ordering to
*	implementing Psylla type. This interface declares methods for comparison.
*
*	@param <T> a type of the second operand at binary comparison operation.
*/
@Type("scalar")
public interface PsyScalar<T extends PsyScalar>
	extends PsyObject
{

	/**
	*	Returns a {@code boolean} object representing the result of “less”
	*	comparison of this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a Ψ boolean value indicating if this object is less than given
	*	object.
	*/
	public PsyBoolean psyLt(final T oScalar);

	/**
	*	Returns a {@code boolean} object representing the result of “less or
	*	equal” comparison of this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a Ψ boolean value indicating if this object is less than or
	*	equal to given object.
	*/
	public PsyBoolean psyLe(final T oScalar);

	/**
	*	Returns a {@code boolean} object representing the result of “greater”
	*	comparison of this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a {@code boolean} result of comparison.
	*/
	public PsyBoolean psyGt(final T oScalar);

	/**
	*	Returns a {@code boolean} object representing the result of “greater or
	*	equal” comparison of this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a {@code boolean} result of comparison.
	*/
	public PsyBoolean psyGe(final T oScalar);

	/**
	*	Compares this object against given object and returns an {@code
	*	integer} indicating the result of the comparison. Returns negative
	*	value if this object is less than given one, zero if this object is
	*	equal to given one, and positive value if this object is greater than
	*	given one.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return a result of the comparison.
	*/
	public PsyInteger psyCmp(final T oScalar);

	default public PsyScalar psyMin(final T oScalar)
	{
		return psyLt(oScalar).booleanValue()? this: oScalar;
	}

	default public PsyScalar psyMax(final T oScalar)
	{
		return psyGt(oScalar).booleanValue()? this: oScalar;
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("cmp", PsyScalar::psyCmp),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("ge", PsyScalar::psyGe),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("gt", PsyScalar::psyGt),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("le", PsyScalar::psyLe),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("lt", PsyScalar::psyLt),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("max", PsyScalar::psyMax),
			new PsyOperator.Arity21<PsyScalar, PsyScalar>("min", PsyScalar::psyMin),
		};

}
