package com.example.nba.data.io

import java.io.Serializable

/** One page with user list */
data class PlayersListResponseIO(

    /** List of players in this response */
    val data: List<PlayerIO> = listOf(),

    /** Extra information for the paging */
    val meta: PagingMetaIO? = null

): Serializable