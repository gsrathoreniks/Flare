/**
 * Copyright 2023 LaatonWalaBhoot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.pantheon.flare

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import java.io.File

fun generateEventPropertyMappings(props: List<String>) {
    // using kotlinpoet here

    val typeBuilder = TypeSpec.classBuilder("AnalyticsPayload")

    props.forEach {
        typeBuilder.addProperty(
            PropertySpec.builder(it.snakeToCamelCase(), Any::class.asTypeName().copy(true))
                .initializer("null")
                .mutable(true)
                .build()
        )
    }

    FileSpec.builder("io.pantheon.flare.generated", "AnalyticsPayload")
        .addType(typeBuilder.build())
        .build()
        .writeTo(File("flare/src/main/java"))
}

fun String.snakeToCamelCase(): String {
    val pattern = "_[a-z]".toRegex()
    return replace(pattern) { it.value.last().uppercase() }
}
