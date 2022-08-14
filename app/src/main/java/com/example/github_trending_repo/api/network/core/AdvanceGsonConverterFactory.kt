package com.example.github_trending_repo.api.network.core

import android.util.Log
import com.example.github_trending_repo.api.network.response.GsonErrorResponse
import io.github.wax911.library.converter.GraphConverter
import io.github.wax911.library.model.request.QueryContainerBuilder
import java.lang.reflect.Type
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdvanceGsonConverterFactory(
    val factory: GsonConverterFactory,
    val graphConverter: GraphConverter
) : Converter.Factory() {
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        if (type == QueryContainerBuilder::class.java) {
            return graphConverter.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
        }
        return factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val gsonConverter = factory
            .responseBodyConverter(type, annotations, retrofit)
        return AdvanceGsonResponseBodyConverter(gsonConverter)
    }
}

class AdvanceGsonResponseBodyConverter<T>(private val converter: Converter<ResponseBody, T>?) :
    Converter<ResponseBody, Any> {

    override fun convert(value: ResponseBody): Any? {
        try {
            return converter?.convert(value)
        } catch (e: Exception) {
            Log.e("Network Exception 2: ", e.toString())
            //  return BaseApiResponse<GsonErrorResponse>(GSON_ERROR.toInt(), e.toString())
            return GsonErrorResponse()
            //  return BaseApiResponse(false,"s","ss",null)
        }
    }
}
