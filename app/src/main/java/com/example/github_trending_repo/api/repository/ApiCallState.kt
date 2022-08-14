package com.example.github_trending_repo.api.repository




sealed class ApiCallState {
    class LOADING(val bundle: Any? = null) : ApiCallState()
    class COMPLETED(val responseResult: Any? = null, ) : ApiCallState()
    class ERROR( val responseResult: Any? = null) : ApiCallState()
}
