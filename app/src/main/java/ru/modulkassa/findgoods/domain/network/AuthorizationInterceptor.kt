package ru.modulkassa.findgoods.domain.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import ru.modulkassa.findgoods.domain.repository.RetailPointRepository

class AuthorizationInterceptor(
    private val repo: RetailPointRepository
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        var request = chain.request()

        if (repo.hasAccount()) {
            val credentials = Credentials.basic(repo.login(), repo.password())
            request = request.newBuilder().addHeader("Authorization", credentials).build()
        }

        return chain.proceed(request)
    }
}