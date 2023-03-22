package dev.mslalith.focuslauncher.core.data.network.api.fakes

import dev.mslalith.focuslauncher.core.data.network.api.QuotesApi
import dev.mslalith.focuslauncher.core.data.network.entities.QuotesApiResponse
import dev.mslalith.focuslauncher.core.data.utils.Constants.Defaults.QUOTES_LIMIT_PER_PAGE
import dev.mslalith.focuslauncher.core.data.utils.dummyQuoteResponseFor

internal class FakeQuotesApi : QuotesApi {
    override suspend fun getQuotes(page: Int, limit: Int): QuotesApiResponse {
        val pageOffset = (page - 1) * QUOTES_LIMIT_PER_PAGE
        return QuotesApiResponse(
            count = 10,
            totalCount = 100,
            page = 1,
            totalPages = 20,
            lastItemIndex = 9,
            results = List(size = QUOTES_LIMIT_PER_PAGE) { dummyQuoteResponseFor(index = pageOffset + it) }
        )
    }
}