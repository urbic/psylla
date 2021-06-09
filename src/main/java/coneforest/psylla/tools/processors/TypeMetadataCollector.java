package coneforest.psylla.tools.processors;

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
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({"coneforest.psylla.Type", "coneforest.psylla.ExceptionType"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class TypeMetadataCollector extends AbstractProcessor
{
	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv)
	{
		final var md=options.get("coneforest.psylla.tools.processors.TypeMetadataCollector.metadataDir");
		final var typeElements=roundEnv.getElementsAnnotatedWith(coneforest.psylla.Type.class);

		for(final var element: typeElements)
		{
			try
			{
				final var typeName=element.getAnnotation(coneforest.psylla.Type.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				final var ps=new java.io.PrintStream(new java.io.File(md+"type/", typeName));
				ps.println(className);
				ps.close();
			}
			catch(final java.io.FileNotFoundException e)
			{
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
			}
		}

		final var exceptionElements=roundEnv.getElementsAnnotatedWith(coneforest.psylla.ExceptionType.class);
		for(final var element: exceptionElements)
		{
			try
			{
				final var typeName=element.getAnnotation(coneforest.psylla.ExceptionType.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				final var ps=new java.io.PrintStream(new java.io.File(md+"exception/", typeName));
				ps.println(className);
				ps.close();
			}
			catch(final java.io.FileNotFoundException e)
			{
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
			}
		}

		return false;
	}

	@Override
	public Set<String> getSupportedOptions()
	{
		return Set.of("coneforest.psylla.tools.processors.TypeMetadataCollector.typemapDir");
	}

	@Override
	public void init(final ProcessingEnvironment penv)
	{
		super.init(penv);

		messager=penv.getMessager();
		options=penv.getOptions();
	}

	private java.util.Map<String, String> options;
	private Messager messager;
}
