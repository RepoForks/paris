package com.airbnb.paris

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec

class EmptyTypedArrayWrapperTest : StringSpec() {

    init {
        val wrapper = EmptyTypedArrayWrapper

        "invalid methods" {
            forAll { index: Int ->
                shouldThrow<IllegalStateException> {
                    wrapper.isNull(index)
                }
                true
            }

            forAll { at: Int ->
                shouldThrow<IllegalStateException> {
                    wrapper.getIndex(at)
                }
                true
            }

            forAll { index: Int, default: Boolean ->
                shouldThrow<IllegalStateException> {
                    wrapper.getBoolean(index, default)
                }
                true
            }

            // TODO etc
        }

        "valid methods" {
            wrapper.getIndexCount() shouldBe 0

            forAll { index: Int -> !wrapper.hasValue(index) }

            // Checks that this doesn't throw
            wrapper.recycle()
        }
    }
}
