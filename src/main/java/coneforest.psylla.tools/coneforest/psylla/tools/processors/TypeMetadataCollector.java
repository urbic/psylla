package coneforest.psylla.tools.processors;

import coneforest.psylla.runtime.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({"coneforest.psylla.runtime.Type",
	"coneforest.psylla.runtime.ErrorType",
	"coneforest.psylla.runtime.OperatorType"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class TypeMetadataCollector
	extends AbstractProcessor
{
	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv)
	{
		final var md=options.get("coneforest.psylla.tools.processors.TypeMetadataCollector.metadataDir");

		final var typeElements=roundEnv.getElementsAnnotatedWith(Type.class);

		for(final var element: typeElements)
		{
			try
			{
				final var typeName=element.getAnnotation(Type.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				final var ps=new PrintStream(new File(md+"/type", typeName));
				ps.println(className);
				ps.close();
			}
			catch(final FileNotFoundException e)
			{
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
			}
		}

		final var exceptionElements=roundEnv.getElementsAnnotatedWith(ErrorType.class);
		for(final var element: exceptionElements)
		{
			try
			{
				final var typeName=element.getAnnotation(ErrorType.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				final var ps=new PrintStream(new File(md+"/error", typeName));
				ps.println(className);
				ps.close();
			}
			catch(final FileNotFoundException e)
			{
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
			}
		}
		final var operatorElements=roundEnv.getElementsAnnotatedWith(OperatorType.class);
		for(final var element: operatorElements)
		{
			try
			{
				final var operatorName=element.getAnnotation(OperatorType.class).value();
				final var ps=new PrintStream(new File(md+"/operator", operatorName));
				ps.println(element.getEnclosingElement()+"#"+element.getSimpleName());
				ps.close();
			}
			catch(final FileNotFoundException e)
			{
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
			}
		}

		return false;
	}

	@Override
	public Set<String> getSupportedOptions()
	{
		return Set.of(getClass().getName()+".metadataDir");
	}

	@Override
	public void init(final ProcessingEnvironment penv)
	{
		super.init(penv);

		messager=penv.getMessager();
		options=penv.getOptions();
	}

	private Map<String, String> options;
	private Messager messager;
}
