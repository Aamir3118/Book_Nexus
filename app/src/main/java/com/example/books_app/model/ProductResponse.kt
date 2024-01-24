package com.example.books_app.model
data class ProductData(
    var id:Int,
    var title: String,
    var description: String,
    var price:Long,
    var discountPercentage:Double,
    var rating:Double,
    var stock:Long,
    var brand:String,
    var category:String,
    var thumbnail: String,
    var images:List<String>
)

data class ProductResponse(
    val products: List<ProductData>,
    val total:Int,
    val skip:Int,
    val limit:Int,
    val brandNames: List<String>?
)
