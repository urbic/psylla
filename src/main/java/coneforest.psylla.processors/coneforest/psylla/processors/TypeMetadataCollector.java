package coneforest.psylla.processors;

import coneforest.psylla.runtime.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({"coneforest.psylla.runtime.Type",
	"coneforest.psylla.runtime.ErrorType",
	"coneforest.psylla.runtime.OperatorType"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class TypeMetadataCollector
	extends AbstractProcessor
{
	private Map<String, String> options;
	private Messager messager;

	public TypeMetadataCollector()
	{
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv)
	{
		final var md=options.get(getClass().getName()+".metadataDir");
		final var typeElements=roundEnv.getElementsAnnotatedWith(Type.class);

		try
		{
			for(final var element: typeElements)
			{
				final var typeName=element.getAnnotation(Type.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				try(final var ps=new PrintStream(
					new File(md+"/type", typeName), StandardCharsets.UTF_8))
				{
					ps.println(className);
				}
			}

			final var exceptionElements=roundEnv.getElementsAnnotatedWith(ErrorType.class);
			for(final var element: exceptionElements)
			{
				final var typeName=element.getAnnotation(ErrorType.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				try(final var ps=new PrintStream(
						new File(md+"/error", typeName), StandardCharsets.UTF_8))
				{
					ps.println(className);
				}
			}
			final var operatorElements=roundEnv.getElementsAnnotatedWith(OperatorType.class);
			for(final var element: operatorElements)
			{
				final var operatorName=element.getAnnotation(OperatorType.class).value();
				try(final var ps=new PrintStream(
						new File(md+"/operator", operatorName), StandardCharsets.UTF_8))
				{
					ps.println(element.getEnclosingElement()+"#"+element.getSimpleName());
				}
			}
		}
		catch(final IOException e)
		{
			messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
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
}
