package com.category.api.client.configurations.Exceptions

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.xml.bind.annotation.*


@XmlRootElement(name = "Errors")
@XmlAccessorType(XmlAccessType.FIELD)
class Errors() {

    constructor(errorCode: Int, errorDescription: String?) : this() {
        errors.add(Error(errorCode = errorCode.toString(), errorDescription = errorDescription.orEmpty()))
    }

    operator fun get(index: Int) = errors[index]

    val size: Int @JsonIgnore get() = errors.size

    @XmlElement(name = "Error")
    var errors = mutableListOf<Error>()

    @XmlAccessorType(XmlAccessType.FIELD)
    class Error() {

        constructor(errorCode: String?, errorDescription: String?) : this() {
            this.errorCode = errorCode
            this.errorDescription = errorDescription
        }

        @XmlAttribute(name = "ErrorCode")
        var errorCode: String? = null

        @XmlAttribute(name = "ErrorDescription")
        var errorDescription: String? = null
    }
}