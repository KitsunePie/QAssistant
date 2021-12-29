/*
 * QAssistant - An Xposed module for QQ/TIM
 * Copyright (C) 2021-2022
 * https://github.com/KitsunePie/QAssistant
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation and our eula published by us;
 *  either version 3 of the License, or any later version and our eula as published
 * by us.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/KitsunePie/QAssistant/blob/master/LICENSE.md>.
 */

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

@KotlinPoetKspPreview
class NormalItemProcessor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger) :
    SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols =
            resolver.getSymbolsWithAnnotation("org.kitsunepie.qassistant.annotations.NormalHookEntry")
                .filter { it is KSClassDeclaration }
                .map { it as KSClassDeclaration }
                .toList()
        if (symbols.isEmpty()) {
            logger.warn(">>>> NormalItem skipped because empty <<<<")
            return emptyList()
        }

        val hook = ClassName("org.kitsunepie.qassistant.app.hook.base", "BaseHook")
        val listOfHook = List::class.asClassName().parameterizedBy(hook)
        val normalHooks = PropertySpec.builder("normalHooks", listOfHook)
            .initializer("listOf(\n" +
                Array(symbols.size) { "%T" }.joinToString(",\n")
                + ")",
                *(symbols.map { it.asStarProjectedType().toTypeName() }.toTypedArray())
            )
            .build()

        logger.warn(">>>> NormalItem Processed(total: " + symbols.size + ") <<<<")
        val dependencies = Dependencies(true, *(symbols.mapNotNull {
            it.containingFile
        }.toTypedArray()))
        FileSpec.builder("org.kitsunepie.qassistant.gen", "AnnotatedNormalItemList")
            .addProperty(normalHooks)
            .build()
            .writeTo(codeGenerator, dependencies)
        return emptyList()
    }
}

@KotlinPoetKspPreview
class NormalItemProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return NormalItemProcessor(environment.codeGenerator, environment.logger)
    }
}
