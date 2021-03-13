package com.qnotifiedx.processors

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.util.Elements
import javax.lang.model.util.Types


abstract class BaseProcessor : AbstractProcessor() {
    protected var env: ProcessingEnvironment? = null
    var mFiler: Filer? = null
    var types: Types? = null
    var elementUtils: Elements? = null

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        mFiler = processingEnv.filer
        types = processingEnv.typeUtils
        elementUtils = processingEnv.elementUtils
        env = processingEnv
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }
}