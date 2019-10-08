package ru.tatarchuk.exchange_rates.rest.response

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs", strict = false)
data class ValCurs(

    @field:Attribute(name = "Date")
    var date: String? = null,

    @field:Attribute(name = "name")
    var name: String? = null,

    @field:ElementList(name = "Valute", inline = true)
    var currencyList: List<Valute>? = null
)
