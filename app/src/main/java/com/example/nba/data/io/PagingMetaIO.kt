package com.example.nba.data.io

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** Meta information of paging response */
data class PagingMetaIO(

    /** How many pages are there in this database in total */
    @SerializedName("total_pages")
    val totalPages: Int? = null,

    /** Index of this page */
    @SerializedName("current_page")
    val currentPage: Int? = null,

    /** A flag whether there is another page after this one or not */
    @SerializedName("next_page")
    val nextPage: Int? = null,

    /** How many items are there received per page */
    @SerializedName("per_page")
    val perPage: Int? = null,

    /** How many items are there in this database in total */
    @SerializedName("total_count")
    val totalCount: Int? = null

): Serializable