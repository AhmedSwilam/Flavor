package kw.com.pixel.flavor.model

data class Country(
    val ThreeLetterIsoCode: String,
    val allows_billing: Boolean,
    val allows_shipping: Boolean,
    val id: Int,
    val name: String,
    val numeric_iso_code: Int,
    val subject_to_vat: Boolean,
    val two_letter_iso_code: String,
    var isSelected:Boolean=false,
    var image:String
)