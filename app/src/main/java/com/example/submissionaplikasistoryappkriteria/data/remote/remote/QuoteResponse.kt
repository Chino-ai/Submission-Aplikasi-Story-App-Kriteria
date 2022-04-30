package com.example.submissionaplikasistoryappkriteria.data.remote.remote


import com.google.gson.annotations.SerializedName


data class QuoteResponseItem(


	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("en")
	val en: String? = null
)
