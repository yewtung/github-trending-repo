package com.example.github_trending_repo.api.repository
import retrofit2.Response

open class BaseRepository(
) {
    protected fun <T : Any> resolveResponse(
        response: Response<T>,
        whenSuccess: (() -> Unit)? = null
    ): ApiCallState {

        return if (response.code() == 200) {
            whenSuccess?.let {
                run(it)
            }
            ApiCallState.COMPLETED(
                response.body()
            )
        } else {
            ApiCallState.ERROR(
                response.body().toString(),
            )
        }
    }

}
