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

import com.appmattus.kotlinfixture.TestContext
import com.appmattus.kotlinfixture.Unresolved
import com.appmattus.kotlinfixture.assertIsRandom
import com.appmattus.kotlinfixture.config.Configuration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ArrayResolverTest {
    private val context = TestContext(
        Configuration(),
        CompositeResolver(ArrayResolver(), StringResolver(), PrimitiveResolver())
    )

    @Test
    fun `Unknown class returns Unresolved`() {
        val result = context.resolve(Number::class)

        assertTrue(result is Unresolved)
    }

    @Test
    fun `Primitive array class returns Unresolved`() {
        val result = context.resolve(IntArray::class)

        assertTrue(result is Unresolved)
    }

    @Test
    fun `Array-Int class returns int class array`() {
        val result = context.resolve(Array<Int>::class)

        assertNotNull(result)
        assertEquals(Array<Int>::class, result::class)
    }

    @Test
    fun `Array-String class returns string array`() {
        val result = context.resolve(Array<String>::class)

        assertNotNull(result)
        assertEquals(Array<String>::class, result::class)
    }

    @Test
    fun `Random values returned`() {
        assertIsRandom {
            context.resolve(Array<String>::class)
        }
    }

    @Test
    fun `Length of array matches configuration value of 3`() {
        val context = context.copy(configuration = Configuration(repeatCount = { 3 }))

        val result = context.resolve(Array<String>::class) as Array<*>

        assertEquals(3, result.size)
    }

    @Test
    fun `Length of array matches configuration value of 7`() {
        val context = context.copy(configuration = Configuration(repeatCount = { 7 }))

        val result = context.resolve(Array<String>::class) as Array<*>

        assertEquals(7, result.size)
    }

    @Test
    fun `Array of arrays`() {
        val context = context.copy(configuration = Configuration(repeatCount = { 3 }))

        @Suppress("UNCHECKED_CAST")
        val result = context.resolve(Array<Array<String>>::class) as Array<Array<String>>

        assertEquals(3, result.size)
        result.forEach {
            assertEquals(3, it.size)
        }
    }
}
