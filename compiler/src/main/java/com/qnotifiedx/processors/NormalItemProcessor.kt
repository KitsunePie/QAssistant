package com.qnotifiedx.processors

import com.google.auto.service.AutoService
import com.qnotifiedx.annotations.NormalHookEntry
import com.squareup.javapoet.*
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror


@AutoService(Processor::class)
@SupportedAnnotationTypes("com.qnotifiedx.annotations.NormalHookEntry")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class NormalItemProcessor : BaseProcessor() {
    private val iProvider: TypeMirror? = null
    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        println(">>>> NormalItemProcessor init <<<<")
    }

    override fun process(annotations: Set<TypeElement?>?, roundEnv: RoundEnvironment): Boolean {

        println(">>>> NormalItemProcessor Processing <<<<")
        val annos: Set<Element> = roundEnv.getElementsAnnotatedWith(
            NormalHookEntry::class.java
        )
        if (annos.isNotEmpty()) {
            val absHook = ClassName.get("com.qnotifiedx.app.hook.base", "BaseNormalHook")
            val list = ClassName.get("java.util", "List")
            val beyond = MethodSpec.methodBuilder("getAnnotatedNormalItemClassList")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(list, absHook))
                .addStatement(
                    "\$T result = new \$T<>()",
                    ParameterizedTypeName.get(list, absHook),
                    ArrayList::class.java
                )
            for (e in annos) {
//                System.out.println("Processing >>> " + e.toString());
                beyond.addStatement("result.add(\$L.INSTANCE)", e.toString())
            }
            beyond.addStatement("return result")
            val util = TypeSpec.classBuilder("AnnotatedNormalItemList")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(beyond.build())
                .build()
            val javaFile = JavaFile.builder("com.qnotifiedx.gen", util)
                .build()
            try {
                javaFile.writeTo(env!!.filer)
            } catch (e: IOException) {
                println(e.message)
            }
            println(">>>> NormalItemProcessor Processed(total: " + annos.size + ") <<<<")
        } else {
            println(">>>> NormalItemProcessor skipped because empty <<<<")
        }
        return false
    }
}
