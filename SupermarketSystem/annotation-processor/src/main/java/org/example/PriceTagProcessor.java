package org.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.imageio.IIOException;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;


@SupportedAnnotationTypes("org.example.GeneratePriceTag") // Вказуємо, які анотації ми обробляємо
@SupportedSourceVersion(SourceVersion.RELEASE_21) // Вказуємо підтримувану версію вихідного коду
@AutoService(Processor.class)
public class PriceTagProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty())
            return false;
        for (Element element : roundEnv.getElementsAnnotatedWith(GeneratePriceTag.class)) { //аналіз всіх елементів з анотацією MyAnnotation
            GeneratePriceTag myAnnotation = element.getAnnotation(GeneratePriceTag.class);
            String[] fields = myAnnotation.fields();  // отримуємо значення з анотації
            PriceUnit unit = myAnnotation.unit();

            String packageToSpawn = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName().toString();

            TypeSpec.Builder builder = TypeSpec.classBuilder(className + "PriceTag").addModifiers(Modifier.PUBLIC);


            for (String field : fields) {
                builder.addField(String.class, field, Modifier.PUBLIC);
            }

            MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(packageToSpawn, element.getSimpleName().toString()), "product");

            for (Element fieldElement : element.getEnclosedElements()) {
                if (fieldElement instanceof VariableElement variableElement) {
                    String fieldName = variableElement.getSimpleName().toString();

                    boolean isInFields = false;
                    for (String f : fields) {
                        if (f.equals(fieldName)) {
                            isInFields = true;
                            break;
                        }
                    }
                    if (!isInFields) continue;

                    constructor.addStatement("this.$L = String.valueOf(product.$L)", fieldName, fieldName);

                    if (variableElement.getAnnotation(ValidPrice.class) != null) {
                        ValidPrice vp = variableElement.getAnnotation(ValidPrice.class);
                        double min = vp.min();
                        double max = vp.max();

                        constructor.addStatement("""
                    if (product.$L < $L || product.$L > $L) {
                        throw new IllegalArgumentException("Price " + product.$L + " is not in the appropriate range.");
                    }
                    """, fieldName, min, fieldName, max, fieldName);

                        constructor.addStatement("""
                    if (product.$L != Math.round(product.$L * 100.0) / 100.0) {
                        throw new IllegalArgumentException("Price " + product.$L + " is not valid");
                    }
                    """, fieldName, fieldName, fieldName);
                    }
                }
            }
            builder.addMethod(constructor.build());


            String unitName = switch (unit){
                case PER_1KG -> "UAH/kg";
                case PER_100G -> "UAH/100g";
                case PER_ONE -> "UAH/piece";
            };

            MethodSpec formatMethod = MethodSpec.methodBuilder("format")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return \"  |  \" + String.join(\"  |  \", $T.asList($L)) + $S + \"  |  \"", ClassName.get("java.util", "Arrays"), String.join(",", fields), " (" + unitName + ") ")
                    .build();
            builder.addMethod(formatMethod);

            JavaFile javaFile = JavaFile.builder(packageToSpawn, builder.build()).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return true; //ми закінчили обробку цих анотацій
    }
}