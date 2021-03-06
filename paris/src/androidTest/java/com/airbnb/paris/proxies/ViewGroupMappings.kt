package com.airbnb.paris.proxies

import android.*
import android.view.*
import junit.framework.*

internal class ViewGroupMapping<I : Any> private constructor(
        testValues: List<I>,
        attrRes: Int,
        setProxyFunction: ViewGroupProxy.(I) -> Unit,
        setStyleBuilderValueFunction: ViewGroupStyleApplier.BaseStyleBuilder<*, *>.(I) -> Any,
        setStyleBuilderResFunction: ViewGroupStyleApplier.BaseStyleBuilder<*, *>.(Int) -> Any,
        /**
         * A function which, when called, will assert that the view has been successfully modified
         * by the associated proxy and/or style builder methods
         */
        assertViewSet: (ViewGroup, I) -> Unit) :
        BaseViewMapping<ViewGroupStyleApplier.BaseStyleBuilder<*, *>, ViewGroupProxy, ViewGroup, I>(
                testValues,
                attrRes,
                setProxyFunction,
                setStyleBuilderValueFunction,
                setStyleBuilderResFunction,
                assertViewSet) {

    companion object {

        fun <I : Any> withCustomAssert(
                testValues: List<I>,
                attrRes: Int,
                setProxyFunction: ViewGroupProxy.(I) -> Unit,
                setStyleBuilderValueFunction: ViewGroupStyleApplier.BaseStyleBuilder<*, *>.(I) -> Any,
                setStyleBuilderResFunction: ViewGroupStyleApplier.BaseStyleBuilder<*, *>.(Int) -> Any,
                assertViewSet: (ViewGroup, I) -> Unit): ViewGroupMapping<I> {
            return ViewGroupMapping(
                    testValues,
                    attrRes,
                    setProxyFunction,
                    setStyleBuilderValueFunction,
                    setStyleBuilderResFunction,
                    assertViewSet)
        }

        fun <I : Any> withAssertEquals(
                testValues: List<I>,
                attrRes: Int,
                setProxyFunction: ViewGroupProxy.(I) -> Unit,
                setStyleBuilderValueFunction: ViewGroupStyleApplier.BaseStyleBuilder<*, *>.(I) -> Any,
                setStyleBuilderResFunction: ViewGroupStyleApplier.BaseStyleBuilder<*, *>.(Int) -> Any,
                getViewFunction: (ViewGroup) -> I): ViewGroupMapping<I> {
            return withCustomAssert(
                    testValues,
                    attrRes,
                    setProxyFunction,
                    setStyleBuilderValueFunction,
                    setStyleBuilderResFunction,
                    { View, input -> Assert.assertEquals(input, getViewFunction(View)) })
        }
    }
}

internal val VIEW_GROUP_MAPPINGS = ArrayList<ViewGroupMapping<*>>().apply {

    // animateLayoutChanges
    add(ViewGroupMapping.withCustomAssert(
            BOOLS,
            R.attr.animateLayoutChanges,
            ViewGroupProxy::setAnimateLayoutChanges,
            ViewGroupStyleApplier.BaseStyleBuilder<*, *>::animateLayoutChanges,
            ViewGroupStyleApplier.BaseStyleBuilder<*, *>::animateLayoutChangesRes,
            { view, input ->
                val layoutTransition = view.layoutTransition
                Assert.assertTrue(if (input) layoutTransition != null else layoutTransition == null)
            }
    ))

    // clipChildren
    add(ViewGroupMapping.withAssertEquals(
            BOOLS,
            R.attr.clipChildren,
            ViewGroupProxy::setClipChildren,
            ViewGroupStyleApplier.BaseStyleBuilder<*, *>::clipChildren,
            ViewGroupStyleApplier.BaseStyleBuilder<*, *>::clipChildrenRes,
            { it.clipChildren }
    ))

    // clipToPadding
    add(ViewGroupMapping.withAssertEquals(
            BOOLS,
            R.attr.clipToPadding,
            ViewGroupProxy::setClipToPadding,
            ViewGroupStyleApplier.BaseStyleBuilder<*, *>::clipToPadding,
            ViewGroupStyleApplier.BaseStyleBuilder<*, *>::clipToPaddingRes,
            { it.clipToPadding }
    ))
}
