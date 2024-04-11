package com.example.pokedex.home.data.model

import com.google.gson.annotations.SerializedName

data class Stats(@SerializedName("base_stat") var baseStat: Int? = null)