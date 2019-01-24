package com.category.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Data

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ColorSwatch (

    var basicColor: String = "",
    var color: String? = null,
    var colorSwatchUrl: String? = null,
    var imageUrl: String? = null,
    var isAvailable: Boolean? = null,
    var skuId: String? = null,
    var rgbColor: String? = null

)
