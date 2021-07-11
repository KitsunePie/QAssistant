package org.kitsunepie.qassistant.processors

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


@AutoService(Processor::class)
@SupportedAnnotationTypes("org.kitsunepie.qassistant.annotations.NormalHookEntry")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class NormalItemProcessor : BaseProcessor() {
    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        println(">>>> NormalItemProcessor init <<<<")
    }

    override fun process(annotations: Set<TypeElement?>?, roundEnv: RoundEnvironment): Boolean {
        println(">>>> NormalItemProcessor Processing <<<<")
        val elems =
            roundEnv.getElementsAnnotatedWith(org.kitsunepie.qassistant.annotations.NormalHookEntry::class.java)
        if (elems.isEmpty()) {
            println(">>>> NormalItemProcessor skipped because empty <<<<")
            return false
        }
        val hook = ClassName("org.kitsunepie.qassistant.app.hook.base", "BaseHook")
        val arrayList = ClassName("kotlin.collections", "ArrayList")
        val arrayListOfHook = arrayList.parameterizedBy(hook)
        val mGetApi = FunSpec.builder("getAnnotatedItemClassList")
            .run {
                addModifiers(KModifier.PUBLIC)
                beginControlFlow("return %T().apply", arrayListOfHook)
                elems.forEach {
                    addStatement("add(%T)", it.asType().asTypeName())
                }
                endControlFlow()
                build()
            }
        val file = FileSpec.builder("org.kitsunepie.qassistant.gen", "AnnotatedNormalItemList")
            .addType(
                TypeSpec.objectBuilder("DelayHooks")
                    .addFunction(mGetApi)
                    .build()
            )
            .build()
        file.writeTo(env!!.filer)
        println(">>>> NormalItemProcessor Processed(total: " + elems.size + ") <<<<")
        return true
    }
}
