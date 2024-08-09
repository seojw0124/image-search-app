package com.jeongu.imagesearchapp.data.repository

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import com.jeongu.imagesearchapp.util.Constants.SEARCH_TYPE_IMAGE
import java.lang.reflect.Type

class CustomDeserializer : JsonDeserializer<SearchResultInfo> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchResultInfo {
        val jsonObject = json.asJsonObject
        return if (jsonObject.get("type").asString == SEARCH_TYPE_IMAGE)
            SearchResultInfo.ImageInfo(
            id = jsonObject.get("id").asString,
            type = jsonObject.get("type").asString,
            thumbnailUrl = jsonObject.get("thumbnailUrl").asString,
            siteName = jsonObject.get("siteName").asString,
            docUrl = jsonObject.get("docUrl").asString,
            dateTime = jsonObject.get("dateTime").asString,
            isBookmarked = jsonObject.get("isBookmarked").asBoolean
        )
        else SearchResultInfo.VideoInfo(
            id = jsonObject.get("id").asString,
            type = jsonObject.get("type").asString,
            thumbnail = jsonObject.get("thumbnail").asString,
            title = jsonObject.get("title").asString,
            url = jsonObject.get("url").asString,
            dateTime = jsonObject.get("dateTime").asString,
            isBookmarked = jsonObject.get("isBookmarked").asBoolean
        )
    }
}
