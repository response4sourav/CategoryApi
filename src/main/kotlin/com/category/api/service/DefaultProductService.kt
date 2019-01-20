package com.category.api.service

import com.category.api.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Enums
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.math.NumberUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DefaultProductService : ProductService {

    @Value("\${textlabel.was.now}")
    private val wasNowLabel: String = ""

    @Value("\${textlabel.was.then.now}")
    private val wasThenNowLabel: String= ""

    @Value("\${textlabel.percent.discount}")
    private val percDiscountLabel: String= ""

    override fun getHexColor(basicColor: String): String {

        return if (basicColor.isNotEmpty() && Enums.getIfPresent(ColorRGB::class.java, basicColor.toUpperCase()).isPresent) {
            ColorRGB.valueOf(basicColor.toUpperCase()).colorHex
        } else StringUtils.EMPTY
    }

    override fun getPriceReduction(price: Price): Double {
        return getReductionAmount(getAmount(price.was), getAmount(price.now))
    }

    override fun formattedPrice(price: Any, currency: String): String {
        val priceVal = getFloatValue(getAmount(price))
        val currencySymbol = getCurrencySymbol(currency)
        return if (priceVal >= 10) {
            String.format("%s%.0f", currencySymbol, priceVal)
        } else {
            String.format("%s%.2f", currencySymbol, priceVal)
        }
    }

    override fun formatPriceLabel(price: Price, labelType: String): String {

        var label = LabelType.SHOWWASNOW
        if (Enums.getIfPresent(LabelType::class.java, labelType.toUpperCase()).isPresent)
            label = LabelType.valueOf(labelType.toUpperCase())

        val formattedLabel: String
        formattedLabel = when (label) {

            LabelType.SHOWWASNOW -> getWasNowPriceLabel(price)

            LabelType.SHOWWASTHENNOW -> getWasNowThenPriceLabel(price)

            LabelType.SHOWPERCDSCOUNT -> getPercDiscountLabel(price)

        }
        return formattedLabel
    }


    private fun getCurrencySymbol(currencyCode: String): String {
        return if (currencyCode.isNotEmpty() && Enums.getIfPresent(CurrencySymbol::class.java, currencyCode.toUpperCase()).isPresent) {
            CurrencySymbol.valueOf(currencyCode.toUpperCase()).currencySymbol
        } else StringUtils.EMPTY
    }

    private fun getReductionAmount(wasPrice: String, nowPrice: String): Double {
        return getDoubleValue(wasPrice) - getDoubleValue(nowPrice)
    }

    private fun getWasNowPriceLabel(price: Price): String {
        return String.format(wasNowLabel, formattedPrice(price.was, price.currency), formattedPrice(price.now, price.currency))
    }

    private fun getWasNowThenPriceLabel(price: Price): String {
        if (getAmount(price.then2).isNotEmpty()) {
            return String.format(wasThenNowLabel, formattedPrice(price.was, price.currency),
                    formattedPrice(price.then2, price.currency), formattedPrice(price.now, price.currency))
        } else if (getAmount(price.then1).isNotEmpty()) {
            return String.format(wasThenNowLabel, formattedPrice(price.was, price.currency),
                    formattedPrice(price.then1, price.currency), formattedPrice(price.now, price.currency))
        }
        return getWasNowPriceLabel(price)
    }

    private fun getPercDiscountLabel(price: Price): String {
        val wasPrice = getDoubleValue(getAmount(price.was))
        if (wasPrice > 0) {
            val percentDiscount = Math.round(getPriceReduction(price) / wasPrice * 100).toInt()
            return String.format(percDiscountLabel, percentDiscount, formattedPrice(price.now, price.currency))
        }
        return StringUtils.EMPTY
    }

    private fun getAmount(price: Any?): String {
        return if (price is String) price
        else {
            val mapper = ObjectMapper()
            mapper.convertValue(price, PriceValue::class.java).from!!
        }
    }

    private fun getDoubleValue(value: String): Double {
        return if (NumberUtils.isNumber(value)) value.toDouble() else 0.0
    }

    private fun getFloatValue(value: String): Float {
        return if (NumberUtils.isNumber(value)) value.toFloat() else 0f
    }
}
