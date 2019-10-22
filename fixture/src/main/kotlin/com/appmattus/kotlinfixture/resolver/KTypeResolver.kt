/*
 * Copyright 2019 Appmattus Limited
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

package com.appmattus.kotlinfixture.resolver

import com.appmattus.kotlinfixture.Context
import com.appmattus.kotlinfixture.Unresolved
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal class KTypeResolver : Resolver {

    override fun resolve(context: Context, obj: Any): Any? {
        return if (obj is KType && obj.classifier is KClass<*>) {
            if (obj.isMarkedNullable && context.random.nextBoolean()) {
                null
            } else {
                context.resolve(obj.classifier!!)
            }
        } else {
            Unresolved
        }
    }
}
