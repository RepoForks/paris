package com.airbnb.paris.processor

import javax.lang.model.util.Elements

val Elements.VIEW_TYPE get() = this.getTypeElement("android.view.View")
