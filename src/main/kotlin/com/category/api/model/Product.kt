package com.category.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Data

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
class Product {

    var additionalServices: List<Any>? = null
    var availabilityMessage: String? = null
    var brand: String? = null
    var categoryQuickViewEnabled: Boolean? = null
    var code: String? = null
    var colorSwatches: List<ColorSwatch>? = null
    var colorWheelMessage: String? = null
    var compare: Boolean? = null
    var defaultSkuId: String? = null
    var directorate: String? = null
    var displaySpecialOffer: String? = null
    var emailMeWhenAvailable: Boolean? = null
    var fabric: String? = null
    var features: List<Any>? = null
    var image: String? = null
    var isBundle: Boolean? = null
    var isInStoreOnly: Boolean? = null
    var isMadeToMeasure: Boolean? = null
    var isProductSet: Boolean? = null
    var nonPromoMessage: String? = null
    var outOfStock: Boolean? = null
    var price: Price? = null
    var productId: String? = null
    var promotionalFeatures: List<Any>? = null
    var swatchAvailable: Boolean? = null
    var swatchCategoryType: String? = null
    var title: String? = null
    var type: String? = null
    var priceLabel: String? = null
    var nowPrice: String? = null

}
